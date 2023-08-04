package uz.nabijonov.otabek.prayertime.presentation.screen.month

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.databinding.FragmentMonthBinding
import uz.nabijonov.otabek.prayertime.presentation.adapter.MonthlyAdapter
import uz.nabijonov.otabek.prayertime.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class MonthScreen : Fragment(R.layout.fragment_month) {

    private val viewModel by viewModels<MonthViewModelImpl>()
    private val binding by viewBinding(FragmentMonthBinding::bind)

    private lateinit var adapterItems: ArrayAdapter<String>
    private lateinit var adapterItems2: ArrayAdapter<Int>

    @Inject
    lateinit var adapter: MonthlyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val networkConnection = NetworkConnection(requireActivity())
        networkConnection.observe(viewLifecycleOwner) {
            if (it) {
                loadData()
                binding.linearMonthData.visibility = View.VISIBLE
                binding.linearMonthNoInternet.visibility = View.GONE
            } else {
                binding.linearMonthData.visibility = View.GONE
                binding.linearMonthNoInternet.visibility = View.VISIBLE
            }
        }

        binding.monthRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.monthRecycler.adapter = adapter

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, regions)
        adapterItems2 = ArrayAdapter<Int>(requireContext(), R.layout.list_item, item_numbers)
        binding.autoCompleteMonth.setAdapter(adapterItems)
        binding.autoCompleteNumber.setAdapter(adapterItems2)

        binding.autoCompleteMonth.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CityName = item
            loadData()
        }

        binding.autoCompleteNumber.setOnItemClickListener { adapterView, _, position, _ ->
            val number = adapterView.getItemAtPosition(position)
            MONTH = number as Int
            loadData()
        }

        loadData()

        viewModel.errorData.observe(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.progressData.observe(viewLifecycleOwner) {
            binding.monthswipe.isRefreshing = it
        }

        viewModel.successData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.monthswipe.isRefreshing = true
        binding.monthswipe.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.loadData(CityName, MONTH)
    }
}