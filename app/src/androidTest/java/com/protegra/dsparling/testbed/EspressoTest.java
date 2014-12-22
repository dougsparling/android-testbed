package com.protegra.dsparling.testbed;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

public class EspressoTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public EspressoTest() {
        super(MainActivity.class);
    }

    public void testHelloWorldEspresso() {
        getActivity();
        onView(withId(R.id.hello_world)).check(matches(withText(R.string.hello_world)));
        onView(withId(R.id.hello_world)).check(matches(hasSibling(withId(R.id.date_label))));
        onView(withId(R.id.hello_world)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.hello_world)).check(matches(new TextSizeEquals(12)));
        onView(withId(R.id.hello_world)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.hello_world)).perform(click(), click(), click(), click(), click(), click(), click(), click());
        onView(withId(R.id.hello_world)).check(matches(withText("Was Clicked 8 Times!")));
    }

    private static class TextSizeEquals extends BaseMatcher<View> {
        private final float expectedTextSize;

        private TextSizeEquals(float expectedTextSize) {
            this.expectedTextSize = expectedTextSize;
        }

        @Override
        public boolean matches(Object o) {
            if (!(o instanceof TextView)) {
                return false;
            }
            float sp = ((TextView) o).getPaint().getTextSize() / ((TextView) o).getPaint().density;
            return expectedTextSize == sp;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("text size equal to " + expectedTextSize);
        }
    }
}

