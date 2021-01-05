import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherForecast.R
import com.example.weatherForecast.model.WeatherResponse

class FiveDaysWeatherForecast(
    incomingForecastList: ArrayList<WeatherResponse>
) :
    RecyclerView.Adapter<FiveDaysWeatherForecast.ViewHolder>(){

    private val forecastList =  incomingForecastList

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = forecastList[position].date
        holder.condition.text = forecastList[position].weather.description
        holder.temp.text = forecastList[position].main.temp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_five_day_forecast,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = forecastList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.date)
        val temp: TextView = itemView.findViewById(R.id.temp)
        val condition: TextView = itemView.findViewById(R.id.condition)

    }
}
