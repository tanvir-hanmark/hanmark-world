package com.hanmark.world

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    companion object {
        private const val FACEBOOK_URL = "https://www.facebook.com/mdtanvirhossaninfo"
        private const val MATTE_BLACK = 0xFF0B0B0B.toInt()
        private const val NEON_GREEN = 0xFF39FF14.toInt()
        private const val LIGHT_GRAY = 0xFFB8B8B8.toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(MATTE_BLACK))
        setSystemBars()
        setContentView(buildScreen())
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun buildScreen(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(MATTE_BLACK)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        applyInsetsPadding(root, dp(24))

        val title = TextView(this).apply {
            text = "HANMARK WORLD"
            setTextColor(NEON_GREEN)
            textSize = 30f
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            letterSpacing = 0.12f
            gravity = Gravity.CENTER
        }

        val divider = View(this).apply {
            setBackgroundColor(NEON_GREEN)
            layoutParams = LinearLayout.LayoutParams(dp(64), dp(2)).apply {
                topMargin = dp(16)
                bottomMargin = dp(16)
            }
        }

        val creator = TextView(this).apply {
            text = "Created by MD Tanvir Hossan"
            setTextColor(Color.WHITE)
            textSize = 16f
            gravity = Gravity.CENTER
        }

        val link = TextView(this).apply {
            text = "facebook.com/mdtanvirhossaninfo"
            setTextColor(NEON_GREEN)
            textSize = 15f
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
            gravity = Gravity.CENTER
            minHeight = dp(48)
            setPadding(dp(16), dp(12), dp(16), dp(12))
            isClickable = true
            isFocusable = true
            setOnClickListener { openFacebook() }
        }

        val hint = TextView(this).apply {
            text = "Tap the link to open Facebook"
            setTextColor(LIGHT_GRAY)
            textSize = 12f
            gravity = Gravity.CENTER
        }

        root.addView(title)
        root.addView(divider)
        root.addView(creator)
        root.addView(link)
        root.addView(hint)
        return root
    }

    // Opens the Facebook app if it handles this link, otherwise the browser.
    private fun openFacebook() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No browser or Facebook app found", Toast.LENGTH_LONG).show()
        }
    }

    // Keeps content clear of the status bar and navigation bar.
    @Suppress("DEPRECATION")
    private fun applyInsetsPadding(view: View, basePadding: Int) {
        view.setOnApplyWindowInsetsListener { v, insets ->
            val left: Int
            val top: Int
            val right: Int
            val bottom: Int
            if (Build.VERSION.SDK_INT >= 30) {
                val bars = insets.getInsets(WindowInsets.Type.systemBars())
                left = bars.left
                top = bars.top
                right = bars.right
                bottom = bars.bottom
            } else {
                left = insets.systemWindowInsetLeft
                top = insets.systemWindowInsetTop
                right = insets.systemWindowInsetRight
                bottom = insets.systemWindowInsetBottom
            }
            v.setPadding(left + basePadding, top + basePadding, right + basePadding, bottom + basePadding)
            insets
        }
    }

    // Black bars with light icons so they stay visible on the dark background.
    @Suppress("DEPRECATION")
    private fun setSystemBars() {
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK
        if (Build.VERSION.SDK_INT >= 30) {
            window.insetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
        } else {
            window.decorView.systemUiVisibility = 0
        }
    }
}
