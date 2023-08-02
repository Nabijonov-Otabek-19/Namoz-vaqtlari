package uz.nabijonov.otabek.prayertime.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.nabijonov.otabek.prayertime.data.common.MonthlyItem
import uz.nabijonov.otabek.prayertime.databinding.MonthItemBinding
import javax.inject.Inject

class MonthlyAdapter @Inject constructor() : RecyclerView.Adapter<MonthlyAdapter.ItemHolder>() {

    private var list: List<MonthlyItem> = arrayListOf()

    fun setData(l: List<MonthlyItem>) {
        list = l
        notifyDataSetChanged()
    }

    inner class ItemHolder(private val binding: MonthItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

            val item = list[adapterPosition]
            binding.apply {
                TVDate.text = (adapterPosition + 1).toString()
                TVbomdod.text = item.times.tong_saharlik
                TVquyosh.text = item.times.quyosh
                TVpeshin.text = item.times.peshin
                TVasr.text = item.times.asr
                TVshom.text = item.times.shom_iftor
                TVhufton.text = item.times.hufton
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MonthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = list.size
}