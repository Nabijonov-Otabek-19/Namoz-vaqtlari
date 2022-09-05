package uz.nabijonov.otabek.prayertime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.nabijonov.otabek.prayertime.databinding.MonthItemBinding
import uz.nabijonov.otabek.prayertime.model.MonthlyModel
import kotlin.collections.ArrayList

class MonthlyAdapter(private val items: ArrayList<MonthlyModel>) :
    RecyclerView.Adapter<MonthlyAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: MonthItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MonthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            TVDate.text = (position + 1).toString()
            TVbomdod.text = item.times.tong_saharlik
            TVquyosh.text = item.times.quyosh
            TVpeshin.text = item.times.peshin
            TVasr.text = item.times.asr
            TVshom.text = item.times.shom_iftor
            TVhufton.text = item.times.hufton
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}