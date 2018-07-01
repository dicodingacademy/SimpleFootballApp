package com.nbs.simplefootballapp.data.libs;

import com.nbs.simplefootballapp.data.model.response.FootballTeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiClient {
    @GET("search_all_teams.php")
    Observable<FootballTeamResponse> getFootballTeam(@Query("l") String leagueName);
}
