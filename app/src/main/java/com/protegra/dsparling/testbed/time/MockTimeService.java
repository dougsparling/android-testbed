package com.protegra.dsparling.testbed.time;

import java.util.Calendar;

public class MockTimeService implements TimeService {

    private final Calendar mockedTime;

    public MockTimeService(Calendar mockedTime) {
        this.mockedTime = mockedTime;
    }

    @Override
    public Calendar getTime() {
        return mockedTime;
    }
}
