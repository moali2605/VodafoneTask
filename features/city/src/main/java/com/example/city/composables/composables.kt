package com.example.city.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.city.view_model.CityViewModel
import com.example.core.util.Constants
import com.example.domain.model.CityDomainModel

@Composable
fun CityList(viewModel: CityViewModel, navController: NavHostController, city: List<CityDomainModel>) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
        itemsIndexed(
            city
        ) { _, item ->
            Column(modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    viewModel.insertCity(
                        CityDomainModel(
                            cityName = item.cityName,
                            cityLat = item.cityLat,
                            cityLong = item.cityLong
                        )
                    )
                    navController.navigate("${Constants.HOME_SCREEN}/${item.cityName}/${item.cityLat}/${item.cityLong}")
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