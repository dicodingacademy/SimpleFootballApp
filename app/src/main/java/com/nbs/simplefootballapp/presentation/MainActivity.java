package com.nbs.simplefootballapp.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nbs.simplefootballapp.R;
import com.nbs.simplefootballapp.di.component.AppComponent;
import com.nbs.simplefootballapp.di.component.DaggerAppComponent;
import com.nbs.simplefootballapp.presentation.viewmodel.Team;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MainContract.View, FootballTeamAdapter.OnTeamItemClickCallback {

    @Inject
    MainContract.Presenter presenter;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.rvItems)
    RecyclerView rvItems;

    private FootballTeamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppComponent appComponent = DaggerAppComponent.builder()
                .build();

        appComponent.inject(this);

        rvItems.setHasFixedSize(true);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new FootballTeamAdapter();
        adapter.setFootballTeams(new ArrayList<Team>());
        adapter.setOnTeamItemClickCallback(this);
        rvItems.setAdapter(adapter);

        presenter.onAttach(this);
        presenter.getFootballTeams();
    }

    @Override
    public void showFootballTeam(List<Team> footballTeams) {
        adapter.getFootballTeams().addAll(footballTeams);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedLoadFootballTeam(String message) {
        showToast(message);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHideProgressbar(boolean isShown) {
        progressbar.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onTeamItemClicked(Team team) {
        showToast(team.getName());
    }
}
