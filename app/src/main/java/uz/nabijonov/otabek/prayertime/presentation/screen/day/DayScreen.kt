package uz.nabijonov.otabek.prayertime.presentation.screen.day

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.databinding.FragmentDayBinding
import uz.nabijonov.otabek.prayertime.utils.Constansts
import uz.nabijonov.otabek.prayertime.utils.Constansts.CityName
import uz.nabijonov.otabek.prayertime.utils.NetworkConnection
import uz.nabijonov.otabek.prayertime.utils.toast

class DayScreen : Fragment(R.layout.fragment_day) {

    private val binding by viewBinding(FragmentDayBinding::bind)

    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(requireActivity()) {
            if (it) {
                binding.linearData.visibility = View.VISIBLE
                binding.linearNoInternet.visibility = View.GONE
                getTime()
            } else {
                binding.linearData.visibility = View.GONE
                binding.linearNoInternet.visibility = View.VISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constansts.regions)
        binding.autoCompleteTxt.setAdapter(adapterItems)

        binding.autoCompleteTxt.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CityName = item
            getTime()
        }

        binding.swipe.isRefreshing = true
        binding.swipe.setOnRefreshListener {
            getTime()
        }

        getTime()
    }

    private fun getTime() {

        val url = "https://islomapi.uz/api/present/day?region=$CityName"

        val queue: RequestQueue = Volley.newRequestQueue(requireActivity())
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            binding.swipe.isRefreshing = false

            try {
                binding.apply {
                    TVDay.text = response.getString("region")
                    TVTime.text = response.getString("date")
                    TVDate.text = response.getString("weekday")
                    val array = response.getJSONObject("times")
                    TVbomdod.text = array.getString("tong_saharlik")
                    TVquyosh.text = array.getString("quyosh")
                    TVpeshin.text = array.getString("peshin")
                    TVasr.text = array.getString("asr")
                    TVshom.text = array.getString("shom_iftor")
                    TVhufton.text = array.getString("hufton")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, {
            binding.swipe.isRefreshing = false
            toast("Check Internet Connection")
        })
        queue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(): DayScreen = DayScreen()
    }
}