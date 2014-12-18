package com.protegra.dsparling.testbed.time;

import com.protegra.dsparling.testbed.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
    library = true
)
public class EnvModule {
    @Provides @Singleton @IsMock Boolean providesIsMock() {
        return "mock".equals(BuildConfig.FLAVOR);
    }
}
