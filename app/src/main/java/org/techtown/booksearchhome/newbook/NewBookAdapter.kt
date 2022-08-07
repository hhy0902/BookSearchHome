package org.techtown.booksearchhome.newbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.booksearchhome.R
import org.techtown.booksearchhome.newbook.model.Item

class NewBookAdapter (val newBookItemClick : (Item) -> Unit) : ListAdapter<Item, NewBookAdapter.ViewHolder>(differ) {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item : Item) {
            val coverImage = itemView.findViewById<ImageView>(R.id.coverImage)
            val title = itemView.findViewById<TextView>(R.id.titleTextView)
            val author = itemView.findViewById<TextView>(R.id.authorTextView)
            val priceSales = itemView.findViewById<TextView>(R.id.priceSalesTextView)
            val customerReviewRank = itemView.findViewById<TextView>(R.id.customerReviewRankTextView)

            title.text = "[${item.categoryName?.split(">")?.get(0)}] " + item.title
            author.text = item.author + " | " + item.publisher + " | " + item.pubDate
            priceSales.text = item.priceStandard.toString()+"원" + " → " + item.priceSales.toString()+"원"

            when(item.customerReviewRank) {
                10 -> customerReviewRank.text = "⭐⭐⭐⭐⭐"
                9 -> customerReviewRank.text = "⭐⭐⭐⭐"
                8 -> customerReviewRank.text = "⭐⭐⭐⭐"
                7 -> customerReviewRank.text = "⭐⭐⭐"
                6 -> customerReviewRank.text = "⭐⭐⭐"
                5 -> customerReviewRank.text = "⭐⭐"
                4 -> customerReviewRank.text = "⭐⭐"
                3 -> customerReviewRank.text = "⭐"
                2 -> customerReviewRank.text = "⭐"
                1 -> customerReviewRank.text = "👎"
                else -> customerReviewRank.text = "🧐"
            }

            Glide.with(coverImage.context)
                .load(item.cover)
                .into(coverImage)

            itemView.setOnClickListener {
                newBookItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.newbook_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList.get(position))
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }

}

































