package com.example.betstrategies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainDataAdapter(private var data: List<Data>, private val listener: MainDataAdapterListener) :
    RecyclerView.Adapter<MainDataAdapter.ItemViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Data>) {
        data = newData
        notifyDataSetChanged()
    }

    interface MainDataAdapterListener {
        fun readMoreClick(id: String)
        fun favoriteClick(id: String)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvReadMore: TextView = itemView.findViewById(R.id.tvReadMore)
        val ivAddToFavor: ImageView = itemView.findViewById(R.id.ivAddToFavor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.ivItem.setImageResource(data[position].resourceId)
        holder.tvTitle.text = data[position].title
        holder.ivItem.setOnClickListener { listener.readMoreClick(data[position].id) }
        holder.ivAddToFavor.apply {
            setImageResource(
                if (data[position].isFavor) R.drawable.ic_favor_filled
                else R.drawable.ic_favor_stroke
            )
            setOnClickListener {
                data[position].isFavor = data[position].isFavor.not()
                listener.favoriteClick(data[position].id)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}