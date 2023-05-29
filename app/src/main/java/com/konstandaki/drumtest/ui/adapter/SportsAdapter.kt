package com.konstandaki.drumtest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konstandaki.drumtest.databinding.SportListItemBinding
import com.konstandaki.drumtest.model.Sport

class SportsAdapter : ListAdapter<Sport, SportsAdapter.SportViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        return SportViewHolder(SportListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SportViewHolder(private var binding: SportListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sport: Sport) {
            binding.apply {
                tvSportName.text = sport.name
                ivSportArrow.setImageResource(if (rvSportEvents.isShown)  android.R.drawable.arrow_up_float
                    else android.R.drawable.arrow_down_float)
                val eventsAdapter = EventsAdapter(sport.events)
                eventsAdapter.submitList(sport.events)
                rvSportEvents.adapter = eventsAdapter
                rvSportEvents.layoutManager = LinearLayoutManager(rvSportEvents.context, LinearLayoutManager.HORIZONTAL, false)
                llSportHeader.setOnClickListener {
                    rvSportEvents.visibility = if (rvSportEvents.isShown) View.GONE else View.VISIBLE
                    ivSportArrow.setImageResource(if (rvSportEvents.isShown)  android.R.drawable.arrow_up_float
                        else android.R.drawable.arrow_down_float)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Sport>() {
            override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}