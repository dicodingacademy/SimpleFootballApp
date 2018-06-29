package com.nbs.simplefootballapp.data.libs;

import com.nbs.simplefootballapp.data.model.response.FootballTeamResponse;

import javax.inject.Inject;

import retrofit2.Call;

public class ApiManager implements IApiManager{

    private ApiClient apiClient;

    @Inject
    public ApiManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Call<FootballTeamResponse> getFootballTeam(String leagueName) {
        return apiClient.getFootballTeam(leagueName);
    }
}
