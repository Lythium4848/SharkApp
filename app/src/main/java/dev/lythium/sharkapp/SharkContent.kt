package dev.lythium.sharkapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage

@Composable
fun SharkListContent(navController: NavController) {
    Column {
        SharkTitle()
        SharkList()
    }
    ExtendedFAB(onClick = { navController.navigate("newShark") })
}

@Composable
fun SharkTitle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp, 8.dp)
    ) {
        Text(
            text = "Shark List",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

data class SharkItem(
    val name: String,
    val imageUrl: String,
)

@Composable
fun SharkElement(SharkItem: SharkItem, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 1.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = SharkItem.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SharkImage(image = SharkItem.imageUrl)
            }
        }
    }
}

@Composable
fun SharkImage(image: String, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = "Image",
        contentScale = ContentScale.Crop,
        loading = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small),
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 2.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
    )
}

//val SharkImageUrls = listOf(
//    "https://www.wallpaperup.com/uploads/wallpapers/2015/04/23/669391/9e5d0e99436486d99d84c2d2f8cb1c69.jpg",
//    "https://wallup.net/wp-content/uploads/2016/01/201579-animals-fish-sea-shark.jpg",
//    "https://weknowyourdreams.com/images/shark/shark-02.jpg",
//    "https://www.wallpaperama.com/post-images/wallpapers/aquarium/great-white-shark.jpg",
//    "https://wallup.net/wp-content/uploads/2016/12/08/272886-shark-Great_White_Shark-sea.jpg",
//    "https://www.pixelstalk.net/wp-content/uploads/2016/05/HD-Great-White-Shark-Wallpaper.jpg",
//    "https://www.deepblu.com/mag/wp-content/uploads/2020/05/Mako-Shark.jpg",
//)

@Composable
fun SharkList(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        listState.animateScrollToItem(index = 0)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
    ) {
        items(SharkRepository.items.size) { index ->
            SharkElement(SharkItem = SharkRepository.items[index])
        }

    }
}

@Composable
fun ExtendedFAB(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            icon = { Icon(Icons.Filled.Add, stringResource(R.string.add_shark)) },
            text = { Text(text = stringResource(R.string.new_shark)) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}