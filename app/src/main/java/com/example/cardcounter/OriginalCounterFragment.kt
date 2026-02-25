package com.example.cardcounter

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardcounter.databinding.FragmentOriginalBinding

class OriginalCounterFragment : Fragment() {

    private var _binding: FragmentOriginalBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CardViewModel
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOriginalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化 ViewModel（使用 requireActivity().application 以便在 Fragment 中使用 AndroidViewModel）
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CardViewModel::class.java)

        setupRecyclerView()
        setupButtons()

        // 观察卡片数据变化，刷新列表
        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            adapter.cards = cards
        }

        // 设置副数按钮文本为当前保存的副数
        binding.btnDecks.text = "副数:${viewModel.decks}"
    }

    private fun setupRecyclerView() {
        adapter = CardAdapter(
            onPlusClick = { position -> viewModel.incrementCard(position) },
            onMinusClick = { position -> viewModel.decrementCard(position) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnReset.setOnClickListener { viewModel.resetCards() }
        binding.btnDecks.setOnClickListener { showDeckDialog() }
        binding.btnEdit.setOnClickListener { showEditDialog() }
    }

    private fun showDeckDialog() {
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_NUMBER
        AlertDialog.Builder(requireContext())
            .setTitle("设置副数")
            .setView(input)
            .setPositiveButton("确定") { _, _ ->
                val decks = input.text.toString().toIntOrNull()
                if (decks != null && decks >= 1) {
                    viewModel.decks = decks
                    binding.btnDecks.text = "副数:$decks"
                } else {
                    Toast.makeText(requireContext(), "请输入大于等于1的数字", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun showEditDialog() {
        val allNames = listOf(
            "3", "4", "5", "6", "7", "8", "9", "10",
            "J", "Q", "K", "A", "2", "小王", "大王"
        )
        val currentEnabled = viewModel.cards.value?.map { it.name } ?: allNames
        val checkedItems = BooleanArray(allNames.size) { index ->
            allNames[index] in currentEnabled
        }

        AlertDialog.Builder(requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}