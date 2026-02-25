package com.example.cardcounter

data class GuandanCard(
    val name: String,                // 牌面名称，如 "2", "小王"
    val initialCount: Int,            // 初始数量（2-A为8，级牌6，大小王各2，红心2）
    var remaining: Int,               // 当前剩余数量
    val playerCounts: IntArray = IntArray(4) // 四个玩家的出牌计数，索引0=上家，1=自己，2=对家，3=下家
)