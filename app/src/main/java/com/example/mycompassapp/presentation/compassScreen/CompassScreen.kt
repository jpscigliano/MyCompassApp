package com.example.mycompassapp.presentation.compassScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycompassapp.R

@Composable
fun CompassScreen(
    viewModel: CompassViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()

    CompassScreen(viewState = viewState, onRunButtonClicked = { viewModel.onRunButtonClicked() })

    LaunchedEffect(Unit) {
        viewModel
            .messages
            .collect { message ->
                val text = when (message) {
                    Message.ShowUnableToLoadEvery10thToast -> context.getString(R.string.every_10th_error)
                    Message.ShowUnableToLoadWordCounterToast -> context.getString(R.string.characters_count_error)
                }
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
    }
}

@Composable
fun CompassScreen(
    viewState: CompassViewState, onRunButtonClicked: () -> Unit
) {
    Column {
        RunButton(onRunButtonClicked = onRunButtonClicked, isLoading = viewState.isOccurrenceRequestLoading || viewState.isCharactersRequestLoading)
        Every10thComponent(characters = viewState.characters)
        CharacterCounter(occurrenceCount = viewState.occurrenceCount)
    }
}


@Composable
fun Every10thComponent(characters: List<Char>) {

    Text(
        text = stringResource(id = R.string.every_10th_title),
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.titleLarge,
    )
    HorizontalDivider()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.5f)
    ) {
        itemsIndexed(characters) { index, character ->
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${(index + 1) * 10}th ",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$character",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }

    }
}

@Composable
fun CharacterCounter(occurrenceCount: Map<Char, Int>) {
    Text(
        text = stringResource(id = R.string.characters_count_title),
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.titleLarge,
    )
    HorizontalDivider()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(occurrenceCount.toList()) { (char, count) ->
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$char",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$count",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}


@Composable
fun RunButton(onRunButtonClicked: () -> Unit, isLoading: Boolean) {
    Button(modifier = Modifier
        .height(70.dp)
        .fillMaxWidth()
        .padding(10.dp), onClick = { onRunButtonClicked() }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.run),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleLarge,
            )
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterEnd), color = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CompassScreenPreview() {
    MaterialTheme {
        val viewState = CompassViewState(
            isCharactersRequestLoading = false,
            characters = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'h', 'i', 'g', 'h', 'i', 'g', 'h', 'i', 'g', 'h', 'i', 'g', 'h', 'i', 'g', 'h', 'i'), isOccurrenceRequestLoading = false,
            occurrenceCount = mapOf(
                'a' to 5, 'b' to 2, 'c' to 1, 'd' to 7, 'e' to 3, 'f' to 8, 'g' to 6, 'h' to 4, 'i' to 9, 'j' to 10, 'k' to 11, 'l' to 5, 'm' to 2, 'n' to 1, 'o' to 7, 'p' to 3, 'q' to 8, 'r' to 6, 's' to 4, 't' to 9, 'u' to 10, 'v' to 11, 'w' to 5, 'x' to 2, 'y' to 1, 'z' to 7
            )
        )
        CompassScreen(viewState, {})
    }
}