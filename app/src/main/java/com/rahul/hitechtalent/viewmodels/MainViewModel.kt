package com.rahul.hitechtalent.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.rahul.hitechtalent.models.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileModel())
    val uiState: StateFlow<ProfileModel> = _uiState.asStateFlow()

    fun setUserProfile(name: String, email: String, website: String, imageUri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                email = email,
                website = website,
                imageUri = imageUri
            )
        }
    }

    fun setProfile(){

    }
}