package com.nbs.simplefootballapp.domain;

import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.data.model.entity.FootballTeam;
import com.nbs.simplefootballapp.data.model.request.GetFootballTeamRequest;
import com.nbs.simplefootballapp.data.model.response.FootballTeamResponse;
import com.nbs.simplefootballapp.presentation.viewmodel.Team;
import com.nbs.simplefootballapp.presentation.viewmodel.TeamMapper;

import java.util.List;

import retrofit2.Call;

public class GetFootballTeamsUseCase extends
        UseCase<GetFootballTeamRequest, FootballTeamResponse> {

    private OnGetFootballTeamsCallback onGetFootballTeamsCallback;

    private TeamMapper teamMapper;

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
    protected Call<FootballTeamResponse> getApi() {
        setApiCall(getApiManager().getFootballTeam(getRequestModel().getLeague()));
        return getApiCall();
    }

    @Override
    protected FootballTeamResponse getCache(GetFootballTeamRequest request) {
        return null;
    }

    @Override
    protected void onCacheLoaded(FootballTeamResponse response) {

    }

    @Override
    protected void onResponseLoaded(FootballTeamResponse response) {
        if (response.getFootballTeams() != null){
            if (!response.getFootballTeams().isEmpty()){
                getOnGetFootballTeamsCallback().onGetFootballTeamsSuccess(teamMapper.getTeams(response.getFootballTeams()));
            }else {
                onResponseEmpty();
            }
        }else{
            onResponseEmpty();
        }
    }

    @Override
    protected void onResponseEmpty() {
        getOnGetFootballTeamsCallback().onGetFootballTeamsFailed(GENERAL_ERROR_NO_DATA);
    }

    @Override
    protected void onErrorResponse(String message) {
        getOnGetFootballTeamsCallback().onGetFootballTeamsFailed(message);
    }

    @Override
    protected void onRequestCancelled() {

    }

    public interface OnGetFootballTeamsCallback{
        void onGetFootballTeamsSuccess(List<Team> teams);

        void onGetFootballTeamsFailed(String message);
    }
}
