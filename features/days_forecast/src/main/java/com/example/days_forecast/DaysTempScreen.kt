package com.example.days_forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.R
import com.example.core.util.setIcon
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DaysTempScreen(
    cityLat: String,
    cityLon: String
) {

    val viewModel: DaysForecastViewModel = hiltViewModel()

    LaunchedEffect(viewModel) {
        viewModel.processIntent(DaysForecastIntent.Load, lat = cityLat.toDouble(), lon = cityLon.toDouble())
    }

    val state = viewModel.stateGetWeather.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.days_temp_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.padding(top = 48.dp, start = 16.dp, end = 16.dp)) {
            Text(
                text = "8 days forecast",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                itemsIndexed(
                    state.value.weatherResponse.daily
                ) { index, item ->
                    Column(modifier = Modifier.padding(top = 16.dp)) {
                        WeatherListItems(
                            date = formatDayOfWeek(item.dateTime),
                            icon = setIcon(id = item.iconSet), // Adjust icon based on item
                            temp = "${item.temp.toInt()}°C",
                            description = item.description
                        )
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .background(Color.Gray)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherIcon(icon: Painter) {
    Box(
        modifier = Modifier
            .size(50.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun WeatherListItems(date: String, icon: Painter, temp: String, description: String) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.width(120.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        WeatherIcon(icon = icon)
        Text(
            text = temp,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.width(48.dp))
        Text(
            text = description,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

private fun formatDayOfWeek(timestamp: Double): String {
    val sdf = SimpleDateFormat("EEE,d MMM", Locale("en"))
    val calendar: Calendar = Calendar.getInstance()
    val currentDay = calendar.get(Calendar.DAY_OF_YEAR)
    calendar.timeInMillis = timestamp.toLong() * 1000
    return when (calendar.get(Calendar.DAY_OF_YEAR)) {
        currentDay -> "Today"
        currentDay + 1 -> "Tomorrow"
        else -> sdf.format(calendar.time).uppercase(Locale.ROOT)
    }
}