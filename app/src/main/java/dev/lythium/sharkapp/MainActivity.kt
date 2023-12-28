package dev.lythium.sharkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.kittinunf.fuel.Fuel
import dev.lythium.sharkapp.SharkRepository.Content
import dev.lythium.sharkapp.ui.theme.SharkAppTheme
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json.Default.decodeFromString


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharkRepository.loadItems()

        setContent {
            Content()
        }
    }
}

@Serializable
data class SharkItem(
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val upvotes: Int = 0,
    val downvotes: Int = 0,
) {
    @Transient
    var upvotesCount = mutableIntStateOf(upvotes)
    @Transient
    var downvotesCount = mutableIntStateOf(downvotes)
    fun upVote() {
        runBlocking {
            Fuel.post("http://127.0.0.1:8080/feed/$id/upvote").responseString { _, response, _ ->
                when (response.statusCode) {
                    200 -> upvotesCount.intValue++
                    else -> {
                        println(response)
                        println("Error upvoting $id")
                    }
                }
            }
        }
    }

    fun downVote() {
        runBlocking {
            Fuel.post("http://127.0.0.1:8080/feed/$id/downvote").responseString { _, response, _ ->
                when (response.statusCode) {
                    200 -> downvotesCount.intValue++
                    else -> {
                        println(response)
                        println("Error downvoting $id")
                    }
                }
            }
        }
    }
}


object SharkRepository {
    val items: MutableList<SharkItem> = mutableListOf()

    fun loadItems() {
        runBlocking {
                Fuel.get("http://127.0.0.1:8080/feed").responseString { _, response, result ->
                    when (response.statusCode) {
                        200 -> {
                            val (data, _) = result
                            try {
                                val sharkItems: List<SharkItem> = decodeFromString(data!!)
                                items.clear()
                                items.addAll(sharkItems)
                            } catch(e: Exception) {
                                println("Error decoding sharks: $e")
                            }
                        }

                        else -> {
                            println(response)
                            println("Error loading sharks")
                        }
                    }
                }
        }

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
}
