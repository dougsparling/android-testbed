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

    public void testDateLabel() {
        MainActivity main = getActivity();

        View dateLabel = main.findViewById(R.id.date_label);
        assertAssignableFrom(TextView.class, dateLabel);

        String label = ((TextView) dateLabel).getText().toString();
        assertEquals("Date: 85/11/05", label);
    }

    public void testLabelClicks() {
        MainActivity main = getActivity();

        View helloWorld = main.findViewById(R.id.hello_world);
        assertAssignableFrom(TextView.class, helloWorld);

        assertContainsRegex("Hel{2}o", ((TextView) helloWorld).getText().toString());

        Spoon.screenshot(main, "initial");

        clickView(main, helloWorld);
        assertContainsRegex("Clicked 1 Times", ((TextView) helloWorld).getText().toString());
        Spoon.screenshot(main, "first");

        clickView(main, helloWorld);
        assertContainsRegex("Clicked 2 Times", ((TextView) helloWorld).getText().toString());
        Spoon.screenshot(main, "second");

        clickView(main, helloWorld);
        assertContainsRegex("Clicked 3 Times", ((TextView) helloWorld).getText().toString());
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
