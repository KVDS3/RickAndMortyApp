package com.kevin.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevin.rickandmortyapp.ui.screens.CharacterDetailScreen
import com.kevin.rickandmortyapp.ui.screens.CharacterListScreen
import com.kevin.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.kevin.rickandmortyapp.ui.viewmodel.CharacterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RickAndMortyApp()
                }
            }
        }
    }
}

@Composable
fun RickAndMortyApp() {
    val viewModel: CharacterViewModel = viewModel()
    var selectedCharacterId by remember { mutableStateOf<Int?>(null) }

    if (selectedCharacterId == null) {
        CharacterListScreen(
            viewModel = viewModel,
            onCharacterClick = { characterId ->
                selectedCharacterId = characterId
            }
        )
    } else {
        CharacterDetailScreen(
            characterId = selectedCharacterId!!,
            viewModel = viewModel,
            onBackClick = { selectedCharacterId = null }
        )
    }
}