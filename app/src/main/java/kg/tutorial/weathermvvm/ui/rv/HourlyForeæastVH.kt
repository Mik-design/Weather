package kg.tutorial.weathermvvm.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorial.weathermvvm.R
import kg.tutorial.weathermvvm.extensions.format
import kg.tutorial.weathermvvm.models.Constants
import kg.tutorial.weathermvvm.models.HourlyForeCast
import kotlin.math.roundToInt

class HourlyForeСastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var tv_time: TextView
    lateinit var tv_temp: TextView
    lateinit var tv_precipitation: TextView
    lateinit var iv_weather_icon_one: ImageView

    fun bind(item: HourlyForeCast){

        itemView.run {
            tv_precipitation = findViewById(R.id.tv_precipitation)
            tv_time = findViewById(R.id.tv_time)
            tv_temp = findViewById(R.id.tv_temp)
            iv_weather_icon_one = findViewById(R.id.iv_weather_icon)


            tv_time.text = item.date.format("HH:mm")
            item.probability?.let {
                tv_precipitation.text = "${(it *100).roundToInt()} %"
            }

            tv_temp.text = item.temp?.roundToInt().toString()

            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon_one)

        }
    }
    companion object {
        fun create(parent: ViewGroup): HourlyForeСastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast, parent, false)

            return HourlyForeСastVH(view)
        }
    }

}