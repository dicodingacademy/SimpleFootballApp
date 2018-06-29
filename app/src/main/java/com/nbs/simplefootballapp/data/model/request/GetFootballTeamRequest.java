package com.nbs.simplefootballapp.data.model.request;

public class GetFootballTeamRequest extends RequestModel {
    private String league;

    public GetFootballTeamRequest(String league) {
        this.league = league;
    }

    public String getLeague() {
        return league;
    }
}
