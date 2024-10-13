package com.zohaibraza.composetest.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zohaibraza.composetest.R
import com.zohaibraza.composetest.data.constants.DimensDp
import com.zohaibraza.composetest.model.LoadingState
import com.zohaibraza.composetest.model.Track
import com.zohaibraza.composetest.ui.theme.ComposeCodingTextTheme

@Composable
fun TracksList(
    tracksList: List<Track>?,
    onRetryClicked: () -> Unit,
    trackClicked: (Track) -> Unit,
    favoriteToggle: (Track) -> Unit = {}
) {
    if (tracksList.isNullOrEmpty()) {
        PlaceHolder(
            loadingState = LoadingState(
                isError = false
            ), onRetryClicked = onRetryClicked
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            tracksList.forEach { track ->
                item {
                    TrackItem(track, trackClicked, favoriteToggle)
                }
            }
        }
    }
}

@Composable
fun TrackItem(
    track: Track, onTrackClicked: (track: Track) -> Unit = {},
    favoriteToggle: (Track) -> Unit = {}
) {
    var isFavourite by remember { mutableStateOf(track.isFavourite) }

    Card(
        shape = RoundedCornerShape(corner = CornerSize(DimensDp.DIMEN_10_DP)),
        onClick = {
            onTrackClicked(track)
        }, elevation = CardDefaults.cardElevation(
            defaultElevation = DimensDp.DIMEN_2_DP
        ), colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(DimensDp.DIMEN_5_DP)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                AsyncImage(
                    model = track.artworkUrl100,
                    contentDescription = stringResource(
                        id = R.string.str_artwork_image_of,
                        track.trackName ?: ""
                    ),
                    modifier = Modifier
                        .size(DimensDp.ARTWORK_SIZE_IN_DP)
                        .clip(RoundedCornerShape(DimensDp.DIMEN_10_DP))
                        .background(Color.Gray),
                    contentScale = ContentScale.FillBounds,
                    error = painterResource(id = android.R.drawable.stat_notify_error)
                )
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text(
                        text = track.trackName ?: stringResource(id = R.string.str_n_a),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = DimensDp.DIMEN_5_DP)
                    )
                    Text(
                        text = "$${track.trackPrice ?: stringResource(id = R.string.str_default_price)}",
                        color = Color.DarkGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                text = track.primaryGenreName
                                    ?: stringResource(id = R.string.str_n_a)
                            )
                        })
                }
                Icon(
                    if (isFavourite)
                        Icons.Rounded.Favorite
                    else
                        Icons.Rounded.FavoriteBorder,
                    contentDescription = if (isFavourite) stringResource(id = R.string.str_remove_from_favourites)
                    else
                        stringResource(id = R.string.str_add_to_favourites),
                    tint = Color.Black,
                    modifier = Modifier
                        .clickable {
                            isFavourite = !isFavourite
                            favoriteToggle(track)
                        }
                        .padding(end = DimensDp.DIMEN_5_DP)
                        .padding(top = DimensDp.DIMEN_5_DP)
                )
            }
        }
    }
}

/**
 * Preview of Track Time
 * */

@Preview
@Composable
private fun PreviewTrackItem() {
    ComposeCodingTextTheme {
        TrackItem(
            track = Track().apply {
                trackName = "Everything Everywhere All at once - Time Machine"
                trackPrice = 154.99
                primaryGenreName = "Action, Sci-Fi, Time Travel"
            }
        )
    }
}