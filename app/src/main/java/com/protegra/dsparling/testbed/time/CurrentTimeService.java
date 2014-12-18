package com.protegra.dsparling.testbed.time;

import java.util.Calendar;

/**
 * Created by dsparling on 2014-12-18.
 */
public class CurrentTimeService implements TimeService {
    @Override
    public Calendar getTime() {
        return Calendar.getInstance();
    }
}
