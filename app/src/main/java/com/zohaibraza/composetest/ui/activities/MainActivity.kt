package com.zohaibraza.composetest.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zohaibraza.composetest.model.Track
import com.zohaibraza.composetest.data.constants.Screens
import com.zohaibraza.composetest.ui.composables.HomeScreen
import com.zohaibraza.composetest.ui.composables.LoginForm
import com.zohaibraza.composetest.ui.composables.TrackDetailScreen
import com.zohaibraza.composetest.ui.theme.ComposeCodingTextTheme
import com.zohaibraza.composetest.ui.viewmodels.TracksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val tracksViewModel: TracksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodingTextTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screens.LOGIN_SCREEN
                    ) {

                        composable(
                            route = Screens.LOGIN_SCREEN,
                        ) {
                            LoginForm(
                                validationPassed = {
                                    navController.navigate(
                                        Screens.HOME_SCREEN
                                    ) {
                                    }
                                }
                            )
                        }

                        composable(Screens.HOME_SCREEN) {
                            TracksScreen(
                                modifier = Modifier.padding(innerPadding),
                                tracksViewModel = tracksViewModel,
                                trackClicked = { track ->
                                    tracksViewModel.detailTrack = track
                                    navController.navigate(
                                        Screens.DETAIL_SCREEN
                                    ) {
                                    }
                                }
                            )
                        }

                        composable(
                            route = Screens.DETAIL_SCREEN,
                        ) {
                            TrackDetailScreen(
                                track = tracksViewModel.detailTrack,
                                toggleFavourite = { track ->
                                    tracksViewModel.toggleFavoriteStatus(track = track)
                                }
                            )
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun TracksScreen(
    modifier: Modifier = Modifier, tracksViewModel: TracksViewModel,
    trackClicked: (Track) -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val fetchingTrackState = tracksViewModel.fetchingTracksState.observeAsState()
    val tracksResponse = tracksViewModel.trackResponse.observeAsState()

    LaunchedEffect(Unit) {
        tracksViewModel.fetchTracks()
        tracksViewModel.searchQuery.observe(lifeCycleOwner) { query ->
            if (query.isNotEmpty()) {
                tracksViewModel.fetchTracks()
            }
        }
    }
    HomeScreen(
        modifier = modifier,
        loadingState = fetchingTrackState.value,
        trackResponse = tracksResponse.value,
        onRetryClicked = {
            tracksViewModel.fetchTracks()
        }, trackClicked = trackClicked,
        searchQuery = {
            tracksViewModel.updateSearchQuery(it)
        }, favoriteToggle = { track ->
            tracksViewModel.toggleFavoriteStatus(track)
        }
    )
}
