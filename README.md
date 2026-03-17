# 🎵 iMusic — Professional Music Streaming App

<div align="center">

![iMusic Banner](https://img.shields.io/badge/iMusic-Professional%20Music%20App-1DB954?style=for-the-badge&logo=music&logoColor=white)

[![Build Status](https://github.com/salman-dev-app/iMusic/actions/workflows/build-apk.yml/badge.svg)](https://github.com/salman-dev-app/iMusic/actions/workflows/build-apk.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android%207.0%2B-green?style=flat-square&logo=android)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/Music%20API-Jamendo%20%7C%20LrcLib-blue?style=flat-square)](https://developer.jamendo.com/v3.0)

**A fully professional, feature-rich music streaming Android application — a Spotify + Apple Music clone powered by free open-source music APIs. No PC required to build — uses GitHub Actions CI/CD.**

[📥 Download APK](https://github.com/salman-dev-app/iMusic/releases/latest) · [🐛 Report Bug](https://github.com/salman-dev-app/iMusic/issues) · [✨ Request Feature](https://github.com/salman-dev-app/iMusic/issues)

</div>

---

## 👨‍💻 Developer

| | |
|---|---|
| **Name** | Md Salman Biswas |
| **Role** | Senior Software Engineer |
| **GitHub** | [@salman-dev-app](https://github.com/salman-dev-app/salman-dev-app) |
| **Skills** | React, Next.js, Kotlin, TypeScript, Node.js, Laravel, Django, AWS, Docker |

---

## 📱 App Screenshots

> **Dark theme** inspired by Spotify · **Clean navigation** like Apple Music · **100% Open Source**

| Home | Search | Player | Library |
|------|--------|--------|---------|
| 🏠 Trending, New Releases | 🔍 Songs/Artists/Albums | 🎵 Full-screen Player | 📚 Playlists/Favorites |

---

## ✨ Features

### 🎵 Music Playback
- ✅ Online streaming (600K+ free Creative Commons songs via Jamendo)
- ✅ Offline playback (downloaded tracks work without internet)
- ✅ Background playback (music continues when app is minimized)
- ✅ Lock screen & notification media controls
- ✅ Bluetooth headphone controls
- ✅ Audio focus management (pauses when call comes in)
- ✅ Gapless playback
- ✅ Crossfade between tracks

### 🎛️ Player Controls
- ✅ Play / Pause / Skip Next / Skip Previous
- ✅ Shuffle mode
- ✅ Repeat (Off / All / One)
- ✅ Seek bar with time display
- ✅ Volume control
- ✅ Sleep timer (5, 10, 15, 20, 30, 45, 60, 90 min)
- ✅ Queue management

### 🔍 Discovery
- ✅ Home screen with trending, new releases, featured artists
- ✅ Genre browsing (20 genres: Pop, Rock, Electronic, Jazz, etc.)
- ✅ Real-time search (songs, artists, albums)
- ✅ Recently played history
- ✅ Artist profiles with top tracks and albums
- ✅ Album detail pages

### 📚 Library
- ✅ Liked / Favorite songs
- ✅ User-created playlists (create, rename, delete)
- ✅ Playlist management (add/remove tracks)
- ✅ Downloaded tracks (offline)

### 🎤 Lyrics
- ✅ Synchronized lyrics (timed, line-by-line) via LrcLib API
- ✅ Plain text lyrics fallback

### ⚙️ Settings
- ✅ Dark / Light theme
- ✅ Audio quality selection
- ✅ Equalizer with presets
- ✅ Normalize volume
- ✅ Download over Wi-Fi only
- ✅ Storage management
- ✅ Sleep timer
- ✅ Notification controls

---

## 🏗️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin 1.9.24 |
| **UI Framework** | Jetpack Compose + Material Design 3 |
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt (Dagger) |
| **Media Playback** | Media3 / ExoPlayer + MediaSession |
| **Database** | Room (SQLite) |
| **Network** | Retrofit + OkHttp + Gson |
| **Image Loading** | Coil |
| **Async** | Coroutines + Flow + StateFlow |
| **Navigation** | Navigation Compose |
| **Background Tasks** | WorkManager |
| **Storage** | DataStore Preferences |
| **CI/CD** | GitHub Actions |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 34 (Android 14) |

---

## 🎵 Free Music APIs Used

### 1. 🎸 Jamendo API (Primary)
- **URL:** https://developer.jamendo.com/v3.0
- **License:** Free, no signup required for basic use
- **Library:** 600,000+ Creative Commons tracks
- **Demo Key:** `b6747d04` (included in app)
- **Limit:** 5,000 requests/day
- **Features:** Trending, search, genres, artists, albums

### 2. 🎤 LrcLib (Lyrics)
- **URL:** https://lrclib.net
- **License:** Completely free, no API key needed
- **Features:** Synchronized & plain text lyrics
- **Millions of songs** supported

### 3. 🎼 MusicBrainz (Metadata)
- **URL:** https://musicbrainz.org/ws/2
- **License:** Open data, free
- **Features:** Artist/album metadata

---

## 🚀 Build the APK — No PC Required!

### Method 1: GitHub Actions (Recommended — Zero Setup!)

1. **Fork this repository** on GitHub
2. Go to **Actions** tab in your forked repo
3. Click **"iMusic - Build Android APK"** workflow
4. Click **"Run workflow"** → **"Run workflow"** button
5. Wait ~5-10 minutes for the build
6. Go to **Releases** tab → download `iMusic-Debug-APK.apk`

> 💡 The workflow automatically runs on every push to `main` branch AND creates a GitHub Release with the APK!

### Method 2: Android Studio (If you have a PC)

```bash
# 1. Clone the repository
git clone https://github.com/salman-dev-app/iMusic.git
cd iMusic

# 2. Open in Android Studio

# 3. Build APK
./gradlew assembleDebug

# 4. APK location:
# app/build/outputs/apk/debug/app-debug.apk
```

### Method 3: GitHub Codespaces (Browser-based!)

1. Open repo on GitHub
2. Click **Code** → **Codespaces** → **Create codespace**
3. In the terminal: `./gradlew assembleDebug`
4. Download the APK from `app/build/outputs/apk/debug/`

---

## 📂 Project Structure

```
iMusic/
├── .github/
│   └── workflows/
│       └── build-apk.yml          # 🤖 GitHub Actions CI/CD
├── app/
│   ├── build.gradle.kts           # App dependencies & config
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/salmandev/imusic/
│       │   ├── IMusicApp.kt       # Hilt Application class
│       │   ├── MainActivity.kt    # Single activity
│       │   ├── core/
│       │   │   ├── Constants.kt   # API keys, config
│       │   │   └── Utils.kt       # Helper functions
│       │   ├── data/
│       │   │   ├── api/           # Retrofit API services
│       │   │   │   ├── JamendoApiService.kt
│       │   │   │   └── LrcLibApiService.kt
│       │   │   ├── local/         # Room database
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── dao/       # Data Access Objects
│       │   │   │   └── entity/    # Database entities
│       │   │   ├── model/         # Domain models
│       │   │   └── repository/    # Data layer
│       │   ├── di/
│       │   │   └── AppModule.kt   # Hilt DI module
│       │   ├── service/
│       │   │   └── MusicPlaybackService.kt  # Media3 service
│       │   ├── ui/
│       │   │   ├── navigation/    # NavGraph
│       │   │   ├── components/    # Reusable composables
│       │   │   ├── screens/       # App screens
│       │   │   │   ├── home/      # Home screen
│       │   │   │   ├── search/    # Search screen
│       │   │   │   ├── library/   # Library screen
│       │   │   │   ├── player/    # Full player screen
│       │   │   │   └── settings/  # Settings screen
│       │   │   └── theme/         # Colors, Typography, Theme
│       │   └── viewmodel/         # ViewModels (MVVM)
│       └── res/                   # Resources, icons
├── gradle/
│   ├── libs.versions.toml         # Version catalog
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🔧 GitHub Actions Workflow Details

The `.github/workflows/build-apk.yml` workflow:

```
Trigger: Push to main → Pull Request → Manual trigger
Steps:
  1. ✅ Checkout code
  2. ✅ Setup JDK 17 (Temurin)
  3. ✅ Setup Android SDK
  4. ✅ Setup Gradle (auto-downloads wrapper)
  5. ✅ Cache Gradle dependencies (faster builds)
  6. ✅ Build Debug APK (./gradlew assembleDebug)
  7. ✅ Upload APK as artifact (90-day retention)
  8. ✅ Create GitHub Release with APK (on main branch)
```

**Build time:** ~5-8 minutes on GitHub's free tier
**Cost:** 💚 Completely FREE (GitHub Free includes 2,000 minutes/month)

---

## 📥 Install APK on Android

1. Download `iMusic-Debug-APK.apk` from [Releases](https://github.com/salman-dev-app/iMusic/releases)
2. On your Android phone: **Settings → Security → Install Unknown Apps** → Enable for your browser
3. Open the downloaded APK file
4. Tap **Install**
5. Open **iMusic** and enjoy! 🎵

---

## 📜 License

```
MIT License

Copyright (c) 2024 Md Salman Biswas

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 🎵 Music Licensing

All music is sourced from **Jamendo** under Creative Commons licenses:
- **CC BY** — Attribution required
- **CC BY-SA** — Attribution + ShareAlike
- **CC BY-NC** — Non-commercial use
- **CC BY-ND** — No derivatives

---

## ⭐ Star This Project

If you found this helpful, please give it a ⭐ star on GitHub!

[![GitHub Stars](https://img.shields.io/github/stars/salman-dev-app/iMusic?style=social)](https://github.com/salman-dev-app/iMusic/stargazers)

---

<div align="center">
  Made with ❤️ by <a href="https://github.com/salman-dev-app/salman-dev-app">Md Salman Biswas</a>
  <br/>
  <sub>🎵 Free music powered by Jamendo API | Open Source MIT</sub>
</div>
