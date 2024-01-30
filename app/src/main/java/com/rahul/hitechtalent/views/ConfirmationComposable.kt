package com.rahul.hitechtalent.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.rahul.hitechtalent.models.ProfileModel
import com.rahul.hitechtalent.R
import com.rahul.hitechtalent.util.Constants
import com.rahul.hitechtalent.util.showShortToast
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun PreviewConfirmationComposable() {
    //ConfirmationComposable()
}

@Composable
fun ConfirmationComposable(uiState: StateFlow<ProfileModel>) {

    val profileModel: ProfileModel = uiState.collectAsState().value


    fun onSubmitButtonClicked() {

    }

    Box(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.hello) + " " + profileModel.name,
                fontSize = dimensionResource(id = R.dimen.title_font_size).value.sp
            )
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            Text(text = stringResource(id = R.string.profile_submitted))
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(140.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {

                if (profileModel.imageUri?.path?.isNotEmpty() == true)
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(profileModel.imageUri),
                        contentDescription = "Add avatar"
                    )
            }

            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = profileModel.email,
            )
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = profileModel.name,
            )
            SpaceHeight(height = dimensionResource(id = R.dimen.space_height))
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = profileModel.website,
            )
        }

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp, start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Constants.gradient)
                .align(Alignment.BottomCenter),
            onClick = { onSubmitButtonClicked() }
        ) {
            Text("Sign In")
        }
    }
}