package dev.jessto.desafiocstv.ui.matchesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jessto.desafiocstv.R
import dev.jessto.desafiocstv.databinding.ItemMatchBinding
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.utils.mappers.formatDate

class MatchesListAdapter(
    private val context: Context,
    private val clickListener: MatchesListClickListener
) :
    ListAdapter<MatchDTO, MatchesListAdapter.MatchesListViewHolder>(MatchesListDiffCallback) {

    inner class MatchesListViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchDTO) {
            with(binding) {
                val leagueSeries = if (match.series.isNullOrEmpty()) match.leagueName else match.leagueName + " - " + match.series

                if (match.status != "running") {
                    tvItemMatchTime.text = formatDate(match.scheduledTime!!)
                    tvItemMatchTime.background = ContextCompat.getDrawable(context, R.drawable.bg_match_time_scheduled)
                } else {
                    tvItemMatchTime.text = context.getString(R.string.agora)
                    tvItemMatchTime.background = ContextCompat.getDrawable(context, R.drawable.bg_match_time_live)
                }


                tvLeagueSeries.text = leagueSeries

                Glide.with(context)
                    .load(match.leagueImg)
                    .placeholder(R.drawable.bg_league_logo_placeholder)
                    .fallback(R.drawable.ic_league_fallback)
                    .fitCenter()
                    .circleCrop()
                    .skipMemoryCache(true)
                    .into(ivLeagueSeries)

                if (match.teams != null) {
                    tvItemNameTeam1.text = match.teams[0].name
                    tvItemNameTeam2.text = match.teams[1].name

                    Glide.with(context)
                        .load(match.teams[0].badgeImg)
                        .fallback(R.drawable.ic_badge_fallback)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .into(ivItemLogoTeam1)

                    Glide.with(context)
                        .load(match.teams[1].badgeImg)
                        .fallback(R.drawable.ic_badge_fallback)
                        .fitCenter()
                        .skipMemoryCache(true)
                        .into(ivItemLogoTeam2)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesListAdapter.MatchesListViewHolder {
        return MatchesListViewHolder(
            ItemMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MatchesListAdapter.MatchesListViewHolder, position: Int) {
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
