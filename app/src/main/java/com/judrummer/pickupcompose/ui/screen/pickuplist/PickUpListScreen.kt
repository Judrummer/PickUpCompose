package com.judrummer.pickupcompose.ui.screen.pickuplist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.judrummer.pickupcompose.ui.theme.PickUpComposeTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun PickUpListScreen() {
    val viewModel: PickUpListViewModel = getViewModel()
    val state = viewModel.state.observeAsState().value ?: PickUpListViewState()
    val swipeRefreshState = rememberSwipeRefreshState(state.refreshing)

    DisposableEffect(Unit) {
        viewModel.initialize()
        onDispose {
            //TOOD: clean , dispose
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "PICK UP")
                },
                navigationIcon = null,
                elevation = 12.dp
            )
        }, content = {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onRefresh()
                },
            ) {
                BoxWithConstraints(contentAlignment = Alignment.Center) {
                    when {
                        state.loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                        state.error != null -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text("Fetch Error.\nPull to Refresh Again.")
                            }
                        }
                        else -> {
                            LazyColumn {
                                itemsIndexed(state.items) { index, item ->
                                    Column(
                                        modifier = Modifier.clickable {
                                            //TODO: click to navigate?
                                        },
                                    ) {
                                        Text(text = item.id.toString())
                                        Text(text = item.id.toString())
                                        Text(text = item.id.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PickUpComposeTheme {
        PickUpListScreen()
    }
}