package com.example.steamstats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.example.steamstats.databinding.GameListItemBinding
import com.example.steamstats.models.GameInfo
import com.squareup.picasso.Picasso
import java.util.*

class StatsAdapter(var gamesList: ObservableArrayList<GameInfo>): RecyclerView.Adapter<StatsAdapter.ViewHolder>() {
    class ViewHolder(val binding: GameListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return gamesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GameListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gamesList[position]
        holder.binding.gameTitle.text = game.title
        Picasso
            .get()
            .load(game.imgIconURL)
            .into(holder.binding.thumbnail)
    }

    fun updateList() {
        notifyDataSetChanged()
    }
}