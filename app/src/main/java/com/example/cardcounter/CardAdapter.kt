package com.example.cardcounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(
    private val onPlusClick: (Int) -> Unit,
    private val onMinusClick: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    var cards: List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvCardName)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        private val btnPlus: Button = itemView.findViewById(R.id.btnPlus)
        private val btnMinus: Button = itemView.findViewById(R.id.btnMinus)

        fun bind(card: Card, onPlus: (Int) -> Unit, onMinus: (Int) -> Unit) {
            tvName.text = card.name
            tvCount.text = card.count.toString()
            btnPlus.setOnClickListener { onPlus(adapterPosition) }
            btnMinus.setOnClickListener { onMinus(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cards[position], onPlusClick, onMinusClick)
    }

    override fun getItemCount() = cards.size
}