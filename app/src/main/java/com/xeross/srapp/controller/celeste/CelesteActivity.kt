package com.xeross.srapp.controller.celeste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.xeross.srapp.R
import com.xeross.srapp.adapter.PageAdapter
import com.xeross.srapp.controller.celeste.level.*

class CelesteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celeste)

        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager_levels)

        val fragmentList = arrayListOf(ForsakenCityFragment.getInstance(),
                OldCityFragment.getInstance(),
                CelestialResortFragment.getInstance(),
                GoldenRidgeFragment.getInstance(),
                MirrorTempleAFragment.getInstance(),
                MirrorTempleBFragment.getInstance(),
                ReflectionFragment.getInstance(),
                TheSummitFragment.getInstance(),
                CelesteRunFragment.getInstance())

        viewPager2.adapter = PageAdapter(this, fragmentList)
    }
}