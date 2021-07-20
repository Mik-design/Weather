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
import kg.tutorial.weathermvvm.models.DailyForeCast
import kotlin.math.roundToInt
class DailyForeCastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var tv_weekday: TextView
    lateinit var tv_precipitation: TextView
//    lateinit var iv_temp_max: TextView
//    lateinit var iv_temp_min: TextView
    lateinit var iv_weather_icon_two: ImageView

    fun bind(item: DailyForeCast) {
        itemView.run {
            tv_weekday = findViewById(R.id.tv_weekday)
            tv_precipitation = findViewById(R.id.tv_precipitation)
//            iv_temp_max = findViewById(R.id.tv_temp_max)
//            iv_temp_min = findViewById(R.id.tv_temp_min)
            iv_weather_icon_two = findViewById(R.id.iv_weather_icon)

            tv_weekday.text = item.date.format("dd/MM")
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()}%"
            }

//           iv_temp_max.text = item.temp?.max?.roundToInt()?.toString()
//            iv_temp_min.text = item.temp?.min?.roundToInt()?.toString()



            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon_two)
        }
    }

    companion object {
        fun create(parent: ViewGroup): DailyForeCastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily_forecast, parent, false)

            return DailyForeCastVH(view)
        }
    }
}
