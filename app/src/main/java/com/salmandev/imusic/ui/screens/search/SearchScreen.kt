package com.salmandev.imusic.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salmandev.imusic.core.Constants
import com.salmandev.imusic.ui.components.*
import com.salmandev.imusic.ui.screens.home.genreColors
import com.salmandev.imusic.viewmodel.PlayerViewModel
import com.salmandev.imusic.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    playerViewModel: PlayerViewModel,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val tracks by viewModel.tracks.collectAsState()
    val artists by viewModel.artists.collectAsState()
    val albums by viewModel.albums.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentTrack by playerViewModel.currentTrack.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Songs", "Artists", "Albums")

    Column(modifier = Modifier.fillMaxSize()) {

        // Search Bar
        SearchBar(
            query = query,
            onQueryChange = { viewModel.updateQuery(it) },
            onSearch = {},
            active = false,
            onActiveChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("What do you want to listen to?") },
            leadingIcon = { Icon(Icons.Filled.Search, "Search") },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.updateQuery("") }) {
                        Icon(Icons.Filled.Close, "Clear")
                    }
                }
            }
        ) {}

        if (query.isEmpty()) {
            // Browse Genres
            Text(
                text = "Browse all",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(Constants.MUSIC_GENRES) { index, genre ->
                    GenreChip(
                        genre = genre,
                        color = genreColors[index % genreColors.size],
                        onClick = {
                            viewModel.updateQuery(genre)
                            viewModel.searchByGenre(genre)
                        }
                    )
                }
            }
        } else {
            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            if (isLoading) {
                LoadingIndicator()
            } else {
                when (selectedTab) {
                    0 -> {
                        // Songs
                        if (tracks.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No songs found for \"$query\"")
                            }
                        } else {
                            LazyColumn {
                                items(tracks) { track ->
                                    TrackListItem(
                                        track = track,
                                        onTrackClick = { playerViewModel.playTrack(it, tracks) },
                                        isPlaying = currentTrack?.id == track.id,
                                        onMoreClick = {}
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        // Artists
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(artists) { artist ->
                                ArtistCard(
                                    imageUrl = artist.imageUrl,
                                    name = artist.name,
                                    onClick = {}
                                )
                            }
                        }
                    }
                    2 -> {
                        // Albums
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(albums) { album ->
                                MusicCard(
                                    imageUrl = album.coverUrl,
                                    title = album.name,
                                    subtitle = album.artistName,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
