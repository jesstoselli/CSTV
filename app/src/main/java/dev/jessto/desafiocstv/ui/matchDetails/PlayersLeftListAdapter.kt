package dev.jessto.desafiocstv.ui.matchDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jessto.desafiocstv.R
import dev.jessto.desafiocstv.databinding.ItemPlayerTeamLeftBinding
import dev.jessto.desafiocstv.ui.model.PlayerDTO

class PlayersLeftListAdapter(private val context: Context) : ListAdapter<PlayerDTO,
        PlayersLeftListAdapter
        .PlayersLeftListViewHolder>
    (PlayersLeftListDiffCallback) {

    inner class PlayersLeftListViewHolder(private val binding: ItemPlayerTeamLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: PlayerDTO) {
            with(binding) {

                tvPlayerName.text = player.name
                tvNickname.text = player.nickname

                Glide.with(context)
                    .load(player.playerImg)
                    .fallback(R.drawable.ic_player_fallback)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .into(ivPlayerPicture)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersLeftListAdapter.PlayersLeftListViewHolder {
        return PlayersLeftListViewHolder(
            ItemPlayerTeamLeftBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayersLeftListViewHolder, position: Int) {
        val playerItem = getItem(position)
        holder.bind(playerItem)
    }

    companion object PlayersLeftListDiffCallback : DiffUtil.ItemCallback<PlayerDTO>() {
        override fun areItemsTheSame(oldItem: PlayerDTO, newItem: PlayerDTO): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlayerDTO, newItem: PlayerDTO): Boolean {
            return oldItem.name == newItem.name
        }

    }
}
