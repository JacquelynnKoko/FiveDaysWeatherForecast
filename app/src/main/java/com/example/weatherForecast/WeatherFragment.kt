package com.example.weatherForecast


import FiveDaysWeatherForecast
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherForecast.viewModel.WeatherViewModel
import com.example.weatherForecast.database.DataBase
import kotlinx.android.synthetic.main.city_fragment.*
import org.json.JSONArray

class WeatherFragment : Fragment() {
    private var handler: Handler? = null

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        handler = Handler()
        return inflater.inflate(R.layout.city_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        context?.let { DataBase.init(it) }

        observe()

        val jsonString =
            context?.assets?.open("city.list.json")?.bufferedReader().use { it?.readText() }
        val list = JSONArray(jsonString)
        val city = ArrayList<String>()

        for (i in 0 until list.length()) {
            city.add(list.getJSONObject(i).getString("name"))
        }

        cityAutoComplete.setAdapter(
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    city
                )
            }
        )

        cityAutoComplete.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.updateWeatherData(cityAutoComplete.text.toString())
            }
        }

        cityAutoComplete.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateWeatherData(cityAutoComplete.text.toString())
            }
        }

        cityAutoComplete.setOnItemClickListener { _, _, _, _ ->
            cityAutoComplete.clearFocus()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun observe() {
        viewModel.weatherListLiveData.observe(viewLifecycleOwner, {

            val recyclerView: RecyclerView = forecastRV
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = FiveDaysWeatherForecast(it)
            recyclerView.adapter = adapter
        })
    }
}