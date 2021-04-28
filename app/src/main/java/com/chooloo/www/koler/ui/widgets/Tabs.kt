package com.chooloo.www.koler.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.chooloo.www.koler.R

class Tabs : LinearLayout {
    private var _tabs: Array<Tab> = arrayOf()
    private var _viewPager: ViewPager2? = null
    private val spacingSmall by lazy { resources.getDimensionPixelSize(R.dimen.default_spacing_small) }

    var viewPager: ViewPager2?
        get() = _viewPager
        set(value) {
            _viewPager = value
            _viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectTab(position)
                }
            })
        }

    var headers: Array<String>
        get() = _tabs.map { it.text.toString() }.toTypedArray()
        set(value) {
            updateTabs(value.map { getTab(it) }.toTypedArray())
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleRes) {
        orientation = HORIZONTAL
    }

    private fun updateTabs(tabs: Array<Tab>) {
        _tabs.forEach { removeView(it) }
        tabs.forEach { addView(it) }
        _tabs = tabs
    }

    private fun getTab(headerText: String) = Tab(context).apply {
        text = headerText
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            marginEnd = spacingSmall
        }
    }

    private fun selectTab(position: Int) {
        _tabs.forEachIndexed { index, tab -> tab.isEnabled = index == position }
    }
}