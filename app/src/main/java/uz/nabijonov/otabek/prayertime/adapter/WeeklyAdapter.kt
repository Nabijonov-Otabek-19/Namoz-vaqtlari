package uz.nabijonov.otabek.prayertime.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.week_item.view.*
import uz.nabijonov.otabek.prayertime.R
import uz.nabijonov.otabek.prayertime.model.WeeklyModel

class WeeklyAdapter(private val items: ArrayList<WeeklyModel>) :
    RecyclerView.Adapter<WeeklyAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.week_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        val color = arrayListOf(
            Color.rgb(255, 134, 134),
            Color.rgb(145, 248, 149),
            Color.rgb(253, 242, 146),
            Color.rgb(255, 206, 133),
            Color.rgb(116, 193, 255)
        )
        val random = color.random()

        holder.itemView.apply {
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

    override fun getItemCount(): Int {
        return items.size
    }
}