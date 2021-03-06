package com.judrummer.pickupcompose.ui.screen.pickuplist

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.judrummer.pickupcompose.location.PickUpLatLng
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PickUpListScreen() {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val viewModel: PickUpListViewModel = getViewModel()
    val state = viewModel.state.observeAsState().value ?: PickUpListViewState()
    val swipeRefreshState = rememberSwipeRefreshState(state.refreshing)

    DisposableEffect(Unit) {
        viewModel.initialize()
        onDispose {
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "PICK UP")
                },
                actions = {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(48.dp)
                            .clickable {
                                if (state.loadingLocation) return@clickable

                                Dexter
                                    .withContext(context)
                                    .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                                    .withListener(object : MultiplePermissionsListener {
                                        @SuppressLint("MissingPermission")
                                        override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                                            if (p0?.areAllPermissionsGranted() == true) {
                                                viewModel.requestLocation()
                                            }
                                        }

                                        override fun onPermissionRationaleShouldBeShown(p0: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                                        }
                                    })
                                    .check()
                            },
                    ) {
                        val modifier = Modifier
                            .align(Alignment.Center)
                            .size(24.dp)

                        if (state.loadingLocation) CircularProgressIndicator(
                            modifier = modifier,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                        else Icon(
                            Icons.Rounded.Search,
                            modifier = modifier,
                            contentDescription = "Search Location",
                        )
                    }
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
                                    .padding(16.dp),
                                horizontalAlignment = CenterHorizontally,
                            ) {
                                Text("Fetch Error.\nPull to Refresh Again.")
                            }
                        }
                        else -> {
                            LazyColumn {
                                itemsIndexed(state.calculatedItems) { index, item ->
                                    Card(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp), elevation = 8.dp) {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                        ) {
                                            if (item.city.isNotBlank()) Text(text = item.city)
                                            Text(text = item.name.uppercase(), style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                                            if (item.address.isNotBlank()) Text(text = item.address)
                                        }
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
