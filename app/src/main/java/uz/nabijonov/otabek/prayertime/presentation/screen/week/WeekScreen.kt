package uz.nabijonov.otabek.prayertime.presentation.screen.week

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.databinding.FragmentWeekBinding
import uz.nabijonov.otabek.prayertime.presentation.adapter.WeeklyAdapter
import uz.nabijonov.otabek.prayertime.utils.*
import uz.nabijonov.otabek.prayertime.utils.NetworkConnection
import uz.nabijonov.otabek.prayertime.utils.toast
import javax.inject.Inject

@AndroidEntryPoint
class WeekScreen : Fragment(R.layout.fragment_week) {

    private val viewModel by viewModels<WeekViewModelImpl>()
    private val binding by viewBinding(FragmentWeekBinding::bind)

    private lateinit var adapterItems: ArrayAdapter<String>

    @Inject
    lateinit var adapter: WeeklyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) {
            if (it) {
                loadData()
                binding.linearWeekData.visibility = View.VISIBLE
                binding.linearWeekNoInternet.visibility = View.GONE
            } else {
                binding.linearWeekData.visibility = View.GONE
                binding.linearWeekNoInternet.visibility = View.VISIBLE
            }
        }

        binding.weekRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.weekRecycler.adapter = adapter

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, regions)
        binding.autoCompleteWeek.setAdapter(adapterItems)

        binding.autoCompleteWeek.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CityName = item
            binding.TVregion.text = CityName
            loadData()
        }

        loadData()

        viewModel.errorData.observe(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.progressData.observe(viewLifecycleOwner) {
            binding.weekswipe.isRefreshing = it
        }

        viewModel.successData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.weekswipe.isRefreshing = true
        binding.weekswipe.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.loadData(CityName)
    }
}