package com.syedcodes.githubreposearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.syedcodes.githubreposearch.ui.theme.GithubReposAppTheme
import com.syedcodes.githubreposearch.ui.theme.navigation.AppNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubReposAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHostEntry(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavHostEntry(modifier: Modifier = Modifier) {
    AppNavigationGraph()
}