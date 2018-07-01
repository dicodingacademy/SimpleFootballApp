package com.nbs.simplefootballapp.domain;

import android.util.Log;

import com.nbs.simplefootballapp.data.libs.ApiCallback;
import com.nbs.simplefootballapp.data.libs.ApiManager;
import com.nbs.simplefootballapp.data.model.entity.FootballTeam;
import com.nbs.simplefootballapp.data.model.request.GetFootballTeamRequest;
import com.nbs.simplefootballapp.data.model.response.FootballTeamResponse;
import com.nbs.simplefootballapp.presentation.viewmodel.Team;
import com.nbs.simplefootballapp.presentation.viewmodel.TeamMapper;

import java.util.List;

import retrofit2.Call;
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
    public void callApi() {
        execute(new ApiCallback<FootballTeamResponse>(){
            @Override
            public void onSuccess(FootballTeamResponse response) {
                if (response.getFootballTeams() != null){
                    getOnGetFootballTeamsCallback().onGetFootballTeamsSuccess(teamMapper.getTeams(response.getFootballTeams()));
                }else{
                    onResponseEmpty();
                }
            }

            @Override
            public void onFailure(String message) {
                getOnGetFootballTeamsCallback().onGetFootballTeamsFailed(message);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "Request Finished");
            }

            @Override
            public void onRequestCancelled() {
                Log.d(TAG, "Request Cancelled");
            }
        });
    }

    public interface OnGetFootballTeamsCallback{
        void onGetFootballTeamsSuccess(List<Team> teams);

        void onGetFootballTeamsFailed(String message);
    }
}
