package com.nbs.simplefootballapp.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nbs.simplefootballapp.R;
import com.nbs.simplefootballapp.data.model.entity.FootballTeam;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootballTeamAdapter extends RecyclerView.Adapter<FootballTeamAdapter.FootballTeamViewholder> {

    private List<FootballTeam> footballTeams;

    private OnTeamItemClickCallback onTeamItemClickCallback;

    public OnTeamItemClickCallback getOnTeamItemClickCallback() {
        return onTeamItemClickCallback;
    }

    public void setOnTeamItemClickCallback(OnTeamItemClickCallback onTeamItemClickCallback) {
        this.onTeamItemClickCallback = onTeamItemClickCallback;
    }

    public List<FootballTeam> getFootballTeams() {
        return footballTeams;
    }

    public void setFootballTeams(List<FootballTeam> footballTeams) {
        this.footballTeams = footballTeams;
    }

    @NonNull
    @Override
    public FootballTeamViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FootballTeamViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FootballTeamViewholder holder, int position) {
        holder.bind(getFootballTeams().get(position));
    }

    @Override
    public int getItemCount() {
        return getFootballTeams().size();
    }

    class FootballTeamViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_team)
        ImageView imgTeam;

        @BindView(R.id.tv_team)
        TextView tvTeam;

        @BindView(R.id.rvItem)
        RelativeLayout rvItem;

        public FootballTeamViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final FootballTeam footballTeam) {
            Picasso.get().load(footballTeam.getStrTeamBadge()).into(imgTeam);
            tvTeam.setText(footballTeam.getStrTeam());
            rvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnTeamItemClickCallback().onTeamItemClicked(footballTeam);
                }
            });
        }
    }

    public interface OnTeamItemClickCallback{
        void onTeamItemClicked(FootballTeam team);
    }
}
