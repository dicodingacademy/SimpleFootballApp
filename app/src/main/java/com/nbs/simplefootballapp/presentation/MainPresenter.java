package com.nbs.simplefootballapp.presentation;

import com.nbs.simplefootballapp.data.model.entity.FootballTeam;
import com.nbs.simplefootballapp.data.model.request.GetFootballTeamRequest;
import com.nbs.simplefootballapp.domain.GetFootballTeamsUseCase;
import com.nbs.simplefootballapp.util.EspressoIdlingResource;

import java.util.List;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter,
        GetFootballTeamsUseCase.OnGetFootballTeamsCallback{

    private GetFootballTeamsUseCase getFootballTeamsUseCase;

    private MainContract.View view;

    @Inject
    public MainPresenter(GetFootballTeamsUseCase getFootballTeamsUseCase) {
        this.getFootballTeamsUseCase = getFootballTeamsUseCase;
        getFootballTeamsUseCase.setOnGetFootballTeamsCallback(this);
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getFootballTeams() {
        EspressoIdlingResource.increment();
        view.showHideProgressbar(true);
        getFootballTeamsUseCase.setRequestModel(new
                GetFootballTeamRequest("English Premier League"));
        getFootballTeamsUseCase.execute();
    }

    @Override
    public void onDetach() {
        getFootballTeamsUseCase.cancel();
    }

    @Override
    public void onGetFootballTeamsSuccess(List<FootballTeam> footballTeams) {
        EspressoIdlingResource.decrement();
        view.showHideProgressbar(false);
        view.showFootballTeam(footballTeams);
    }

    @Override
    public void onGetFootballTeamsFailed(String message) {
        EspressoIdlingResource.decrement();
        view.showHideProgressbar(false);
        view.onFailedLoadFootballTeam(message);
    }
}
