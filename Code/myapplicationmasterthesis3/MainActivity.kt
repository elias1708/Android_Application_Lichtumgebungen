package com.example.myapplicationmasterthesis3

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Replace with correct Layout
        setContentView(R.layout.activity_main)

        // Initialize views
        val ipAddressInput: EditText = findViewById(R.id.ipAddressInput)
        val startButton: Button = findViewById(R.id.startButton)

        // Set onClickListener for the start button
        startButton.setOnClickListener {

            // Retrieve the IP address entered by the user
            val ip = ipAddressInput.text.toString()
            val url_start = "http://"
            val url_end = "/api/mDwnfX625QPnjPRHSN0YPFrDdnM1vZs3T0TnDtFW/lights/1/state"
            val lightUrl1 = url_start.plus(ip).plus(url_end)

            // Log the constructed URL
            print(lightUrl1)

            // Define the request body to turn off the light
            val requestBody1 = """
              {   
                  "on": false
              }
          """.trimIndent()

            // create OkHttp-Client
            val client1 = OkHttpClient()

            // Create a PUT request to turn off the light
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

            // Create an intent to start SecondActivity and pass the light URL as extra data
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("lightUrl1",lightUrl1)
            startActivity(intent)

        }
    }
}
