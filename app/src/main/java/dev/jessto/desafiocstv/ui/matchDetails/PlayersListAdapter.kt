package dev.jessto.desafiocstv.ui.matchDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jessto.desafiocstv.R
import dev.jessto.desafiocstv.databinding.ItemPlayerTeam1Binding
import dev.jessto.desafiocstv.ui.model.PlayerDTO

class PlayersListAdapter(private val context: Context) : ListAdapter<PlayerDTO, PlayersListAdapter.PlayersListViewHolder>
    (PlayersListDiffCallback) {

    inner class PlayersListViewHolder(private val binding: ItemPlayerTeam1Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerDTO) {
            with(binding) {
                tvPlayerName.text = player.name
                tvNickname.text = player.nickname

                if (player.playerImg != null) {
                    Glide.with(context)
                        .load(player.playerImg)
                        .placeholder(R.drawable.bg_player_placeholder)
                        .fallback(R.drawable.ic_player_fallback)
                        .fitCenter() // TODO: Round picture corners with Glide
                        .skipMemoryCache(true)
                        .into(ivPlayerPicture)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersListViewHolder {
        return PlayersListViewHolder(
            ItemPlayerTeam1Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayersListViewHolder, position: Int) {
        val playerItem = getItem(position)
        holder.bind(playerItem)
    }

    companion object PlayersListDiffCallback : DiffUtil.ItemCallback<PlayerDTO>() {
        override fun areItemsTheSame(oldItem: PlayerDTO, newItem: PlayerDTO): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlayerDTO, newItem: PlayerDTO): Boolean {
            return oldItem.name == newItem.name
        }

    }
}
