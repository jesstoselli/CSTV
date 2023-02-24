package dev.jessto.desafiocstv.ui.matchesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jessto.desafiocstv.R
import dev.jessto.desafiocstv.databinding.ItemMatchBinding
import dev.jessto.desafiocstv.ui.model.MatchDTO

class MatchesListAdapter(private val context: Context, private val clickListener: MatchesListClickListener) :
    ListAdapter<MatchDTO, MatchesListAdapter.MatchesListViewHolder>(MatchesListDiffCallback) {

    inner class MatchesListViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchDTO) {
            with(binding) {
                val leagueSeries = match.leagueName + " " + match.series

                tvItemMatchTime.text = match.scheduledTime.toString()
                tvItemNameTeam1.text = match.teams[0].name
                tvItemNameTeam2.text = match.teams[1].name
                tvLeagueSeries.text = leagueSeries

                if (match.leagueImg != null) {
                    Glide.with(context)
                        .load(match.leagueImg)
                        .placeholder(R.drawable.bg_league_logo_placeholder)
                        .fallback(R.drawable.ic_league_fallback)
                        .fitCenter()
                        .circleCrop()
                        .skipMemoryCache(true)
                        .into(ivLeagueSeries)
                }

                if (match.teams[0].badgeImg != null) {
                    Glide.with(context)
                        .load(match.teams[0].badgeImg)
                        .placeholder(R.drawable.bg_team_logo_placeholder)
                        .fallback(R.drawable.ic_badge_fallback)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .into(ivItemLogoTeam1)
                }

                if (match.teams[1].badgeImg != null) {
                    Glide.with(context)
                        .load(match.teams[1].badgeImg)
                        .placeholder(R.drawable.bg_team_logo_placeholder)
                        .fallback(R.drawable.ic_badge_fallback)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .into(ivItemLogoTeam2)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesListViewHolder {
        return MatchesListViewHolder(
            ItemMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchesListViewHolder, position: Int) {
        val matchItem = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(matchItem)
        }

        holder.bind(matchItem)
    }

    companion object MatchesListDiffCallback : DiffUtil.ItemCallback<MatchDTO>() {
        override fun areItemsTheSame(oldItem: MatchDTO, newItem: MatchDTO): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MatchDTO, newItem: MatchDTO): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class MatchesListClickListener(val clickListener: (match: MatchDTO) -> Unit) {
        fun onClick(match: MatchDTO) = clickListener(match)
    }
}
