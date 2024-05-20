package com.example.city

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.core.R
import com.example.core.util.Constants.HOME_SCREEN
import com.example.core.util.Painters
import com.example.domain.model.CityResponseDomainModel
import com.example.myapplication.domain.model.CityDomainModel

@Composable
fun CitySearchScreen(navController: NavHostController) {

    val viewModel: CityViewModel = hiltViewModel()
    viewModel.getCities()

    val state by viewModel.stateGetCities.collectAsStateWithLifecycle()
    val cityInfoState by viewModel.stateGetCityInfo.collectAsStateWithLifecycle()

    var city by rememberSaveable { mutableStateOf(emptyList<CityDomainModel>()) }
    var cityInfo by rememberSaveable { mutableStateOf(emptyList<CityResponseDomainModel>()) }
    var text by rememberSaveable { mutableStateOf("") }

    if (!cityInfoState.loading) {
        cityInfo = cityInfoState.cityInfoResponse
    }

    if (!state.loading) {
        city = state.cityResponse
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = Painters.homeBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.padding(top = 60.dp, start = 16.dp, end = 16.dp)) {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                    if (text.isNotEmpty()) {
                        viewModel.getCityInfo(text)
                    }
                },
                label = { Text(stringResource(com.example.city.R.string.enter_city_name)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (text.isNotEmpty()) {
                            viewModel.getCityInfo(text)
                        }
                    }
                )

            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            if (text.isNotEmpty()) {
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    itemsIndexed(
                        cityInfo
                    ) { _, item ->
                        Column(modifier = Modifier
                            .padding(top = 16.dp)
                            .clickable {
                                viewModel.insertCity(
                                    CityDomainModel(
                                        cityName = item.name,
                                        cityLat = item.latitude,
                                        cityLong = item.longitude
                                    )
                                )
                                navController.navigate("$HOME_SCREEN/${item.name}/${item.latitude}/${item.longitude}")
                            }) {
                            Text(text = "${item.name}, ${item.state}, ${item.country}")
                            Spacer(modifier = Modifier.height(16.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                            )
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    itemsIndexed(
                        city
                    ) { _, item ->
                        Column(modifier = Modifier
                            .padding(top = 16.dp)
                            .clickable {
                                navController.navigate("$HOME_SCREEN/${item.cityName}/${item.cityLat}/${item.cityLong}")
                            }) {
                            Text(text = item.cityName)
                            Spacer(modifier = Modifier.height(16.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                            )
                        }
                    }
                }
            }
        }
    }
}
