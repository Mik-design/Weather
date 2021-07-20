package kg.tutorial.weathermvvm.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorial.weathermvvm.models.DailyForeCast
import kg.tutorial.weathermvvm.models.HourlyForeCast

class CollectionsConverter {

    @TypeConverter
    fun fromHourlyForecastListToJson(list: List<HourlyForeCast>?): String? =
        Gson().toJson(list)

    @TypeConverter
    fun fromJsonToHourlyForecastList(json: String?): List<HourlyForeCast>? =
        Gson().fromJson(json, object : TypeToken<List<HourlyForeCast>>() {}.type)

    @TypeConverter
    fun fromDailyForecastListToJson(list: List<DailyForeCast>?): String? =
        Gson().toJson(list)

    @TypeConverter
    fun fromJsonToDailyForecastList(json: String?): List<DailyForeCast>? =
        Gson().fromJson(json, object : TypeToken<List<DailyForeCast>>() {}.type)
}