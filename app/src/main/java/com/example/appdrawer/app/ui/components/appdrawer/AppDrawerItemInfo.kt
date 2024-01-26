package com.example.appdrawer.app.ui.components.appdrawer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    val title: String,
)