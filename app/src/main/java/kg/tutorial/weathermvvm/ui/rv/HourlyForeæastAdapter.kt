package kg.tutorial.weathermvvm.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.tutorial.weathermvvm.R
import kg.tutorial.weathermvvm.models.HourlyForeCast

class HourlyForeСastAdapter : RecyclerView.Adapter<HourlyForeСastVH>() {

    private val items = arrayListOf<HourlyForeCast>()

    fun setItems(newItems: List<HourlyForeCast>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForeСastVH {
        return HourlyForeСastVH.create(parent)

    }

    override fun onBindViewHolder(holder: HourlyForeСastVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.count()
}

