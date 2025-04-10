package com.test.fetchapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.fetchapp.R
import com.test.fetchapp.data.Item
import com.test.fetchapp.view.ui.theme.FetchAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            FetchAppTheme {
                val viewModel: ItemsListViewModel = viewModel()
                val screenState = viewModel.state.collectAsState()

                listScreen(screenState.value)
                LaunchedEffect(Unit) {
                    viewModel.fetchItems()
                }
            }
        }
    }
}

@Composable
fun listScreen(screenState: ScreenState, modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    val displayCutout = WindowInsets.displayCutout.asPaddingValues()
    val startPadding = displayCutout.calculateStartPadding(layoutDirection)
    val endPadding = displayCutout.calculateEndPadding(layoutDirection)
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = startPadding.coerceAtLeast(0.dp),
                end = endPadding.coerceAtLeast(0.dp)
            )
            .systemBarsPadding(),
        color = MaterialTheme.colorScheme.primary
    ) {
        when (screenState) {
            ScreenState.Loading -> LoadingProgressBar()
            is ScreenState.ContentLoaded -> LazyColumn {

                screenState.items.forEach { grouped ->

                    item {
                        ItemCard(content = { GroupCardContent(
                            stringResource(
                                R.string.list_label,
                                grouped.id
                            )) })
                    }

                    items(items = grouped.subItems) {
                        it.name?.let { name ->
                            ItemCard(
                                content = { SimpleItemCardContent(name) }
                            )
                        }
                    }
                }
            }

            is ScreenState.Error -> ErrorAlertDialog(screenState.error)
        }
    }
}

@Composable
fun GroupCardContent(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            modifier = modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun SimpleItemCardContent(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier = modifier.padding(8.dp), style = MaterialTheme.typography.headlineSmall)
}

@Composable
fun ItemCard(
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        header()
        content()
    }
}

@Composable
fun LoadingProgressBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun ErrorAlertDialog(message: String, modifier: Modifier = Modifier) {
    var openDialog by remember { mutableStateOf(true) }
    if (openDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.error_alert_dialog_title)) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = {
                    openDialog = false
                }) {
                    Text(stringResource(R.string.error_alsert_dialog_confirm_button_text))
                }
            },
            onDismissRequest = {
                openDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    FetchAppTheme {
        LoadingProgressBar()
    }
}