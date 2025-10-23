package com.kevin.rickandmortyapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevin.rickandmortyapp.app.data.repository.NetworkResult
import com.kevin.rickandmortyapp.ui.viewmodel.CharacterViewModel
import com.kevin.rickandmortyapp.app.data.remote.model.Character
import com.kevin.rickandmortyapp.app.data.remote.model.CharacterResponse
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel,
    onCharacterClick: (Int) -> Unit
) {
    val charactersState by viewModel.characters.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rick & Morty Characters") },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(Icons.Default.Refresh, "Actualizar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (charactersState) {
                is NetworkResult.Loading -> LoadingScreen()
                is NetworkResult.Success -> {
                    val characters = (charactersState as NetworkResult.Success).data?.results ?: emptyList()
                    if (characters.isEmpty()) EmptyScreen()
                    else CharacterList(characters = characters, onCharacterClick = onCharacterClick)
                }
                is NetworkResult.Error -> {
                    ErrorScreen(
                        message = (charactersState as NetworkResult.Error).message ?: "Error desconocido",
                        onRetry = { viewModel.refresh() }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character.id) }
            )
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${character.status} - ${character.species}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ubicación: ${character.location.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando personajes...")
        }
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "¡Ups! Algo salió mal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No se encontraron personajes")
    }
}