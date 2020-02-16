package com.catnip.moviegate.ui.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.api.get
import com.catnip.moviegate.BuildConfig
import com.catnip.moviegate.R
import com.catnip.moviegate.data.local.dao.FavoriteDao
import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.data.local.entity.Favorite
import com.catnip.moviegate.data.network.AppScheduler
import com.catnip.moviegate.ext.addTo
import com.catnip.moviegate.ext.performOnBackOutOnMain
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class RemoteViewsFavoriteWidget(
    private val context: Context
) : RemoteViewsService.RemoteViewsFactory, KoinComponent {

    private val TAG: String = RemoteViewsFavoriteWidget::class.java.simpleName
    private var favorites = mutableListOf<Favorite>()
    private lateinit var compositeDisposable: CompositeDisposable
    private val dao by inject<FavoriteDao>()
    private val appScheduler by inject<AppScheduler>()
    override fun onCreate() {
        compositeDisposable = CompositeDisposable()
        fetchData()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun onDataSetChanged() {
        fetchData()
    }

    private fun fetchData() {
        dao.getAll(ContentType.MOVIE.toString())
            .performOnBackOutOnMain(appScheduler)
            .subscribe(
                {
                    favorites.clear()
                    favorites.addAll(it)
                },
                {
                    Log.d(TAG, it?.message)
                }
            ).addTo(compositeDisposable)
    }

    override fun getViewAt(pos: Int): RemoteViews {
        val movie = favorites[pos]
        val view = RemoteViews(context.packageName, R.layout.list_item_widget)
        val bitmap = runBlocking {
            Coil.get(BuildConfig.BASE_POSTER_IMG_URL + movie.posterPath) {
                size(100,150)
            }.toBitmap()
        }
        view.setImageViewBitmap(R.id.img_widget_poster, bitmap)

        view.setTextViewText(R.id.txt_title_content, movie.title)
        val intent = Intent()
        intent.putExtra(FavoriteStackImageWidget.PARAM_ID_CONTENT, movie.id)
        view.setOnClickFillInIntent(R.id.img_widget_poster, intent)
        return view
    }

    override fun getCount(): Int = favorites.size

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}