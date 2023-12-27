package dev.lythium.sharkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.lythium.sharkapp.ui.theme.SharkAppTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Content()
        }
    }
}

data class SharkItem(
    val name: String,
    val imageUrl: String,
    val initialVotes: SharkVotes = SharkVotes(Random.nextInt(0, 100), Random.nextInt(0, 100)),
) {

    var votes = mutableStateOf(initialVotes)
    fun upVote() {
        votes.value = votes.value.copy(upVotes = votes.value.upVotes + 1)
    }

    fun downVote() {
        votes.value = votes.value.copy(downVotes = votes.value.downVotes + 1)
    }
}

data class SharkVotes(
    val upVotes: Int,
    val downVotes: Int,
)


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
