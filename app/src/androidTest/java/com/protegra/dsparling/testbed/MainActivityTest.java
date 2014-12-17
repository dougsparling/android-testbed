package com.protegra.dsparling.testbed;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import com.squareup.spoon.Spoon;

import static android.test.MoreAsserts.*;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testHelloWorld() {
        MainActivity main = getActivity();

        View helloWorld = main.findViewById(R.id.hello_world);
        assertAssignableFrom(TextView.class, helloWorld);

        String label = ((TextView) helloWorld).getText().toString();
        assertContainsRegex("Hel{2}o", label);
    }

    public void testLabelClicks() {
        MainActivity main = getActivity();

        View helloWorld = main.findViewById(R.id.hello_world);
        assertAssignableFrom(TextView.class, helloWorld);

        String label = ((TextView) helloWorld).getText().toString();
        assertContainsRegex("Hel{2}o", label);

        Spoon.screenshot(main, "initial");

        clickView(main, helloWorld);
        assertContainsRegex("Clicked 1 Times", label);
        Spoon.screenshot(main, "first");

        clickView(main, helloWorld);
        assertContainsRegex("Clicked 2 Times", label);
        Spoon.screenshot(main, "second");

        clickView(main, helloWorld);
        assertContainsRegex("Clicked 3 Times", label);
        Spoon.screenshot(main, "third");
    }

    private void clickView(MainActivity activity, final View view) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
    }

}
