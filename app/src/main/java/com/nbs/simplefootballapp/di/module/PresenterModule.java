package com.nbs.simplefootballapp.di.module;

import com.nbs.simplefootballapp.presentation.MainContract;
import com.nbs.simplefootballapp.presentation.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    @Provides
    MainContract.Presenter provideMainPresenter(MainPresenter mainPresenter){
        return mainPresenter;
    }
}
