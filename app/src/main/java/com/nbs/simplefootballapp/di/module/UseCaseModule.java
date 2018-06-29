package com.nbs.simplefootballapp.di.module;

import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.domain.GetFootballTeamsUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    GetFootballTeamsUseCase provideGetFootballTeamUseCase(ApiManager apiManager){
        return new GetFootballTeamsUseCase(apiManager);
    }
}
