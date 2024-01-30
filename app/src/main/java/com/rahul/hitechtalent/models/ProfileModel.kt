package com.rahul.hitechtalent.models

import android.net.Uri

data class ProfileModel(
    val name: String = "",
    val email: String = "",
    val website: String = "",
    val imageUri: Uri? = null
)
