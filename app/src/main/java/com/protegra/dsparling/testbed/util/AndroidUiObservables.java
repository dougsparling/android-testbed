package com.protegra.dsparling.testbed.util;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import rx.Observable;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.subjects.BehaviorSubject;

public class AndroidUiObservables {
    public static Observable<CharSequence> fromEditText(EditText text) {
        final BehaviorSubject<CharSequence> textSubject = BehaviorSubject.create(text.getText().toString());

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textSubject.onNext(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return textSubject;
    }
}
