package com.example.cardcounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuandanCardAdapter(
    private val cards: List<GuandanCard>,
    private val onPlayerClick: (cardIndex: Int, playerIndex: Int) -> Unit // 回调：卡片位置，玩家索引
) : RecyclerView.Adapter<GuandanCardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCardName: TextView = itemView.findViewById(R.id.tvCardName)
        val tvRemaining: TextView = itemView.findViewById(R.id.tvRemaining)
        val btnUpper: Button = itemView.findViewById(R.id.btnUpper)
        val btnSelf: Button = itemView.findViewById(R.id.btnSelf)
        val btnPartner: Button = itemView.findViewById(R.id.btnPartner)
        val btnLower: Button = itemView.findViewById(R.id.btnLower)

        fun bind(card: GuandanCard, position: Int, listener: (Int, Int) -> Unit) {
            tvCardName.text = card.name
            tvRemaining.text = card.remaining.toString()
            btnUpper.text = "上家(${card.playerCounts[0]})"
            btnSelf.text = "自己(${card.playerCounts[1]})"
            btnPartner.text = "对家(${card.playerCounts[2]})"
            btnLower.text = "下家(${card.playerCounts[3]})"

            btnUpper.setOnClickListener { listener(position, 0) }
            btnSelf.setOnClickListener { listener(position, 1) }
            btnPartner.setOnClickListener { listener(position, 2) }
            btnLower.setOnClickListener { listener(position, 3) }
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