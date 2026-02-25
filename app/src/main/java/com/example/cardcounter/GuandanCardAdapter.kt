package com.example.cardcounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuandanCardAdapter(
    private val cards: List<GuandanCard>,
    private val onPlayerClick: (cardIndex: Int, playerIndex: Int) -> Unit
) : RecyclerView.Adapter<GuandanCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCardName: TextView = itemView.findViewById(R.id.tvCardName)
        val tvRemaining: TextView = itemView.findViewById(R.id.tvRemaining)
        val tvUpper: TextView = itemView.findViewById(R.id.tvUpper)
        val tvSelf: TextView = itemView.findViewById(R.id.tvSelf)
        val tvPartner: TextView = itemView.findViewById(R.id.tvPartner)
        val tvLower: TextView = itemView.findViewById(R.id.tvLower)

        fun bind(card: GuandanCard, position: Int, listener: (Int, Int) -> Unit) {
            tvCardName.text = card.name
            tvRemaining.text = card.remaining.toString()

            tvUpper.text = card.playerCounts[0].toString()
            tvSelf.text = card.playerCounts[1].toString()
            tvPartner.text = card.playerCounts[2].toString()
            tvLower.text = card.playerCounts[3].toString()

            tvUpper.setOnClickListener { listener(position, 0) }
            tvSelf.setOnClickListener { listener(position, 1) }
            tvPartner.setOnClickListener { listener(position, 2) }
            tvLower.setOnClickListener { listener(position, 3) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guandan_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], position, onPlayerClick)
    }

    override fun getItemCount() = cards.size
}