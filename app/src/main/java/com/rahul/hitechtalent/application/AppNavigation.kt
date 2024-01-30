package com.rahul.hitechtalent.application

enum class Screen {
    SIGNUP,
    CONFIRM_PROFILE,
}
sealed class NavigationItem(val route: String) {
    object SignUp : NavigationItem(Screen.SIGNUP.name)
    object ConfirmProfile : NavigationItem(Screen.CONFIRM_PROFILE.name)
}