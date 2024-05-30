package com.example.myapplicationmasterthesis3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class DaylightMemoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daylight_memory) // Replace with correct Layout

        // Retrieve the light URL from the intent
        val lightUrl1 = intent.getStringExtra("lightUrl1").toString()

        // JSON request body to turn on the light with specific settings
        val requestBody1 = """
              {   
                  "on": true,
                  "sat": 254,
                  "bri": 175,
                  "hue": 47000,
                  "ct": 153
              }
          """.trimIndent()

        // create OkHttp-Client
        val client1 = OkHttpClient()

        // create PUT-Request
        val request1 = Request.Builder()
            .url(lightUrl1)
            .put(requestBody1.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        // send Request
        client1.newCall(request1).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Licht eingeschaltet.")
                } else {
                    println("Fehler beim Einschalten des Lichts: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Fehler beim Senden der Anfrage: ${e.message}")
            }
        })

        // SeekBar for adjusting brightness
        val seekBar: SeekBar = findViewById(R.id.brightnessSeekBar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the brightness of the Philips Hue light
                val scale_start = 96 // set minimum of the brightness scale here
                val scale_end = 254 // set maximum here
                // Calculate scaled brightness
                val brightness_scaled = scale_start + ((progress.toFloat() / 254) * (scale_end - scale_start))
                updateLightBrightness(lightUrl1, brightness_scaled.toInt())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Button for returning to the previous activity
        val backButton: Button = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            // Return to the previous activity along with the light URL
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("lightUrl1",lightUrl1)
            startActivity(intent)
        }
    }

    // Function to update the brightness of the light
    private fun updateLightBrightness(lightUrl: String, brightness: Int) {
        val client = OkHttpClient()

        // JSON request body to update the brightness of the light
        val requestBody = """
            {   
                "on": true,
                "bri": $brightness
            }
        """.trimIndent()

        // Create a PUT request to update the brightness of the light
        val request = Request.Builder()
            .url(lightUrl)
            .put(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        // Send the PUT request asynchronously
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Brightness updated successfully.")
                } else {
                    println("Error updating brightness: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error updating brightness: ${e.message}")
            }
        })
    }

}
