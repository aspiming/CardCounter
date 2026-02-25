package com.example.cardcounter

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("card_counter_prefs", Context.MODE_PRIVATE)

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    // 副数属性：读取/写入 SharedPreferences，设置后自动刷新列表
    var decks: Int
        get() = prefs.getInt("decks", 1)
        set(value) {
            prefs.edit().putInt("decks", value).apply()
            refreshCards()
        }

    // 启用的牌型列表：存储为逗号分隔字符串
    var enabledCardNames: List<String>
        get() {
            val saved = prefs.getString("enabled_cards", null)
            return if (saved != null) {
                saved.split(",").filter { it.isNotEmpty() }
            } else {
                allCards // 默认全部启用
            }
        }
        set(value) {
            val toSave = value.joinToString(",")
            prefs.edit().putString("enabled_cards", toSave).apply()
            refreshCards()
        }

    private val allCards = listOf(
        "3", "4", "5", "6", "7", "8", "9", "10",
        "J", "Q", "K", "A", "2", "小王", "大王"
    )

    init {
        refreshCards()
    }

    private fun getBaseCount(name: String): Int =
        if (name == "小王" || name == "大王") 1 else 4

    private fun generateCards(): List<Card> =
        enabledCardNames.map { name ->
            Card(name, getBaseCount(name) * decks, getBaseCount(name))
        }

    private fun refreshCards() {
        _cards.value = generateCards()
    }

    fun updateEnabledCards(newEnabled: List<String>) {
        enabledCardNames = newEnabled
    }

    fun resetCards() {
        refreshCards()
    }

    fun incrementCard(position: Int) {
        _cards.value = _cards.value?.toMutableList()?.apply {
            val card = this[position]
            this[position] = card.copy(count = card.count + 1)
        }
    }

    fun decrementCard(position: Int) {
        _cards.value = _cards.value?.toMutableList()?.apply {
            val card = this[position]
            if (card.count > 0) {
                this[position] = card.copy(count = card.count - 1)
            }
        }
    }
}