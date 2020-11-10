package pt.isel.jht.pdm.hangman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class HangmanHistoryAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<HangmanHistoryAdapter.ItemViewHolder>() {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private val inflater = LayoutInflater.from(context)
    private var items = emptyList<HangmanHistoryItem>()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordView = itemView.findViewById<TextView>(android.R.id.text1)
        val dateView = itemView.findViewById<TextView>(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.wordView.text = item.word
        holder.dateView.text = dateFormatter.format(item.date)
    }

    override fun getItemCount() = items.size

    internal fun setItems(items: List<HangmanHistoryItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}
