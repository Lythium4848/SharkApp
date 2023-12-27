package dev.lythium.sharkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.lythium.sharkapp.ui.theme.SharkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }
}

object SharkRepository {
    val items: MutableList<SharkItem> = mutableListOf()

    fun addItem(item: SharkItem) {
        items.add(item)
    }
}

@Composable
fun Content() {
    val navController = rememberNavController()

    SharkAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(navController = navController, startDestination = "sharkList") {
                composable("sharkList") {
                    SharkListContent(navController = navController)
                }
                composable("newShark") {
                    NewSharkContent(navController = navController)
                }
            }
        }
    }
}
