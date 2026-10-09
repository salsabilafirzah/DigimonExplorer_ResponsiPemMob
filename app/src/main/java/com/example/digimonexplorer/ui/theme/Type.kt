package com.example.digimonexplorer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Font bergaya "digital" untuk judul. Untuk memakai font sendiri (mis. Orbitron),
// taruh file .ttf di res/font lalu ganti dengan FontFamily(Font(R.font.nama_file)).
val DigiFont = FontFamily.Monospace

val AppTypography = Typography(
    headlineLarge = TextStyle(fontFamily = DigiFont, fontWeight = FontWeight.Bold, fontSize = 30.sp),
    titleLarge = TextStyle(fontFamily = DigiFont, fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleMedium = TextStyle(fontFamily = DigiFont, fontWeight = FontWeight.Bold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = DigiFont, fontWeight = FontWeight.Normal, fontSize = 12.sp)
)
