package com.vicky7230.eatit.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by agrim on 4/7/17.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface HomeActivityContext {
}
