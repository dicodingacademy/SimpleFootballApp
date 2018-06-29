package com.nbs.simplefootballapp.presentation;

import com.nbs.simplefootballapp.data.model.entity.FootballTeam;
import com.nbs.simplefootballapp.data.model.request.GetFootballTeamRequest;
import com.nbs.simplefootballapp.domain.GetFootballTeamsUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterTest {
    @Mock
    private MainContract.View view;

    @Mock
    private GetFootballTeamsUseCase getFootballTeamsUseCase;

    private MainPresenter mainPresenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(getFootballTeamsUseCase);
        getFootballTeamsUseCase.setRequestModel(new GetFootballTeamRequest("English Premier League"));
        mainPresenter.onAttach(view);
    }

    @Test
    public void testGetFootballTeam(){
        List<FootballTeam> footballTeams = new ArrayList<>();

        Mockito.doNothing().when(getFootballTeamsUseCase).execute();

        mainPresenter.getFootballTeams();
        mainPresenter.onGetFootballTeamsSuccess(footballTeams);

        Mockito.verify(view).showHideProgressbar(true);
        Mockito.verify(view).showHideProgressbar(false);
        Mockito.verify(view).showFootballTeam(footballTeams);
    }

}