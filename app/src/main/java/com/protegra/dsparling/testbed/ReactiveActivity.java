package com.protegra.dsparling.testbed;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class ReactiveActivity extends Activity {

    private static class Pair<X, Y> {

        private Pair(X first, Y second) {
            this.first = first;
            this.second = second;
        }

        X first; Y second;
    }

    private static Observable<Pair<Long, Long>> DATA =
        Observable.interval(1, TimeUnit.SECONDS)
                .zipWith(Observable.interval(5, TimeUnit.MILLISECONDS)
                            .cache()
                            .onBackpressureDrop(),
                    (x,y) -> new Pair<>(x, y))
            .observeOn(AndroidSchedulers.mainThread());

    private Subscription dataSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reactive);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSub = DATA.subscribe(pair -> {
            String label = String.format("%d %d", pair.first, pair.second);
            ((TextView) findViewById(R.id.text)).setText(label);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSub.unsubscribe();
    }
}
