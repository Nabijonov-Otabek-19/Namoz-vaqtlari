package uz.nabijonov.otabek.prayertime.screen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import uz.nabijonov.otabek.prayertime.connection.NetworkConnection
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.databinding.FragmentDayBinding
import uz.nabijonov.otabek.prayertime.utils.Constansts

class DayFragment : Fragment() {

    private var _binding: FragmentDayBinding? = null
    private val binding get() = _binding!!

    private var CITY = "Toshkent"
    private val CHANNEL_ID = "channelId"
    private val CHANNEL_NAME = "channelName"

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constansts.regions)
        binding.autoCompleteTxt.setAdapter(adapterItems)

        binding.autoCompleteTxt.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CITY = item
            getTime()
        }

        createNotification()
        binding.swipe.isRefreshing = true
        binding.swipe.setOnRefreshListener {
            getTime()
        }

        getTime()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun getTime() {

        val url = "https://islomapi.uz/api/present/day?region=$CITY"

        val queue: RequestQueue = Volley.newRequestQueue(requireActivity())
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            binding.swipe.isRefreshing = false

            try {
                binding.TVDay.text = response.getString("region")
                binding.TVTime.text = response.getString("date")
                binding.TVDate.text = response.getString("weekday")
                val array = response.getJSONObject("times")
                binding.TVbomdod.text = array.getString("tong_saharlik")
                binding.TVquyosh.text = array.getString("quyosh")
                binding.TVpeshin.text = array.getString("peshin")
                binding.TVasr.text = array.getString("asr")
                binding.TVshom.text = array.getString("shom_iftor")
                binding.TVhufton.text = array.getString("hufton")

//                val notification = NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
//                    .setContentTitle("Peshin")
//                    .setContentText(array.getString("peshin"))
//                    .setSmallIcon(R.drawable.ic_day)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .build()
//
//                val notificationManager = NotificationManagerCompat.from(requireActivity())
//                notificationManager.notify(0, notification)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, {
            binding.swipe.isRefreshing = false
            Toast.makeText(requireActivity(), "Check Internet Connection", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(): DayFragment = DayFragment()
    }
}