package com.example.digimonexplorer.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.digimonexplorer.data.model.Digimon
import com.example.digimonexplorer.ui.UiState
import com.example.digimonexplorer.ui.components.ErrorView
import com.example.digimonexplorer.ui.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    digimonId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(digimonId) { viewModel.load(digimonId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Digimon", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (val s = state) {
                is UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView(s.message, onRetry = { viewModel.load(digimonId) })
                is UiState.Success -> DetailContent(s.data)
            }
        }
    }
}

@Composable
private fun DetailContent(d: Digimon) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = d.imageUrl, contentDescription = d.name, modifier = Modifier.size(200.dp))
        Text(d.name, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(onClick = {}, label = { Text(d.level) })
            AssistChip(onClick = {}, label = { Text(d.attribute) })
            AssistChip(onClick = {}, label = { Text(d.type) })
        }

        Spacer(Modifier.height(16.dp))
        InfoCard("Release Date", d.releaseDate)
        InfoCard("Deskripsi", d.description)

        if (d.skills.isNotEmpty()) {
            Text(
                "Skills",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            d.skills.take(5).forEach { InfoCard(it.name, it.description) }
        }
    }
}

@Composable
private fun InfoCard(title: String, content: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(4.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
