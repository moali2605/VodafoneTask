package com.example.city

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.core.R
import com.example.myapplication.domain.model.CityDomainModel

@Composable
fun CitySearchScreen(navController: NavHostController) {
    var city by rememberSaveable { mutableStateOf(emptyList<CityDomainModel>()) }
    var text by rememberSaveable { mutableStateOf("") }
    val viewModel: CityViewModel = hiltViewModel()
    val state by viewModel.stateGetCities.collectAsStateWithLifecycle()

    if (!state.loading) {
        city = state.cityResponse
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.homebg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.padding(top = 60.dp, start = 16.dp, end = 16.dp)) {
            TextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter city name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                itemsIndexed(
                    city
                ) { _, item ->
                    Column(modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable {
                            navController.navigate("homeScreen/$item")
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
