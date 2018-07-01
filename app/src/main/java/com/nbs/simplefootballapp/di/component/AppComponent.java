package com.nbs.simplefootballapp.di.component;

import com.nbs.simplefootballapp.di.module.MapperModule;
import com.nbs.simplefootballapp.presentation.MainActivity;
import com.nbs.simplefootballapp.di.module.ApiModule;
import com.nbs.simplefootballapp.di.module.PresenterModule;
import com.nbs.simplefootballapp.di.module.UseCaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, UseCaseModule.class, PresenterModule.class, MapperModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
