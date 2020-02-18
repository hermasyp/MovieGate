package com.catnip.mgfavapp.ui;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.catnip.mgfavapp.BuildConfig
import com.catnip.mgfavapp.R
import com.catnip.mgfavapp.model.Favorite
import com.catnip.mgfavapp.utils.textParseFromDate
import kotlinx.android.synthetic.main.list_item_favorite.view.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class FavoriteListAdapter(private val itemClick: (Favorite?) -> Unit) :
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {
    var items: MutableList<Favorite> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_favorite, parent, false)
        return FavoriteViewHolder(view).apply {
            itemView.setOnClickListener { itemClick(items[adapterPosition]) }
        }
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class FavoriteViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bindView(item: Favorite) {
            with(item) {
                itemView.txt_title_content.text = this.title
                itemView.txt_year_content.textParseFromDate(this.releaseDate)
                itemView.img_poster.load(BuildConfig.BASE_POSTER_IMG_URL + this.posterPath)
            }

        }
    }

}