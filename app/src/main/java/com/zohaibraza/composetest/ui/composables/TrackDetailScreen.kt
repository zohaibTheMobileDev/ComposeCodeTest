package com.zohaibraza.composetest.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zohaibraza.composetest.R
import com.zohaibraza.composetest.data.constants.DimensDp
import com.zohaibraza.composetest.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailScreen(
    track: Track?,
    toggleFavourite: (Track) -> Unit
) {
    if (track == null) {
        return
    }

    var isFavourite by remember {
        mutableStateOf(track.isFavourite)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.str_track_detail))
                    Spacer(Modifier.weight(1f))
                    Icon(
                        if (isFavourite)
                            Icons.Rounded.Favorite
                        else
                            Icons.Rounded.FavoriteBorder,
                        contentDescription = if (isFavourite) stringResource(id = R.string.str_remove_from_favourites) else
                            stringResource(id = R.string.str_add_to_favourites),
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                                isFavourite = !isFavourite
                                toggleFavourite(track)
                            }
                            .padding(end = 10.dp)
                    )
                }
            })
        AsyncImage(
            model = track.artworkUrl100,
            contentDescription = stringResource(id = R.string.str_artwork_image_of, track.trackName ?: ""),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.85f)
                .background(Color.Gray),
            contentScale = ContentScale.FillBounds,
            error = painterResource(id = android.R.drawable.stat_notify_error)
        )
        Spacer(Modifier.size(DimensDp.DIMEN_20_DP))
        Text(
            text = track.trackName ?: stringResource(id = R.string.str_n_a), color = Color.Black,
            fontSize = 20.sp, fontWeight = FontWeight.Bold,
            maxLines = 2, overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$${track.trackPrice ?: stringResource(id = R.string.str_default_price)}", color = Color.DarkGray,
                fontSize = 18.sp, fontWeight = FontWeight.W900,
                modifier = Modifier.padding(start = DimensDp.DIMEN_10_DP)

            )
            Spacer(Modifier.weight(1f))
            SuggestionChip(
                onClick = {},
                label = { Text(text = track.primaryGenreName ?: "Genre: N/A") },
                modifier = Modifier.padding(end = DimensDp.DIMEN_10_DP)
            )
        }
        Spacer(Modifier.size(DimensDp.DIMEN_5_DP))
        Text(
            text = track.shortDescription ?: "", color = Color.DarkGray,
            fontSize = 18.sp, fontWeight = FontWeight.W900,
            modifier = Modifier.padding(horizontal = DimensDp.DIMEN_10_DP)
        )
        Spacer(Modifier.size(DimensDp.DIMEN_15_DP))
        Text(
            text = track.longDescription ?: "", color = Color.DarkGray,
            fontSize = 16.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = DimensDp.DIMEN_10_DP)
        )
    }

}