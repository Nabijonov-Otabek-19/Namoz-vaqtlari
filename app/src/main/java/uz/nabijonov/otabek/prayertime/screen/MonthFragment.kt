package uz.nabijonov.otabek.prayertime.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.nabijonov.otabek.prayertime.MainViewModel
import uz.nabijonov.otabek.prayertime.connection.NetworkConnection
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.adapter.MonthlyAdapter
import uz.nabijonov.otabek.prayertime.databinding.FragmentMonthBinding
import uz.nabijonov.otabek.prayertime.utils.Constansts

class MonthFragment : Fragment() {

    private var _binding: FragmentMonthBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private var CITY = "Toshkent"
    private var NUMBER = 1

    private val item_numbers = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    lateinit var adapterItems: ArrayAdapter<String>
    lateinit var adapterItems2: ArrayAdapter<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(requireActivity()) {
            if (it) {
                binding.linearMonthData.visibility = View.VISIBLE
                binding.linearMonthNoInternet.visibility = View.GONE
                loadData()
            } else {
                binding.linearMonthData.visibility = View.GONE
                binding.linearMonthNoInternet.visibility = View.VISIBLE
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constansts.regions)
        adapterItems2 = ArrayAdapter<Int>(requireContext(), R.layout.list_item, item_numbers)
        binding.autoCompleteMonth.setAdapter(adapterItems)
        binding.autoCompleteNumber.setAdapter(adapterItems2)

        binding.autoCompleteMonth.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CITY = item
            loadData()
        }

        binding.autoCompleteNumber.setOnItemClickListener { adapterView, _, position, _ ->
            val number = adapterView.getItemAtPosition(position)
            NUMBER = number as Int
            loadData()
        }

        loadData()

        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progress.observe(requireActivity()) {
            binding.monthswipe.isRefreshing = it
        }

        viewModel.monthlyTimes.observe(requireActivity()) {
            binding.monthRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.monthRecycler.adapter = MonthlyAdapter(it)
        }

        binding.monthswipe.isRefreshing = true
        binding.monthswipe.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getMonthlyTimes(CITY, NUMBER)
    }


    companion object {
        @JvmStatic
        fun newInstance(): MonthFragment = MonthFragment()
    }
}