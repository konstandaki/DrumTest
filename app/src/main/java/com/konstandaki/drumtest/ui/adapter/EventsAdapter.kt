package com.konstandaki.drumtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konstandaki.drumtest.databinding.EventListItemBinding
import com.konstandaki.drumtest.model.Event

class EventsAdapter(private val events: MutableList<Event>) : ListAdapter<Event, EventsAdapter.EventViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentEvent = getItem(position)
        holder.itemView.setOnClickListener {
            addToFavorites(currentEvent)
        }
        holder.bind(currentEvent)
    }

    private fun addToFavorites(event: Event) {
        if (!event.isFavorite) {
            event.isFavorite = true
            val position = events.indexOf(event)
            notifyItemChanged(position)
            if (position != 0) {
                val removedEvent = events.removeAt(position)
                events.add(0, removedEvent)
                notifyItemMoved(position, 0)
            }
        }
    }

    class EventViewHolder(private var binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.apply {
                tvEventTimer.text = event.start.toString()
                ivEventFavorite.setImageResource(if (event.isFavorite)  android.R.drawable.star_big_on
                    else android.R.drawable.star_big_off)
                tvEventName.text = event.name
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}