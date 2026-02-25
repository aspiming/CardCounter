package com.example.cardcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.cardcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 创建适配器并设置给 ViewPager2
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        // 将 TabLayout 与 ViewPager2 关联，并设置标签文字
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "普通记牌"
                1 -> "掼蛋记牌"
                else -> ""
            }
        }.attach()
    }
}