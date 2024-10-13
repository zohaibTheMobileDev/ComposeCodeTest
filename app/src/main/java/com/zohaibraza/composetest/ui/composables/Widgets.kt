package com.zohaibraza.composetest.ui.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zohaibraza.composetest.R
import com.zohaibraza.composetest.data.constants.Constants
import com.zohaibraza.composetest.data.constants.DimensDp
import com.zohaibraza.composetest.model.Greetings
import com.zohaibraza.composetest.model.LoadingState
import com.zohaibraza.composetest.model.Track
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    loadingState: LoadingState?,
    trackResponse: List<Track>?,
    onRetryClicked: () -> Unit,
    trackClicked: (Track) -> Unit,
    searchQuery: (String) -> Unit = {},
    favoriteToggle: (Track) -> Unit = {}
) {
    val mContext = LocalContext.current

    var typedSearchQuery by remember {
        mutableStateOf("")
    }

    // Create instance of DateTimeFormat
    val simpleDateFormat =
        SimpleDateFormat(Constants.LAST_OPENED_DATE_TIME_FORMAT, Locale.getDefault())

    // Using Calendar to get Current Hour to show Greetings Text
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.time = Date()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val greetingsText = when (currentHour) {
        in 0..11 -> Greetings.MORNING.message
        in 12..15 -> Greetings.AFTERNOON.message
        in 16..20 -> Greetings.EVENING.message
        in 21..23 -> Greetings.NIGHT.message
        else -> Greetings.HELLO.message
    }

    var lastOpened by remember {
        mutableStateOf(Greetings.HELLO.message)
    }

    // Using Shared Preference to save Last Used timestamp
    LaunchedEffect(key1 = Unit) {
        val sharedPreference = mContext.getSharedPreferences(
            Constants.SHARED_PREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )
        lastOpened = simpleDateFormat.format(
            sharedPreference.getLong(
                Constants.LAST_OPENED,
                System.currentTimeMillis()
            )
        )
        sharedPreference.edit().putLong(Constants.LAST_OPENED, System.currentTimeMillis()).apply()
    }


    Column(modifier = modifier.fillMaxSize()) {
        // TopBar to show Greetings Text & Last Used Time
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "$greetingsText, ${Constants.USER_NAME}",
                        fontSize = 14.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = DimensDp.DIMEN_5_DP)
                    )
                    Text(
                        text = stringResource(R.string.str_last_used, lastOpened),
                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                titleContentColor = Color.White,
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(size = 100.dp)),
            value = typedSearchQuery, onValueChange = {
                typedSearchQuery = it
                searchQuery(it)
            }, placeholder = {
                Text(text = stringResource(R.string.str_search_here))
            }, leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.str_search_movies)
                )
            }, singleLine = true, maxLines = 1, trailingIcon = {
                if (typedSearchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.str_cancel_search),
                        modifier = Modifier.clickable {
                            typedSearchQuery = ""
                        }
                    )
                }
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        TracksScreen(
            loadingState = loadingState,
            trackResponse = trackResponse,
            onRetryClicked = onRetryClicked,
            trackClicked = trackClicked,
            favoriteToggle = favoriteToggle
        )
    }

}


@Composable
fun TracksScreen(
    loadingState: LoadingState?,
    trackResponse: List<Track>?,
    onRetryClicked: () -> Unit,
    trackClicked: (Track) -> Unit,
    favoriteToggle: (Track) -> Unit = {}
) {
    if (loadingState == null || loadingState.inProgress) {
        ProgressBar()
    } else if (loadingState.isError) {
        PlaceHolder(loadingState = loadingState, onRetryClicked = onRetryClicked)
    } else {
        TracksList(
            tracksList = trackResponse, onRetryClicked = onRetryClicked,
            trackClicked = trackClicked, favoriteToggle = favoriteToggle
        )
    }
}


@Composable
fun ProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PlaceHolder(loadingState: LoadingState, onRetryClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = android.R.drawable.stat_notify_error),
            contentDescription = "Error Fetching Tracks"
        )
        Text(
            text = if (loadingState.isError) {
                loadingState.errorMessage ?: "Error Fetching Tracks"
            } else {
                "No Data Found"
            }
        )
        Button(
            onClick = onRetryClicked,
            shape = RectangleShape
        ) {
            Text(text = "Retry")
        }
    }
}


/**
 * Below are all Previews
 * */

@Preview
@Composable
private fun PreviewProgressBar() {
    ProgressBar()
}