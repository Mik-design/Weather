package kg.tutorial.weathermvvm.repo

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kg.tutorial.weathermvvm.models.ForeCast
import kg.tutorial.weathermvvm.network.WeatherApi
import kg.tutorial.weathermvvm.storage.ForeCastDatabase

class WeatherRepo(
    private val db: ForeCastDatabase,
    private val weatherApi: WeatherApi
) {

    fun getWeatherFromApi(): Single<ForeCast> {
        return weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getForeCastFromDbAsLIve() = db.forecastDao().getAll()
}