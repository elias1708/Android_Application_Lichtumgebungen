package com.example.myapplicationmasterthesis3

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.ArrayAdapter
import android.content.Intent
import android.widget.Spinner
import android.widget.Button
import android.util.Log


class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Replace with correct Layout
        setContentView(R.layout.activity_second)

        // Initialize views
        val startButton: Button = findViewById(R.id.startButton)
        val backButton: Button = findViewById(R.id.backButton)
        val lightSpinner: Spinner = findViewById(R.id.lightSpinner)
        val activitySpinner: Spinner = findViewById(R.id.activitySpinner)

        // Set onClickListener for the back button to return to MainActivity
        backButton.setOnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        }

        // Set up adapters for the spinners
        val lightOptions = resources.getStringArray(R.array.light_options)
        val lightAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lightOptions)
        lightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lightSpinner.adapter = lightAdapter

        val activityOptions = resources.getStringArray(R.array.activity_options)
        val activityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, activityOptions)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activitySpinner.adapter = activityAdapter

        // Retrieve the light URL from the intent
        val lightUrl1 = intent.getStringExtra("lightUrl1").toString()

        // Log the received light URL
        Log.d("receiver","Got message: " + lightUrl1);

        // Set onClickListener for the start button
        startButton.setOnClickListener {
            // Get the selected items from the spinners
            val selectedLight = lightSpinner.selectedItem.toString()
            val selectedActivity = activitySpinner.selectedItem.toString()

            // Log the selected light and activity
            Log.d("selectedLight","Got message: " + selectedLight);
            Log.d("selectedActivity","Got message: " + selectedActivity);

            // Determine which activity to start based on the selected light and activity
            val intent = if (selectedLight == "Daylight" && selectedActivity == "Memory") {
                Intent(this, DaylightMemoryActivity::class.java)}
            else if (selectedLight == "Neutralwhite" && selectedActivity == "Memory") {
                Intent(this, NeutralwhitelightMemoryActivity::class.java)}
            else if (selectedLight == "Daylight" && selectedActivity == "Reading Comprehension") {
                Intent(this, DaylightReadingActivity::class.java)}
            else{
                Intent(this, NeutralwhitelightReadingActivity::class.java)}

            // Pass the light URL to the next activity
            intent.putExtra("lightUrl1",lightUrl1)
            startActivity(intent)

        }

    }

}





