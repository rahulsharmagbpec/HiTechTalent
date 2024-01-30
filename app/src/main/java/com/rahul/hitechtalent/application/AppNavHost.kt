package com.rahul.hitechtalent.application

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rahul.hitechtalent.views.ConfirmationComposable
import com.rahul.hitechtalent.views.SignUpComposable
import com.rahul.hitechtalent.viewmodels.MainViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.SignUp.route,
) {
    val viewModel: MainViewModel = hiltViewModel<MainViewModel>()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.SignUp.route) {
            SignUpComposable(navController, viewModel::setUserProfile)
        }
        composable(NavigationItem.ConfirmProfile.route) {
            ConfirmationComposable(viewModel.uiState)
        }
    }
}