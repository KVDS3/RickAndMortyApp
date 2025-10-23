package com.kevin.rickandmortyapp.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevin.rickandmortyapp.app.data.repository.NetworkResult
import com.kevin.rickandmortyapp.ui.viewmodel.CharacterViewModel
import com.kevin.rickandmortyapp.app.data.remote.model.Character
import com.kevin.rickandmortyapp.app.data.remote.model.CharacterResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CharacterDetailScreen(
    characterId: Int,
    viewModel: CharacterViewModel,
    onBackClick: () -> Unit
) {
    val characterState by viewModel.selectedCharacter.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.loadCharacterById(characterId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Personaje") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (characterState) {
            is NetworkResult.Loading -> {
                LoadingScreen()
            }
            is NetworkResult.Success -> {
                val character = (characterState as NetworkResult.Success).data
                if (character != null) {
                    CharacterDetailContent(
                        character = character,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
            is NetworkResult.Error -> {
                ErrorScreen(
                    message = (characterState as NetworkResult.Error).message ?: "Error",
                    onRetry = { viewModel.loadCharacterById(characterId) }
                )
            }
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Estado: ${character.status}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Especie: ${character.species}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Género: ${character.gender}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ubicación: ${character.location.name}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Origen: ${character.origin.name}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}