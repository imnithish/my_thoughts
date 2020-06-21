package com.imnstudios.mythoughts.ui.home.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.data.db.entities.Thoughts
import java.text.SimpleDateFormat
import java.util.*

class AllThoughtsAdapter(val thoughts: List<Thoughts>, val recycleClick: RecycleClick) :
    RecyclerView.Adapter<AllThoughtsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.thoughts_item, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val thoughts = thoughts[position]

        holder.thoughtsItem.text = thoughts.thought

        val timestamp = thoughts.id.toLong() * 1000
        val dateTimeTemp = Date(timestamp)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val dateTime = format.format(dateTimeTemp)
        holder.timestampItem.text = dateTime

        holder.thoughtsContainerCard.setBackgroundColor(Color.parseColor(thoughts.color))
    }

    override fun getItemCount(): Int {
        return thoughts.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var thoughtsItem: TextView
        var timestampItem: TextView
        var thoughtsContainerCard: CardView

        init {
            view.setOnClickListener {
                recycleClick.onItemClick(adapterPosition)
            }
            this.thoughtsItem = view.findViewById(R.id.thoughts_item)
            this.timestampItem = view.findViewById(R.id.timestamp_item)
            this.thoughtsContainerCard = view.findViewById(R.id.thoughts_container_card)
        }
    }


    interface RecycleClick {
        fun onItemClick(position: Int)
    }

    fun getNoteAt(position: Int): Thoughts {
        return thoughts[position]
    }
}