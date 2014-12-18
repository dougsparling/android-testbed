package com.protegra.dsparling.testbed.time;

import com.protegra.dsparling.testbed.App;
import com.protegra.dsparling.testbed.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = MainActivity.class,
        includes = EnvModule.class,
        library = true
)
public class TimeModule {
    @Provides TimeService provideTimeService(@IsMock Boolean isMock) {
        return isMock ? createMockTimeService() : new CurrentTimeService();
    }

    private MockTimeService createMockTimeService() {
        try {
            Calendar novFifthEightyFive = Calendar.getInstance();
            novFifthEightyFive.setTime(new SimpleDateFormat("yyMMdd").parse("851105"));
            return new MockTimeService(novFifthEightyFive);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
