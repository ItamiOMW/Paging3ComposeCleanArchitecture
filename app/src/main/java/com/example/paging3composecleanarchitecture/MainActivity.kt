package com.example.paging3composecleanarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.paging3composecleanarchitecture.presentation.BeerViewModel
import com.example.paging3composecleanarchitecture.presentation.BeersScreen
import com.example.paging3composecleanarchitecture.ui.theme.Paging3ComposeCleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Paging3ComposeCleanArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: BeerViewModel = hiltViewModel()
                    val beers = viewModel.beerPagingFlow.collectAsLazyPagingItems()
                    BeersScreen(beers = beers)
                }

            }
        }
    }
}
