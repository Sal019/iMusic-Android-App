package com.salmandev.imusic.ui.screens.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.salmandev.imusic.ui.components.*
import com.salmandev.imusic.viewmodel.LibraryViewModel
import com.salmandev.imusic.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    playerViewModel: PlayerViewModel,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val favoriteTracks by viewModel.favoriteTracks.collectAsState()
    val userPlaylists by viewModel.userPlaylists.collectAsState()
    val downloadedTracks by viewModel.downloadedTracks.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val showDialog by viewModel.showCreatePlaylistDialog.collectAsState()
    val currentTrack by playerViewModel.currentTrack.collectAsState()

    var newPlaylistName by remember { mutableStateOf("") }
    val tabs = listOf("Playlists", "Liked Songs", "Downloads")

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.hideCreatePlaylist() },
            title = { Text("Create Playlist") },
            text = {
                OutlinedTextField(
                    value = newPlaylistName,
                    onValueChange = { newPlaylistName = it },
                    label = { Text("Playlist name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (newPlaylistName.isNotBlank()) {
                        viewModel.createPlaylist(newPlaylistName)
                        newPlaylistName = ""
                    }
                }) { Text("Create") }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.hideCreatePlaylist(); newPlaylistName = "" }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Library", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.showCreatePlaylist() }) {
                        Icon(Icons.Filled.Add, "Create Playlist")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { viewModel.setSelectedTab(index) },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    if (userPlaylists.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.QueueMusic, null, modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Create your first playlist")
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { viewModel.showCreatePlaylist() }) {
                                    Icon(Icons.Filled.Add, null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("New Playlist")
                                }
                            }
                        }
                    } else {
                        LazyColumn {
                            items(userPlaylists) { playlist ->
                                ListItem(
                                    headlineContent = { Text(playlist.name, fontWeight = FontWeight.Medium) },
                                    supportingContent = { Text("${playlist.trackCount} songs") },
                                    leadingContent = {
                                        Icon(Icons.Filled.PlaylistPlay, null, modifier = Modifier.size(44.dp))
                                    },
                                    trailingContent = {
                                        IconButton(onClick = {
                                            viewModel.deletePlaylist(playlist.id.toLongOrNull() ?: 0L)
                                        }) { Icon(Icons.Filled.Delete, "Delete") }
                                    }
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
                1 -> {
                    if (favoriteTracks.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.FavoriteBorder, null, modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Songs you like will appear here")
                            }
                        }
                    } else {
                        LazyColumn {
                            items(favoriteTracks) { track ->
                                TrackListItem(
                                    track = track,
                                    onTrackClick = { playerViewModel.playTrack(it, favoriteTracks) },
                                    isPlaying = currentTrack?.id == track.id
                                )
                            }
                        }
                    }
                }
                2 -> {
                    if (downloadedTracks.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.DownloadDone, null, modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Downloaded songs appear here")
                            }
                        }
                    } else {
                        LazyColumn {
                            items(downloadedTracks) { track ->
                                TrackListItem(
                                    track = track,
                                    onTrackClick = { playerViewModel.playTrack(it, downloadedTracks) },
                                    isPlaying = currentTrack?.id == track.id
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
