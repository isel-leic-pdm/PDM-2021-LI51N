package pt.isel.jht.pdm.currencies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrenciesAdapter(context: Context) : RecyclerView.Adapter<CurrenciesAdapter.ItemViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var items = emptyList<ExchangeRate>()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val targetView = itemView.findViewById<TextView>(android.R.id.text1)
        val rateView = itemView.findViewById<TextView>(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.targetView.text = item.targetCurrency
        holder.rateView.text = item.rate.toString()
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items : List<ExchangeRate>) {
        this.items = items
        notifyDataSetChanged()
    }
}
