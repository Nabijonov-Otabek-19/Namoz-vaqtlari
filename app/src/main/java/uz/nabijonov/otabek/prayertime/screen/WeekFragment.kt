package uz.nabijonov.otabek.prayertime.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.nabijonov.otabek.prayertime.MainViewModel
import uz.nabijonov.otabek.prayertime.connection.NetworkConnection
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.adapter.WeeklyAdapter
import uz.nabijonov.otabek.prayertime.databinding.FragmentWeekBinding
import uz.nabijonov.otabek.prayertime.utils.Constansts

class WeekFragment : Fragment() {

    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private var CITY = "Toshkent"

    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(requireActivity()) {
            if (it) {
                binding.linearWeekData.visibility = View.VISIBLE
                binding.linearWeekNoInternet.visibility = View.GONE
                loadData()
            } else {
                binding.linearWeekData.visibility = View.GONE
                binding.linearWeekNoInternet.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constansts.regions)
        binding.autoCompleteWeek.setAdapter(adapterItems)

        binding.autoCompleteWeek.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CITY = item
            binding.TVregion.text = CITY
            loadData()
        }

        loadData()

        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progress.observe(requireActivity()) {
            binding.weekswipe.isRefreshing = it
        }

        viewModel.weeklyTimes.observe(requireActivity()) {
            binding.weekRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.weekRecycler.adapter = WeeklyAdapter(it)
        }

        binding.weekswipe.isRefreshing = true
        binding.weekswipe.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getWeeklyTimes(CITY)
    }

//    private fun getTime() {
//        val today = "https://islomapi.uz/api/present/day?region=Toshkent"
//        val month = "https://islomapi.uz/api/monthly?region=Toshkent&month=4"
//        val week = "https://islomapi.uz/api/present/week?region=Toshkent"

    companion object {
        @JvmStatic
        fun newInstance(): WeekFragment = WeekFragment()
    }
}