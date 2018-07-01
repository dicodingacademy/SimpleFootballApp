package com.nbs.simplefootballapp.di.module;

import com.nbs.simplefootballapp.presentation.viewmodel.TeamMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class MapperModule {
    @Provides
    TeamMapper provideTeamMapper(){
        return new TeamMapper();
    }
}
