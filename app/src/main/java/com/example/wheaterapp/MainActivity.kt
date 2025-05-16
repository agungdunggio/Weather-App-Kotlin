package com.example.weatherapp.ui.view

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.example.weatherapp.ui.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private val apiKey = "YOUR_API_KEY_HERE" // Ganti dengan API key kamu

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityInput = findViewById<EditText>(R.id.etCity)
        val btnFetch = findViewById<Button>(R.id.btnFetch)
        val tvTemp = findViewById<TextView>(R.id.tvTemp)
        val tvDesc = findViewById<TextView>(R.id.tvDesc)
        val imgIcon = findViewById<ImageView>(R.id.imgIcon)

        btnFetch.setOnClickListener {
            val city = cityInput.text.toString()
            if (city.isNotBlank()) {
                viewModel.fetchWeather(city, apiKey)
            }
        }

        viewModel.weatherData.observe(this, Observer { data ->
            tvTemp.text = "Temp: ${data.main.temp}°C"
            tvDesc.text = data.weather.firstOrNull()?.description ?: "No description"
            val iconCode = data.weather.firstOrNull()?.icon ?: "01d"
            imgIcon.load("https://openweathermap.org/img/wn/${iconCode}@2x.png")
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
