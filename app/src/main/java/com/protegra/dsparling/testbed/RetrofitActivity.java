package com.protegra.dsparling.testbed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.protegra.dsparling.testbed.imgur.Imgur;
import com.protegra.dsparling.testbed.imgur.Meme;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.util.Collections.nCopies;

public class RetrofitActivity extends Activity {

    public static final int BATCH_SIZE = 10;

    private MemeData placeholderMeme;
    private MemeAdapter adapter;
    private Subscription memeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        placeholderMeme = new MemeData(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder), "...", android.R.color.white);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.img_recycler);
        adapter = new MemeAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: if we wanted to be really fancy we could persist the current page across config changes...
        memeSubscription = Observable.interval(5, TimeUnit.SECONDS) // refresh images every 5 seconds
                .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(4)))
                .map(x -> x + 1)                                  // refresh starting on with page 2, then do 3, etc.
                .startWith(1L)                                    // start on page 1
                .flatMap(Imgur.API::latestMemes)                  // get latest memes
                .filter(res -> res.success)                       // only successes
                .map(res -> res.data)                             // map to the "data" in the response
                .flatMap(Observable::from)                        // flatten Observable<Iterable<T>> -> Observable<T> so we can buffer across requests
                .map(this::resolveMeme)                           // resolve image data and description
                .startWith(nCopies(BATCH_SIZE, placeholderMeme))  // start stream with placeholders
                .buffer(BATCH_SIZE)                               // release 10 images at a time to the update method
                .observeOn(AndroidSchedulers.mainThread())        // once images are downloaded, etc., update on UI thread
                .subscribe(this::updateImages);
    }

    protected MemeData resolveMeme(Meme memeSummary) {
        try {
            Bitmap memeBitmap = BitmapFactory.decodeStream(new URL(memeSummary.link).openConnection().getInputStream());
            int accentColor = Palette.generate(memeBitmap).getLightMutedColor(android.R.color.white);
            return new MemeData(memeBitmap, memeSummary.description, accentColor);
        } catch (Exception e) {
            return placeholderMeme;
        }
    }

    public void updateImages(List<MemeData> memes) {
        adapter.setMemeData(memes);
        // TODO: more granular changes to make use of fun recyclerview animations...
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();

        memeSubscription.unsubscribe();
        memeSubscription = null;
    }

    static class MemeCardHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView desc;

        private MemeCardHolder(View memeCard) {
            super(memeCard);
            this.image = (ImageView) memeCard.findViewById(R.id.meme_image);
            this.desc = (TextView) memeCard.findViewById(R.id.meme_desc);
        }
    }

    static class MemeData {
        public final Bitmap image;
        public final CharSequence desc;
        public final int accentColor;

        MemeData(Bitmap image, CharSequence desc, int accentColor) {
            this.image = image;
            this.desc = desc;
            this.accentColor = accentColor;
        }
    }

    static class MemeAdapter extends RecyclerView.Adapter<MemeCardHolder> {

        private List<MemeData> data;

        private MemeAdapter() {
            this.data = Collections.emptyList();
        }

        @Override
        public MemeCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView memeCard = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_card, parent, false);
            return new MemeCardHolder(memeCard);
        }

        @Override
        public void onBindViewHolder(MemeCardHolder holder, int position) {
            holder.image.setImageBitmap(data.get(position).image);
            holder.desc.setText(data.get(position).desc);
            ((CardView) holder.itemView).setCardBackgroundColor(data.get(position).accentColor);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setMemeData(List<MemeData> memeData) {
            this.data = memeData;
        }
    }
}
