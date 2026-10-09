# Digimon Explorer

Aplikasi Android sederhana untuk menjelajahi data Digimon dari [Digi-API](https://digi-api.com/). Dibuat dengan Kotlin, Jetpack Compose, Material 3, Navigation Compose, dan arsitektur MVVM.

## Screenshot

| Home | Detail | Loading | Error |
|------|--------|---------|-------|
| ![Home](screenshots/home.png) | ![Detail](screenshots/detail.png) | ![Loading](screenshots/loading.png) | ![Error](screenshots/error.png) |

> Ganti file di folder `screenshots/` dengan screenshot aplikasimu sendiri.

## Fitur

- Daftar Digimon dalam `LazyVerticalGrid` (nama, level, attribute, type, gambar)
- Pencarian Digimon berdasarkan nama
- Halaman detail (gambar, chip level/attribute/type, tanggal rilis, deskripsi, skills)
- State UI: Loading, Success (data), Error (dengan tombol coba lagi)
- Custom Theme (light dan dark) dan Custom Typography

## Tech Stack

| Komponen | Library |
|---|---|
| Bahasa | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigasi | Navigation Compose |
| Networking | Retrofit + Gson |
| Gambar | Coil |
| Async | Kotlin Coroutines + StateFlow |

## Arsitektur (MVVM)

```
API (Digi-API)
   -> DigiApiService (Retrofit)
   -> DigimonRepository
   -> ViewModel (StateFlow<UiState>)
   -> Composable Screen (View)
```

## Struktur Folder

```
com.example.digimonexplorer
├── MainActivity.kt              : entry point, memasang theme dan NavGraph
├── data
│   ├── model
│   │   ├── DigimonDto.kt        : data class sesuai JSON API (field nullable)
│   │   └── Digimon.kt           : model untuk UI + mapper (toDigimon)
│   ├── remote
│   │   ├── DigiApiService.kt    : interface endpoint Retrofit
│   │   └── RetrofitInstance.kt  : konfigurasi Retrofit (base URL, converter)
│   └── repository
│       └── DigimonRepository.kt : mengambil list lalu detail secara paralel
└── ui
    ├── UiState.kt               : sealed interface Loading / Success / Error
    ├── components/StateViews.kt : LoadingView dan ErrorView
    ├── home                     : HomeScreen + HomeViewModel
    ├── detail                   : DetailScreen + DetailViewModel
    ├── navigation/NavGraph.kt   : rute home dan detail/{id}
    └── theme                    : Color, Type, Theme
```

## Penjelasan Teknis

**Model.** `DigimonDto.kt` memetakan JSON API ke data class. Field opsional dibuat nullable (`String?`, `List<...>?`) sebagai penerapan null safety. `Digimon.kt` adalah model bersih untuk UI, dibuat lewat mapper `toDigimon()` yang memakai `?.`, `?:`, `firstOrNull`, `orEmpty`, dan `mapNotNull`.

**Networking.** Endpoint list `/digimon` hanya mengembalikan id, nama, dan gambar. Level, attribute, dan type hanya ada di `/digimon/{id}`. Karena itu repository memanggil detail setiap item secara paralel memakai `coroutineScope`, `async`, dan `awaitAll`. Jika satu detail gagal, item itu tetap tampil dengan nilai "Unknown".

**ViewModel dan State.** Setiap ViewModel menyimpan `MutableStateFlow<UiState<T>>` dan hanya mengekspos `StateFlow` read-only. UI mengumpulkannya dengan `collectAsStateWithLifecycle()` lalu menampilkan layar sesuai state memakai `when`.

**Navigation.** `NavHost` memiliki dua rute: `home` dan `detail/{id}` (argumen `Int`). Maksimal dua screen sesuai ketentuan.

**Theme dan Typography.** `Color.kt` berisi palet bertema digital (oranye, biru, cyan, navy). `Theme.kt` mendefinisikan `lightColorScheme` dan `darkColorScheme`. `Type.kt` mendefinisikan `Typography` kustom dengan font monospace untuk judul.

**Fitur Kotlin yang dipakai.** Data class, null safety, lambda (`onClick`, `filter`, `map`), collection (`List`, `filter`, `take`, `mapNotNull`), sealed interface, dan coroutines.

## Cara Menjalankan

1. Clone repository ini.
2. Buka di Android Studio (Ladybug atau lebih baru), tunggu Gradle sync selesai.
3. Pastikan perangkat atau emulator terhubung ke internet.
4. Klik Run.
