package kg.tutorial.weathermvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import kg.tutorial.weathermvvm.R
import kg.tutorial.weathermvvm.extensions.format
import kg.tutorial.weathermvvm.models.Constants
import kg.tutorial.weathermvvm.models.ForeCast
import kg.tutorial.weathermvvm.ui.rv.DailyForeCastAdapter
import kg.tutorial.weathermvvm.ui.rv.HourlyForeСastAdapter
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    private lateinit var dailyForeCastAdapter: DailyForeCastAdapter
    private lateinit var hourlyForecastAdapter: HourlyForeСastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = getViewModel(MainViewModel::class)
        setupViews()
        setupRecyclerViews()
        subscribeToLiveData()
        obtainFirebaseToken()
        parseDataFromIntent()
    }

    private fun obtainFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.i("TOKEN", it)
        }
    }

    private fun parseDataFromIntent() {
        intent.getStringExtra("EXTRA")?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        }
    }

    private fun setupViews() {
        val tv_refresh: TextView = findViewById(R.id.tv_refresh)
        tv_refresh.setOnClickListener {
            vm.showLoading()
            vm.getWeatherFromApi()
        }
    }

    private fun setupRecyclerViews() {
        val rv_daily_forecast: RecyclerView = findViewById(R.id.rv_daily_forecast)
        val rv_hourly_forecast: RecyclerView = findViewById(R.id.rv_hourly_forecast)

        dailyForeCastAdapter = DailyForeCastAdapter()
        rv_daily_forecast.adapter = dailyForeCastAdapter

        hourlyForecastAdapter = HourlyForeСastAdapter()
        rv_hourly_forecast.adapter = hourlyForecastAdapter
    }


    private fun subscribeToLiveData() {
        vm.getForeCastAsLive().observe(this, Observer {
            it?.let {
                setValuesToViews(it)
                loadWeatherIcon(it)
                setDataToRecyclerViews(it)
            }
        })

        vm._isLoading.observe(this, Observer {
            when (it) {
                true -> showLoading()
                false -> hideLoading()
            }
        })
    }

    private fun setDataToRecyclerViews(it: ForeCast) {
        it.daily?.let { dailyList ->
            dailyForeCastAdapter.setItems(dailyList)
        }

        it.hourly?.let { hourlyList ->
            hourlyForecastAdapter.setItems(hourlyList)
        }
    }

    private fun showLoading() {
        val progress: ProgressBar = findViewById(R.id.progress)
        progress.post {
            progress.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        val progress: ProgressBar = findViewById(R.id.progress)
        progress.postDelayed({
            progress.visibility = View.INVISIBLE
        }, 2000)
    }

    private fun setValuesToViews(it: ForeCast) {
        val tv_temperature: TextView = findViewById(R.id.tv_temperature)
        val tv_date: TextView = findViewById(R.id.tv_date)
        val tv_temp_max: TextView = findViewById(R.id.tv_temp_max)
        val tv_temp_min: TextView = findViewById(R.id.tv_temp_min)
        val tv_feels_like: TextView = findViewById(R.id.tv_feels_like)
        val tv_weather: TextView = findViewById(R.id.tv_weather)
        val tv_sunsrise: TextView = findViewById(R.id.tv_sunsrise)
        val tv_sunset: TextView = findViewById(R.id.tv_sunset)
        val tv_humidity: TextView = findViewById(R.id.tv_humidity)
        var iv_weather_icon: ImageView = findViewById(R.id.iv_weather_icon)

        tv_temperature.text = it.current?.temp?.roundToInt().toString()
        tv_date.text = it.current?.date.format()
        tv_temp_max.text = it.daily?.get(0)?.temp?.max?.roundToInt()?.toString()
        tv_temp_min.text = it.daily?.get(0)?.temp?.min?.roundToInt()?.toString()
        tv_feels_like.text = it.current?.feels_like?.roundToInt()?.toString()
        tv_weather.text = it.current?.weather?.get(0)?.description
        tv_sunsrise.text = it.current?.sunrise.format("hh:mm")
        tv_sunset.text = it.current?.sunset.format("hh:mm")
        tv_humidity.text = "${it.current?.humidity?.toString()} %"
    }

    private fun loadWeatherIcon(it: ForeCast) {
        var iv_weather_icon: ImageView = findViewById(R.id.iv_weather_icon)
        it.current?.weather?.get(0)?.icon?.let { icon ->
            Glide.with(this)
                .load("${Constants.iconUri}${icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }

    companion object {
        const val TOKEN = "TOKEN"
    }
}

