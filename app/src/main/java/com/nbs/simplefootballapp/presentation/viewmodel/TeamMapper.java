package com.nbs.simplefootballapp.presentation.viewmodel;

import com.nbs.simplefootballapp.data.model.entity.FootballTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamMapper {
    private Team getTeam(FootballTeam footballTeam){
        return new Team(footballTeam.getIdTeam(),
                footballTeam.getStrTeam(), footballTeam.getStrTeamBadge(),
                footballTeam.getStrDescriptionEN(), footballTeam.getStrManager());
    }

    public List<Team> getTeams(List<FootballTeam> footballTeams){
        List<Team> teams = new ArrayList<>();
        for (FootballTeam footballTeam: footballTeams){
            Team team = getTeam(footballTeam);
            teams.add(team);
        }

        return teams;
    }
}
