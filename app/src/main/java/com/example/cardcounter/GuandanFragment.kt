package com.example.cardcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardcounter.databinding.FragmentGuandanBinding

class GuandanFragment : Fragment() {

    private var _binding: FragmentGuandanBinding? = null
    private val binding get() = _binding!!

    // 卡片数据列表
    private val cardList = mutableListOf<GuandanCard>()
    private lateinit var adapter: GuandanCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuandanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCardData()
        setupRecyclerView()
        setupResetButton()
    }

    private fun initCardData() {
        val cardNames = listOf(
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A",
            "级牌", "小王", "大王", "红心"
        )
        // 初始数量：2-A各8张，级牌6，大小王各2，红心2
        val initialCounts = listOf(
            8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
            6, 2, 2, 2
        )
        for (i in cardNames.indices) {
            cardList.add(
                GuandanCard(
                    name = cardNames[i],
                    initialCount = initialCounts[i],
                    remaining = initialCounts[i],
                    playerCounts = IntArray(4) // 默认为0
                )
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = GuandanCardAdapter(cardList) { cardIndex, playerIndex ->
            // 点击玩家按钮的逻辑
            val card = cardList[cardIndex]
            if (card.remaining > 0) {
                card.remaining--
                card.playerCounts[playerIndex]++
                adapter.notifyItemChanged(cardIndex)
            } else {
                Toast.makeText(requireContext(), "该牌已无剩余", Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupResetButton() {
        binding.btnReset.setOnClickListener {
            // 重置所有卡片到初始状态
            for (card in cardList) {
                card.remaining = card.initialCount
                card.playerCounts.fill(0)
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}