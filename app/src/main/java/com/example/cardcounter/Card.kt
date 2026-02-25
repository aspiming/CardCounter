package com.example.cardcounter

data class Card(
    val name: String,      // 牌面名称，如 "3", "小王"
    var count: Int,        // 当前剩余数量
    val baseCount: Int     // 一副牌的基础数量（数字牌=4，大小王=1）
)
