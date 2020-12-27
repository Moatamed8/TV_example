package com.example.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tvshow.R;
import com.example.tvshow.databinding.ItemContainerEpisodeBinding;
import com.example.tvshow.models.Episode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>{
    private List<Episode>episodes;
    private LayoutInflater layoutInflater;
    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (layoutInflater==null){
        layoutInflater=LayoutInflater.from(parent.getContext());

    }
        ItemContainerEpisodeBinding itemContainerEpisodeBinding= DataBindingUtil.inflate(
          layoutInflater, R.layout.item_container_episode,parent,false
        );
    return new EpisodesViewHolder(itemContainerEpisodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        holder.bindEpisodes(episodes.get(position));

    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodesViewHolder extends RecyclerView.ViewHolder{
        private ItemContainerEpisodeBinding itemContainerEpisodeBinding;
        public EpisodesViewHolder(ItemContainerEpisodeBinding itemContainerEpisodeBinding){
            super(itemContainerEpisodeBinding.getRoot());
            this.itemContainerEpisodeBinding=itemContainerEpisodeBinding;
        }
        public void bindEpisodes(Episode episode){

            String title="S";
            String season =episode.getSeason();
            if (season.length()==1){
                season="0".concat(season);
            }
            String episodesNumber=episode.getEpisode();
            if (episodesNumber.length()==1){
                episodesNumber="0".concat(episodesNumber);

            }
            episodesNumber="E".concat(episodesNumber);
            title=title.concat(season).concat(episodesNumber);
            itemContainerEpisodeBinding.setTitle(title);
            itemContainerEpisodeBinding.setName(episode.getName());
            itemContainerEpisodeBinding.setAirDate(episode.getAirDate());
        }
    }
}
