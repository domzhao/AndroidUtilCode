package com.blankj.utilcode.pkg.feature.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import com.blankj.lib.base.BaseDrawerActivity
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_bar_status_drawer.*


/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : demo about BarUtils
 * ```
 */
class BarStatusDrawerActivity : BaseDrawerActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BarStatusDrawerActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private val mColorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            updateStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }

    private var mAlphaCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            barStatusAlphaChangeAlphaSb.visibility = View.VISIBLE
            barStatusAlphaRandomColorBtn.visibility = View.GONE
            barStatusAlphaSetTransparentBtn.visibility = View.VISIBLE

            mDrawerContainerView.setBackgroundResource(R.drawable.bar_status_alpha_bg)
        } else {
            barStatusAlphaChangeAlphaSb.visibility = View.GONE
            barStatusAlphaRandomColorBtn.visibility = View.VISIBLE
            barStatusAlphaSetTransparentBtn.visibility = View.GONE

            mDrawerContainerView.setBackgroundColor(Color.WHITE)
        }
        updateStatusBar()
    }

    private var mFrontCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { _, _ -> updateStatusBar() }

    override fun initData(bundle: Bundle?) {
        mColor = ContextCompat.getColor(Utils.getApp(), R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_drawer
    }

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        barStatusDrawerAlphaCb.setOnCheckedChangeListener(mAlphaCheckedChangeListener)
        barStatusDrawerFrontCb.setOnCheckedChangeListener(mFrontCheckedChangeListener)
        barStatusAlphaChangeAlphaSb.setOnSeekBarChangeListener(mColorListener)
        barStatusAlphaRandomColorBtn.setOnClickListener(this)
        barStatusAlphaSetTransparentBtn.setOnClickListener(this)

        barStatusAlphaChangeAlphaSb.visibility = View.GONE
        barStatusAlphaSetTransparentBtn.visibility = View.GONE

        updateStatusBar()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.randomColorBtn -> {
                mColor = ColorUtils.getRandomColor()
                updateStatusBar()
            }
            R.id.setTransparentBtn -> barStatusAlphaChangeAlphaSb.progress = 0
        }
    }

    private fun updateStatusBar() {
        if (barStatusDrawerAlphaCb.isChecked) {
            BarUtils.setStatusBarColor4Drawer(mDrawerRootLayout, barStatusDrawerFakeStatusBar, Color.argb(mAlpha, 0, 0, 0), barStatusDrawerFrontCb.isChecked)
            barStatusDrawerAboutTv.text = mAlpha.toString()
        } else {
            BarUtils.setStatusBarColor4Drawer(mDrawerRootLayout, barStatusDrawerFakeStatusBar, mColor, barStatusDrawerFrontCb.isChecked)
            barStatusDrawerAboutTv.text = ColorUtils.int2ArgbString(mColor)
        }
        BarUtils.addMarginTopEqualStatusBarHeight(barStatusDrawerAlphaCb)// 其实这个只需要调用一次即可
    }
}