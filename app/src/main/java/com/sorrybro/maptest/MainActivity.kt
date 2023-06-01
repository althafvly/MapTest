package com.sorrybro.maptest

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sorrybro.maptest.ui.theme.MapTestTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var location by remember { mutableStateOf("New York City") }

                            TextField(
                                value = location,
                                onValueChange = { location = it },
                                label = { Text("Enter location") }
                            )

                            Text(
                                text = "Current location: $location",
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center
                            )

                            Button(
                                onClick = { openMagicEarthWithDestination(location) },
                                modifier = Modifier.padding(4.dp)

                            ) {
                                Text(text = "Magic Earth")
                            }

                            Button(
                                onClick = { openOpenStreetMapWithDestination(location) },
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(text = "Open Street Map")
                            }

                            Button(
                                onClick = { openMapWithDestination(location) },
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(text = "Map")
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openMagicEarthWithDestination(destination: String) {
        val encodedDestination = destination.replace(" ", "+")
        val geoUri = Uri.parse("magicearth://map?q=$encodedDestination")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        mapIntent.setPackage("com.generalmagic.magicearth")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    private fun openOpenStreetMapWithDestination(destination: String) {
        val encodedDestination = Uri.encode(destination)
        val openStreetMapUrl =
            "https://www.openstreetmap.org/search?query=$encodedDestination"
        val uri = Uri.parse(openStreetMapUrl)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun openMapWithDestination(destination: String) {
        val encodedDestination = destination.replace(" ", "+")
        val uri = Uri.parse("geo:0,0?q=$encodedDestination")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(mapIntent)
    }
}
