package com.example.cardcounter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2  // 两个页面

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OriginalCounterFragment()  // 第一个页面：普通记牌器
            1 -> GuandanFragment()          // 第二个页面：掼蛋记牌器
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}