package com.nbs.simplefootballapp.presentation;

import com.nbs.simplefootballapp.data.model.entity.FootballTeam;

import java.util.List;

public interface MainContract {
    interface View{
        void showFootballTeam(List<FootballTeam> footballTeams);

        void onFailedLoadFootballTeam(String message);

        void showHideProgressbar(boolean isShown);
    }

    interface Presenter{
        void onAttach(MainContract.View view);

        void getFootballTeams();

        void onDetach();
    }
}
