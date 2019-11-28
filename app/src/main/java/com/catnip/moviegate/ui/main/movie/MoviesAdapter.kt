package com.catnip.moviegate.ui.main.movie

import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.catnip.moviegate.BuildConfig
import com.catnip.moviegate.R
import com.catnip.moviegate.ext.textParseFromDate
import com.catnip.moviegate.model.movies.Movie
import com.catnip.moviegate.network.PaginateResultState
import kotlinx.android.synthetic.main.list_item_lazy_load.view.*
import kotlinx.android.synthetic.main.list_item_movies.view.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MoviesAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MoviesDiffUtils()) {

    val MOVIE_VIEW_TYPE = 1
    val LAZY_LOAD_VIEW_TYPE = 2

    private var state: PaginateResultState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        return if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.list_item_movies, parent, false)
            MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.list_item_lazy_load, parent, false)
            LazyLoadItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position))
        } else {
            (holder as LazyLoadItemViewHolder).bind(state)
        }
    }

    private fun isHavingExtraRow(): Boolean {
        return state != null && state != PaginateResultState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isHavingExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHavingExtraRow() && position == itemCount - 1) {
            LAZY_LOAD_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class MoviesDiffUtils : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(movie: Movie?) {
            itemView.txt_title_content.text = movie?.title
            itemView.txt_year_content.textParseFromDate(movie?.releaseDate)
            itemView.img_poster.load(BuildConfig.BASE_POSTER_IMG_URL + movie?.posterPath)
        }
    }

    class LazyLoadItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(resultState: PaginateResultState?) {
            if (resultState != null) {
                when (resultState) {
                    PaginateResultState.LOADING -> {
                        itemView.progress_bar_item.visibility = VISIBLE
                    }
                    PaginateResultState.ERROR -> {
                        itemView.progress_bar_item.visibility = GONE
                        itemView.txt_msg_error.visibility = VISIBLE
                        itemView.txt_msg_error.text = resultState.message
                    }
                    PaginateResultState.EOF -> {
                        itemView.progress_bar_item.visibility = GONE
                        itemView.txt_msg_error.visibility = VISIBLE
                        itemView.txt_msg_error.text = resultState.message
                    }
                    else -> {
                        itemView.progress_bar_item.visibility = GONE
                    }
                }
            }
        }
    }

    fun setState(state: PaginateResultState) {
        val prevState = this.state
        val hadExtraRow = isHavingExtraRow()
        this.state = state
        val hasExtraRow = isHavingExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && prevState != state) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }
    }

}