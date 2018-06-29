package com.nbs.simplefootballapp.data.model.response;

import com.google.gson.annotations.SerializedName;
import com.nbs.simplefootballapp.data.model.entity.FootballTeam;

import java.util.ArrayList;
import java.util.List;

public class FootballTeamResponse extends ResponseModel {
    @SerializedName("teams")
    private List<FootballTeam> footballTeams = new ArrayList<>();

    public List<FootballTeam> getFootballTeams() {
        return footballTeams;
    }

    public void setFootballTeams(List<FootballTeam> footballTeams) {
        this.footballTeams = footballTeams;
    }
}
