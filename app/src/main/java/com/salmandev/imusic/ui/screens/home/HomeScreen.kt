package com.salmandev.imusic.ui.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salmandev.imusic.core.Constants
import com.salmandev.imusic.core.Utils
import com.salmandev.imusic.ui.components.*
import com.salmandev.imusic.ui.navigation.Screen
import com.salmandev.imusic.ui.theme.SpotifyGreen
import com.salmandev.imusic.viewmodel.HomeViewModel
import com.salmandev.imusic.viewmodel.PlayerViewModel

val genreColors = listOf(
    Color(0xFF1DB954), Color(0xFF3498DB), Color(0xFF9B59B6),
    Color(0xFFE74C3C), Color(0xFFF39C12), Color(0xFF1ABC9C),
    Color(0xFFE67E22), Color(0xFF2ECC71), Color(0xFF8E44AD),
    Color(0xFF16A085), Color(0xFFC0392B), Color(0xFF2980B9)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    playerViewModel: PlayerViewModel,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trendingTracks by viewModel.trendingTracks.collectAsState()
    val newReleases by viewModel.newReleases.collectAsState()
    val featuredArtists by viewModel.featuredArtists.collectAsState()
    val newAlbums by viewModel.newAlbums.collectAsState()
    val recentlyPlayed by viewModel.recentlyPlayed.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val currentTrack by playerViewModel.currentTrack.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = Utils.getGreeting(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "iMusic",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = SpotifyGreen
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(Icons.Filled.Settings, "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        when {
            isLoading && trendingTracks.isEmpty() -> LoadingIndicator()
            error != null && trendingTracks.isEmpty() -> ErrorState(
                message = error ?: "Failed to load",
                onRetry = { viewModel.loadHomeData() }
            )
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {

                    // Recently Played
                    if (recentlyPlayed.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Recently Played")
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(recentlyPlayed.take(8)) { track ->
                                    MusicCard(
                                        imageUrl = track.albumArtUrl,
                                        title = track.name,
                                        subtitle = track.artistName,
                                        onClick = { playerViewModel.playTrack(track, recentlyPlayed) },
                                        cardWidth = 120
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Trending Now
                    item {
                        SectionHeader(
                            title = "🔥 Trending Now",
                            onSeeAllClick = {}
                        )
                    }

                    items(trendingTracks.take(6)) { track ->
                        TrackListItem(
                            track = track,
                            onTrackClick = { playerViewModel.playTrack(it, trendingTracks) },
                            isPlaying = currentTrack?.id == track.id,
                            onMoreClick = {}
                        )
                    }

                    // Featured Artists
                    if (featuredArtists.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            SectionHeader(title = "🎤 Featured Artists")
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(featuredArtists.take(8)) { artist ->
                                    ArtistCard(
                                        imageUrl = artist.imageUrl,
                                        name = artist.name,
                                        onClick = {}
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // New Albums
                    if (newAlbums.isNotEmpty()) {
                        item {
                            SectionHeader(title = "💿 New Albums")
                        }
                        item {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(newAlbums.take(8)) { album ->
                                    MusicCard(
                                        imageUrl = album.coverUrl,
                                        title = album.name,
                                        subtitle = album.artistName,
                                        onClick = {}
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Browse Genres
                    item {
                        SectionHeader(title = "🎵 Browse Genres")
                    }
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .height(320.dp)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = false
                        ) {
                            itemsIndexed(Constants.MUSIC_GENRES.take(12)) { index, genre ->
                                GenreChip(
                                    genre = genre,
                                    color = genreColors[index % genreColors.size],
                                    onClick = {
                                        navController.navigate(Screen.Search.route)
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // New Releases
                    if (newReleases.isNotEmpty()) {
                        item {
                            SectionHeader(title = "✨ New Releases")
                        }
                        items(newReleases.take(8)) { track ->
                            TrackListItem(
                                track = track,
                                onTrackClick = { playerViewModel.playTrack(it, newReleases) },
                                isPlaying = currentTrack?.id == track.id
                            )
                        }
                    }
                }
            }
        }
    }
}
