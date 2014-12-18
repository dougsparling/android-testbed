package com.protegra.dsparling.testbed.time;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by dsparling on 2014-12-18.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface IsMock {
}
