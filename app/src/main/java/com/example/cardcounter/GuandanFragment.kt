package com.example.cardcounter

import android.app.AlertDialog
import android.content.Context
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

    // 所有卡片数据（固定）
    private val allCards = mutableListOf<GuandanCard>()

    // 当前显示的卡片列表（根据编辑牌型过滤）
    private var visibleCards = mutableListOf<GuandanCard>()

    private lateinit var adapter: GuandanCardAdapter

    // SharedPreferences 用于保存牌型选择
    private val prefs by lazy {
        requireActivity().getSharedPreferences("guandan_prefs", Context.MODE_PRIVATE)
    }

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

        initAllCards()
        loadVisibleCards()  // 加载保存的牌型
        setupRecyclerView()
        setupButtons()
    }

    private fun initAllCards() {
        val cardNames = listOf(
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A",
            "级牌", "小王", "大王", "红心"
        )
        val initialCounts = listOf(
            8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
            6, 2, 2, 2
        )
        for (i in cardNames.indices) {
            allCards.add(
                GuandanCard(
                    name = cardNames[i],
                    initialCount = initialCounts[i],
                    remaining = initialCounts[i],
                    playerCounts = IntArray(4)
                )
            )
        }
    }

    /**
     * 从 SharedPreferences 读取保存的牌名列表，构造 visibleCards
     * 如果没有保存，则显示全部
     */
    private fun loadVisibleCards() {
        val saved = prefs.getString("enabled_card_names", null)
        visibleCards = if (saved != null) {
            val enabledNames = saved.split(",").filter { it.isNotEmpty() }
            allCards.filter { it.name in enabledNames }.toMutableList()
        } else {
            allCards.toMutableList() // 默认全部
        }
    }

    /**
     * 保存当前选中的牌名列表到 SharedPreferences
     */
    private fun saveEnabledCardNames(names: List<String>) {
        val toSave = names.joinToString(",")
        prefs.edit().putString("enabled_card_names", toSave).apply()
    }

    private fun setupRecyclerView() {
        adapter = GuandanCardAdapter(visibleCards) { cardIndex, playerIndex ->
            val card = visibleCards[cardIndex]
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

    private fun setupButtons() {
        binding.btnEditCards.setOnClickListener { showEditCardDialog() }
        binding.btnReset.setOnClickListener {
            // 重置所有卡片到初始值
            for (card in allCards) {
                card.remaining = card.initialCount
                card.playerCounts.fill(0)
            }
            // 如果当前有过滤，可见列表中的卡片也会被更新（因为对象相同）
            adapter.notifyDataSetChanged()
        }
    }

    private fun showEditCardDialog() {
        val allNames = allCards.map { it.name }.toTypedArray()
        // 当前可见的牌名集合
        val visibleNames = visibleCards.map { it.name }.toSet()
        val checkedItems = BooleanArray(allNames.size) { index ->
            allNames[index] in visibleNames
        }

        AlertDialog.Builder(requireContext())
            .setTitle("选择要显示的牌")
            .setMultiChoiceItems(allNames, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton("确定") { _, _ ->
                // 根据勾选结果生成新的 visibleCards
                val newVisibleNames = allNames.filterIndexed { index, _ -> checkedItems[index] }
                // 保存到 SharedPreferences
                saveEnabledCardNames(newVisibleNames)
                // 更新 visibleCards
                visibleCards = allCards.filter { it.name in newVisibleNames }.toMutableList()
                // 重新设置适配器（或 notifyDataSetChanged，但需重新创建适配器以保证数据源更新）
                adapter = GuandanCardAdapter(visibleCards) { cardIndex, playerIndex ->
                    val card = visibleCards[cardIndex]
                    if (card.remaining > 0) {
                        card.remaining--
                        card.playerCounts[playerIndex]++
                        adapter.notifyItemChanged(cardIndex)
                    } else {
                        Toast.makeText(requireContext(), "该牌已无剩余", Toast.LENGTH_SHORT).show()
                    }
                }
                binding.recyclerView.adapter = adapter
            }
            .setNegativeButton("取消", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}