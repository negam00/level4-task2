package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.Game
import kotlinx.android.synthetic.main.history_item.view.*

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    /**
     * Creates and returns a ViewHolder object, inflating the layout called item_game.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvResult.text = game.result
            itemView.tvTimestamp.text = game.date
            itemView.ivComputer.setImageResource(Game.GAME_RES_DRAWABLE_IDS[game.computerPick])
            itemView.ivPlayer.setImageResource(Game.GAME_RES_DRAWABLE_IDS[game.playerPick])
        }
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int = games.size


    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(games[position])
}
