package uz.nabijonov.otabek.prayertime.presentation.screen.week

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.nabijonov.otabek.prayertime.MainViewModel
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.databinding.FragmentWeekBinding
import uz.nabijonov.otabek.prayertime.presentation.adapter.WeeklyAdapter
import uz.nabijonov.otabek.prayertime.utils.Constansts
import uz.nabijonov.otabek.prayertime.utils.Constansts.CityName
import uz.nabijonov.otabek.prayertime.utils.NetworkConnection

class WeekScreen : Fragment(R.layout.fragment_week) {

    private val binding by viewBinding(FragmentWeekBinding::bind)

    private lateinit var viewModel: MainViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constansts.regions)
        binding.autoCompleteWeek.setAdapter(adapterItems)

        binding.autoCompleteWeek.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CityName = item
            binding.TVregion.text = CityName
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
        viewModel.getWeeklyTimes(CityName)
    }

//    private fun getTime() {
//        val today = "https://islomapi.uz/api/present/day?region=Toshkent"
//        val month = "https://islomapi.uz/api/monthly?region=Toshkent&month=4"
//        val week = "https://islomapi.uz/api/present/week?region=Toshkent"

    companion object {
        @JvmStatic
        fun newInstance(): WeekScreen = WeekScreen()
    }
}