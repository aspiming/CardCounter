package com.example.cardcounter

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CardViewModel
    private lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(CardViewModel::class.java)

        setupRecyclerView()
        setupButtons()

        viewModel.cards.observe(this) { cards ->
            adapter.cards = cards
        }
    }

    private fun setupRecyclerView() {
        adapter = CardAdapter(
            onPlusClick = { position -> viewModel.incrementCard(position) },
            onMinusClick = { position -> viewModel.decrementCard(position) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnReset.setOnClickListener { viewModel.resetCards() }

        binding.btnDecks.setOnClickListener { showDeckDialog() }

        binding.btnEdit.setOnClickListener { showEditDialog() }
    }

    private fun showDeckDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        AlertDialog.Builder(this)
            .setTitle("设置副数")
            .setView(input)
            .setPositiveButton("确定") { _, _ ->
                val decks = input.text.toString().toIntOrNull()
                if (decks != null && decks >= 1) {
                    viewModel.decks = decks      // 直接赋值给属性
                    binding.btnDecks.text = "副数:$decks"
                } else {
                    Toast.makeText(this, "请输入大于等于1的数字", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showEditDialog() {
        // 所有可能的牌名（按固定顺序）
        val allNames = listOf(
            "3", "4", "5", "6", "7", "8", "9", "10",
            "J", "Q", "K", "A", "2", "小王", "大王"
        )
        // 获取当前启用的牌名（从 ViewModel 中获取当前卡片列表）
        val currentEnabled = viewModel.cards.value?.map { it.name } ?: allNames
        val checkedItems = BooleanArray(allNames.size) { index ->
            allNames[index] in currentEnabled
        }

        AlertDialog.Builder(this)
            .setTitle("选择显示的牌")
            .setMultiChoiceItems(allNames.toTypedArray(), checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton("确定") { _, _ ->
                val newEnabled = allNames.filterIndexed { index, _ -> checkedItems[index] }
                viewModel.updateEnabledCards(newEnabled)
            }
            .setNegativeButton("取消", null)
            .show()
    }
}