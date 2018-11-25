package com.marcobrenes.mobileui.ext

import android.app.Activity
import androidx.test.rule.ActivityTestRule

@Suppress("TestFunctionName")
internal inline fun <reified A: Activity> ActivityTestRule(
        initialTouchMode: Boolean = false,
        launchActivity: Boolean = true)
        = ActivityTestRule(A::class.java, initialTouchMode, launchActivity)