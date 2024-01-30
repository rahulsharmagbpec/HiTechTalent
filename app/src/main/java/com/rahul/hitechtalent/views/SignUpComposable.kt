package com.rahul.hitechtalent.views

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.rahul.hitechtalent.util.Constants
import com.rahul.hitechtalent.R
import com.rahul.hitechtalent.application.Screen
import com.rahul.hitechtalent.util.createImageFile
import com.rahul.hitechtalent.util.isValidPassword
import com.rahul.hitechtalent.util.showShortToast
import java.util.Objects
import kotlin.reflect.KFunction4

@Preview
@Composable
fun SignUpComposablePreview() {
    //SignUpComposable(navController) { }
}

@Composable
fun SignUpComposable(
    navController: NavHostController,
    updateProfile: KFunction4<String, String, String, Uri, Unit>,
) {
    val context = LocalContext.current
    val file = context.createImageFile()

    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.rahul.hitechtalent.provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val firstNameState: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val emailState: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val passwordState: MutableState<String> = rememberSaveable { mutableStateOf("") }
    val websiteState: MutableState<String> = rememberSaveable { mutableStateOf("") }

    fun updateFirstName(firstName: String) {
        firstNameState.value = firstName
    }

    fun updateEmail(text: String) {
        emailState.value = text
    }

    fun updatePassword(text: String) {
        passwordState.value = text
    }

    fun updateWebsite(text: String) {
        websiteState.value = text
    }

    fun onSubmitButtonClicked() {
        val name: String = firstNameState.value
        val email: String = emailState.value
        val password: String = passwordState.value
        val website: String = websiteState.value
        if (name.isEmpty()) {
            Constants.nameError.showShortToast(context = context)
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Constants.emailError.showShortToast(context = context)
        } else if (!isValidPassword(password = password)) {
            Constants.passwordError.showShortToast(context = context)
        } else if (!URLUtil.isValidUrl(website)) {
            Constants.websiteError.showShortToast(context = context)
        } else if (capturedImageUri.path == null) {
            Constants.imageRequiredError.showShortToast(context = context)
        } else {
            updateProfile.invoke(
                firstNameState.value,
                emailState.value,
                websiteState.value,
                capturedImageUri
            )
            navController.navigate(Screen.CONFIRM_PROFILE.name)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), reverseScrolling = true)
            .height(IntrinsicSize.Max)

    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 20.dp)

        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.profile_creation),
                fontSize = dimensionResource(id = R.dimen.title_font_size).value.sp
            )
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            Text(text = stringResource(id = R.string.submit_portfolio))
            Text(text = stringResource(id = R.string.email_password_Required))
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(140.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        },
                    text = stringResource(id = R.string.tap_to_add_avatar)
                )
                if (capturedImageUri.path?.isNotEmpty() == true)
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(capturedImageUri),
                        contentDescription = "Add avatar"
                    )
            }

            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                value = firstNameState.value,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { updateFirstName(it) },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.first_name_placeholder),
                        fontSize = 14.sp
                    )
                })
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = emailState.value,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { updateEmail(it) },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.email_text_placeholder),
                        fontSize = 14.sp
                    )
                })
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                value = passwordState.value,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { updatePassword(it) },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.password_text_placeholder),
                        fontSize = 14.sp
                    )
                })
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                value = websiteState.value,
                shape = RoundedCornerShape(12.dp),
                onValueChange = { updateWebsite(it) },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.website_text_placeholder),
                        fontSize = 14.sp
                    )
                })
            SpaceHeight(height = 70.dp)
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Constants.gradient),

                onClick = { onSubmitButtonClicked() }
            ) {
                Text("Submit")
            }
        }
    }
}



@Composable
fun SpaceHeight(height: Dp) {
    Spacer(modifier = Modifier.height(height = height))
}

