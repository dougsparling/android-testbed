package com.protegra.dsparling.testbed;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.squareup.spoon.Spoon;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.*;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.*;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.*;

public class ReactiveTest extends ActivityInstrumentationTestCase2 {
    public ReactiveTest() {
        super(ReactiveActivity.class);
    }

    public void testCounter() throws InterruptedException {
        Activity reactive = getActivity();
        Thread.sleep(1000L);
        for (int i = 1; i <= 5; i++) {
            onView(withId(R.id.text)).check(matches(withText(new PatternMatcher("\\d+ \\d+"))));
            Spoon.screenshot(reactive, "after " + i + " seconds");
            Thread.sleep(1000L);
        }

        stop(reactive, 5, TimeUnit.SECONDS);

        for (int i = 10; i <= 15; i++) {
            onView(withId(R.id.text)).check(matches(withText(new PatternMatcher("\\d+ \\d+"))));
            Spoon.screenshot(reactive, "after " + i + " seconds");
            Thread.sleep(1000L);
        }
    }

    private void stop(Activity reactive, long time, TimeUnit unit) throws InterruptedException {
        getInstrumentation().callActivityOnStop(reactive);
        Thread.sleep(unit.toMillis(time));
        getInstrumentation().callActivityOnStart(reactive);
    }

    private static class PatternMatcher extends BaseMatcher<String> {
        private final Pattern pattern;

        private PatternMatcher(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        @Override
        public boolean matches(Object o) {
            return o != null && this.pattern.matcher(o.toString()).matches();
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("matches " + pattern.toString());
        }
    }
}
