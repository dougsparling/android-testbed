package com.protegra.dsparling.testbed;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscription;
import rx.android.observables.ViewObservable;
import rx.functions.FuncN;

public class FormActivity extends ActionBarActivity {
    private static final String BACKGROUND_COUNTER = "background.counter";

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

//        RecyclerView recycler = (RecyclerView) findViewById(R.id.form_recycler);
//        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recycler.setAdapter(new );
    }

    @Override
    protected void onResume() {
        super.onResume();

        subscription = Observable.combineLatest(Arrays.asList(
                        observeNumbers(R.id.first_field),
                        observeNumbers(R.id.second_field),
                        Observable.interval(1, TimeUnit.SECONDS)),
                new FuncN<Long>() {
                    @Override
                    public Long call(Object... args) {
                        return 0L;
                    }
                }
                        .subscribe(this::updateSum);
        //array -> {List.list(array).foldLeft((e, sum) -> {((Long)e) + sum}, 0L)})
    }

    @Override
    protected void onPause() {
        super.onPause();

        subscription.unsubscribe();
        subscription = null;
    }

    private void updateSum(Long sum) {
        ((TextView) findViewById(R.id.sum_label)).setText(sum.toString());
    }

    private Observable<Long> observeNumbers(int textRes) {
        EditText editText = (EditText) findViewById(textRes);
        return ViewObservable.text(editText, true)
                .map(x -> {
                    Log.d("rx", "text: " + x.text);
                    return x;
                })
                .map(event -> {
                    if (Pattern.matches("^\\d{1,18}$", event.text.toString())) {
                        return Long.valueOf(event.text.toString());
                    } else {
                        return 0L;
                    }
                });

    }
}
