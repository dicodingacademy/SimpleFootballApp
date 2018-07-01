package com.nbs.simplefootballapp.domain;

import com.nbs.simplefootballapp.data.libs.ApiCallback;
import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.data.model.request.GetFootballTeamRequest;
import com.nbs.simplefootballapp.data.model.response.FootballTeamResponse;
import com.nbs.simplefootballapp.presentation.viewmodel.Team;
import com.nbs.simplefootballapp.presentation.viewmodel.TeamMapper;

import java.util.List;

import rx.Observable;

public class GetFootballTeamsUseCase extends UseCase<GetFootballTeamRequest, FootballTeamResponse> {

    private OnGetFootballTeamsCallback onGetFootballTeamsCallback;

    private TeamMapper teamMapper;

    private static final String TAG = GetFootballTeamsUseCase.class.getSimpleName();

    public GetFootballTeamsUseCase(ApiManager apiManager, TeamMapper teamMapper) {
        super(apiManager);
        this.teamMapper = teamMapper;
    }

    public OnGetFootballTeamsCallback getOnGetFootballTeamsCallback() {
        return onGetFootballTeamsCallback;
    }

    public void setOnGetFootballTeamsCallback(OnGetFootballTeamsCallback onGetFootballTeamsCallback) {
        this.onGetFootballTeamsCallback = onGetFootballTeamsCallback;
    }

    @Override
    protected Observable<FootballTeamResponse> getApi() {
        setApiCall(getApiManager().getFootballTeam(getRequestModel().getLeague()));
        return getApiCall();
    }

    @Override
    protected void onResponseEmpty() {
        getOnGetFootballTeamsCallback().onGetFootballTeamsFailed(ApiCallback.GENERAL_ERROR_NO_DATA);
    }

    @Override
    protected void onResponseLoaded(FootballTeamResponse response) {
        if (response.getFootballTeams() != null){
            getOnGetFootballTeamsCallback().onGetFootballTeamsSuccess(teamMapper.getTeams(response.getFootballTeams()));
        }else{
            onResponseEmpty();
        }
    }

    @Override
    protected void onResponseError(String message) {

    }

    public interface OnGetFootballTeamsCallback{
        void onGetFootballTeamsSuccess(List<Team> teams);

        void onGetFootballTeamsFailed(String message);
    }
}
