package uz.nabijonov.otabek.prayertime.presentation.screen.day

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.databinding.FragmentDayBinding
import uz.nabijonov.otabek.prayertime.utils.Constansts
import uz.nabijonov.otabek.prayertime.utils.Constansts.CityName
import uz.nabijonov.otabek.prayertime.utils.NetworkConnection
import uz.nabijonov.otabek.prayertime.utils.toast

@AndroidEntryPoint
class DayScreen : Fragment(R.layout.fragment_day) {

    private val binding by viewBinding(FragmentDayBinding::bind)
    private val viewModel: DayViewModel by viewModels<DayViewModelImpl>()

    private lateinit var adapterItems: ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(requireActivity()) {
            if (it) {
                binding.linearData.visibility = View.VISIBLE
                binding.linearNoInternet.visibility = View.GONE
                viewModel.loadData(CityName)
            } else {
                binding.linearData.visibility = View.GONE
                binding.linearNoInternet.visibility = View.VISIBLE
            }
        }

        viewModel.errorData.observe(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.progressData.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = it
        }

        viewModel.successData.observe(viewLifecycleOwner) {
            binding.apply {
                TVDay.text = it.region
                TVDate.text = it.weekday
                TVTime.text = it.date

                TVbomdod.text = it.times.tong_saharlik
                TVquyosh.text = it.times.quyosh
                TVpeshin.text = it.times.peshin
                TVasr.text = it.times.asr
                TVshom.text = it.times.shom_iftor
                TVhufton.text = it.times.hufton
            }
        }

        adapterItems =
            ArrayAdapter<String>(requireContext(), R.layout.list_item, Constansts.regions)
        binding.autoCompleteTxt.setAdapter(adapterItems)

        binding.autoCompleteTxt.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position).toString()
            CityName = item
            viewModel.loadData(CityName)
        }

        binding.swipe.isRefreshing = true
        binding.swipe.setOnRefreshListener {
            viewModel.loadData(CityName)
        }
    }
}