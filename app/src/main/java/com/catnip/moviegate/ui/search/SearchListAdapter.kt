package com.catnip.moviegate.ui.search;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.catnip.moviegate.BuildConfig
import com.catnip.moviegate.R
import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.ext.textParseFromDate
import com.catnip.moviegate.model.content.Content
import kotlinx.android.synthetic.main.list_item_favorite.view.img_poster
import kotlinx.android.synthetic.main.list_item_favorite.view.txt_title_content
import kotlinx.android.synthetic.main.list_item_favorite.view.txt_year_content
import kotlinx.android.synthetic.main.list_item_search.view.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class SearchListAdapter(private val itemClick: (Content?) -> Unit) :
    RecyclerView.Adapter<SearchListAdapter.FavoriteViewHolder>() {
    var items: MutableList<Content> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_search, parent, false)
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

        fun bindView(item: Content) {
            with(item) {
                itemView.txt_title_content.text = this.title
                itemView.txt_year_content.textParseFromDate(this.releaseDate)
                itemView.txt_type_content.text = this.contentType.toString()
                if (this.contentType == ContentType.MOVIE) {
                    itemView.txt_type_content.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorTagMovie
                        )
                    )
                } else {
                    itemView.txt_type_content.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorTagTvShows
                        )
                    )
                }
                itemView.img_poster.load(BuildConfig.BASE_POSTER_IMG_URL + this.posterPath)
            }

        }
    }

}