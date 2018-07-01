package com.nbs.simplefootballapp.presentation;

import com.nbs.simplefootballapp.presentation.viewmodel.Team;

import java.util.List;

public interface MainContract {
    interface View{
        void showFootballTeam(List<Team> footballTeams);

        void onFailedLoadFootballTeam(String message);

        void showHideProgressbar(boolean isShown);
    }

    interface Presenter{
        void onAttach(MainContract.View view);

        void getFootballTeams();

        void onDetach();
    }
}
