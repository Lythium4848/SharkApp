package dev.lythium.sharkapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController

@Composable
fun NewSharkContent(navController: NavController) {
    Dialog(
        onDismissRequest = { navController.navigate("sharkList") },
    ) {
        NewSharkForm(navController = navController)
    }
}

@Composable
fun NewSharkTitle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(24.dp, 16.dp, 16.dp, 2.dp)
    ) {
        Text(
            text = stringResource(R.string.new_shark),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun NewSharkForm(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
    ) {
        var sharkName by remember { mutableStateOf("") }
        var sharkImageUrl by remember { mutableStateOf("") }

        Column {
            NewSharkTitle()
            OutlinedTextField(
                value = sharkName,
                onValueChange = { sharkName = it },
                label = { Text(stringResource(R.string.name)) },
                modifier = Modifier.padding(24.dp, 8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                )
            )
            OutlinedTextField(
                value = sharkImageUrl,
                onValueChange = { sharkImageUrl = it },
                label = { Text(stringResource(R.string.image_url)) },
                modifier = Modifier.padding(24.dp, 8.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrect = true
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(16.dp, 0.dp, 16.dp, 12.dp)
            ) {
                Button(
                    onClick = {
                        val newItem = SharkItem(sharkName, sharkImageUrl)
                        SharkRepository.addItem(newItem)
                        navController.navigate("sharkList")
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.create),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}