
package com.example.knowyourcountry

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder

@Composable
fun mainScreen(modifier: Modifier, vM: mainViewModel) {

    val obj by vM.dbObj
    var search by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFE0F7FA), Color(0xFFFFFFFF))
                )
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it; isTyping = true },

                label = { Text("Search any country") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )
            IconButton(
                onClick = {isTyping=false; vM.fetchData(search) },
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        }

        // Result
        if(isTyping==false) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when {
                    obj.loading -> {

                        CircularProgressIndicator()


                    }

                    obj.error != null -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Error Icon",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(bottom = 16.dp)
                            )

                            Text(
                                text = "Oops! Something went wrong.",
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = obj.error ?: "Unknown error",
                                color = Color.DarkGray,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }


                    else -> {
                        contryScreen(obj.responseList)
                    }
                }
            }
        }
    }
}

@Composable
fun contryScreen(list: List<keyValue>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list) { country ->
            CountryItem(country)
        }
    }
}

@Composable
fun CountryItem(item: keyValue) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components { add(SvgDecoder.Factory()) }
                .build()

            Image(
                painter = rememberAsyncImagePainter(
                    model = item.flag,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(2f)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            InfoText(label = "Name", value = item.name)
            InfoText(label = "Capital", value = item.capital)
            InfoText(label = "Region", value = item.region)
            InfoText(label = "Population", value = item.population.toString())
            InfoText(label = "Area", value = item.area.toString())
            InfoText(label = "Currency", value = item.currency)
            InfoText(
                label = "Coordinates",
                value = "Lat: ${item.coordinates.latitude}, Lon: ${item.coordinates.longitude}"
            )
            InfoText(
                label = "Coordinates",
                value = "Lat: ${item.coordinates.latitude}, Lon: ${item.coordinates.longitude}"
            )
            InfoText(
                label = "Borders",
                value = item.borders.joinToString(", "))

            InfoText(
                label = "Languages",
                value = item.languages.joinToString(", ")
            )

            InfoText(
                label = "Timezones",
                value = item.timezones.joinToString(", ")
            )
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF37474F)
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

