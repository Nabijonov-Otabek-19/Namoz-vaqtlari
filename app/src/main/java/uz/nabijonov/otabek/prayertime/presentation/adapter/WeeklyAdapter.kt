package uz.nabijonov.otabek.prayertime.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.nabijonov.otabek.prayertime.databinding.WeekItemBinding
import uz.nabijonov.otabek.prayertime.data.common.WeeklyItem
import javax.inject.Inject

class WeeklyAdapter @Inject constructor() : RecyclerView.Adapter<WeeklyAdapter.ItemHolder>() {

    private var list: List<WeeklyItem> = arrayListOf()

    fun setData(l: List<WeeklyItem>) {
        list = l
        notifyDataSetChanged()
    }

    inner class ItemHolder(private val binding: WeekItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = list[adapterPosition]

            val color = arrayListOf(
                Color.rgb(255, 134, 134),
                Color.rgb(145, 248, 149),
                Color.rgb(253, 242, 146),
                Color.rgb(255, 206, 133),
                Color.rgb(116, 193, 255)
            )
            val random = color.random()

            binding.apply {
                weekCard.setCardBackgroundColor(random)
                TVDate.text = item.weekday
                TVDay.text = item.date.split(",")[0]
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
            WeekItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = list.size
}