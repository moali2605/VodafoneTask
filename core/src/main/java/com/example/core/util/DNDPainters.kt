package com.example.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.core.R


object Painters {

    val homeBg
        @Composable
        get() = painterResource(id = R.drawable.homebg)

    val daysTempBg
        @Composable
        get() = painterResource(id = R.drawable.days_temp_bg)
}