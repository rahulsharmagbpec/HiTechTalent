package com.rahul.hitechtalent.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

class Constants {
    companion object {
        const val nameError: String = "Name cannot be empty"
        const val emailError: String = "Email is not valid"
        const val passwordError: String = "Password should contain letter and digit"
        const val websiteError: String = "Website is not valid"
        const val imageRequiredError:String = "Profile picture is required"
        val gradient: Brush = Brush.horizontalGradient(listOf(Color(0xFFFF5E13), Color(0xFFEB2121)))
    }
}