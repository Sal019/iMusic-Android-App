package com.salmandev.imusic.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.salmandev.imusic.ui.theme.SpotifyGreen

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String = "",
    val hasSwitch: Boolean = false,
    val switchState: Boolean = false,
    val onClick: () -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var darkMode by remember { mutableStateOf(true) }
    var downloadOnWifi by remember { mutableStateOf(true) }
    var crossfade by remember { mutableStateOf(false) }
    var showLyrics by remember { mutableStateOf(true) }
    var normalizeVolume by remember { mutableStateOf(false) }
    var showNotification by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Profile Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = SpotifyGreen,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "MS",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                "Md Salman Biswas",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Senior Software Engineer",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // AUDIO SETTINGS
            item { SettingsSectionHeader("Audio") }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.Equalizer,
                    title = "Equalizer",
                    subtitle = "Customize sound profile",
                    checked = false,
                    onToggle = {}
                )
            }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.VolumeUp,
                    title = "Normalize Volume",
                    subtitle = "Same volume across all songs",
                    checked = normalizeVolume,
                    onToggle = { normalizeVolume = it }
                )
            }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.Shuffle,
                    title = "Crossfade",
                    subtitle = "Smooth transition between songs (3s)",
                    checked = crossfade,
                    onToggle = { crossfade = it }
                )
            }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.HighQuality,
                    title = "Audio Quality",
                    subtitle = "High (256kbps)",
                    onClick = {}
                )
            }

            // PLAYBACK SETTINGS
            item { SettingsSectionHeader("Playback") }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.Lyrics,
                    title = "Show Lyrics",
                    subtitle = "Display lyrics while playing",
                    checked = showLyrics,
                    onToggle = { showLyrics = it }
                )
            }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.Alarm,
                    title = "Sleep Timer",
                    subtitle = "Off",
                    onClick = {}
                )
            }

            // DOWNLOADS
            item { SettingsSectionHeader("Downloads") }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.Wifi,
                    title = "Download over Wi-Fi only",
                    subtitle = "Avoid using mobile data for downloads",
                    checked = downloadOnWifi,
                    onToggle = { downloadOnWifi = it }
                )
            }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.Storage,
                    title = "Storage",
                    subtitle = "Manage downloaded content",
                    onClick = {}
                )
            }

            // APPEARANCE
            item { SettingsSectionHeader("Appearance") }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.DarkMode,
                    title = "Dark Mode",
                    subtitle = "Use dark theme",
                    checked = darkMode,
                    onToggle = { darkMode = it }
                )
            }

            // NOTIFICATIONS
            item { SettingsSectionHeader("Notifications") }

            item {
                SettingsToggleItem(
                    icon = Icons.Filled.Notifications,
                    title = "Show Notifications",
                    subtitle = "Media controls in notification bar",
                    checked = showNotification,
                    onToggle = { showNotification = it }
                )
            }

            // ABOUT
            item { SettingsSectionHeader("About") }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.Info,
                    title = "App Version",
                    subtitle = "iMusic v1.0.0",
                    onClick = {}
                )
            }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.Code,
                    title = "Open Source",
                    subtitle = "GitHub: salman-dev-app",
                    onClick = {}
                )
            }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.MusicNote,
                    title = "Music Source",
                    subtitle = "Jamendo API - 600K+ free songs",
                    onClick = {}
                )
            }

            item {
                SettingsNavItem(
                    icon = Icons.Filled.Person,
                    title = "Developer",
                    subtitle = "Md Salman Biswas",
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        color = SpotifyGreen,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingContent = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = onToggle)
        }
    )
    HorizontalDivider()
}

@Composable
fun SettingsNavItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingContent = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
        trailingContent = { Icon(Icons.Filled.ChevronRight, null) },
        modifier = Modifier.clickable { onClick() }
    )
    HorizontalDivider()
}
