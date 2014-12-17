package com.protegra.dsparling.testbed;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

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

    public void testFailureOhNo() {
        assertTrue("Look around you. What do you see? Maths.", 1 == 2);
    }
}
