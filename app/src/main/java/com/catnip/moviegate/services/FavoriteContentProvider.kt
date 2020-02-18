package com.catnip.moviegate.services

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.catnip.moviegate.data.local.dao.FavoriteDao
import com.catnip.moviegate.data.local.entity.Favorite
import com.catnip.moviegate.data.local.entity.Favorite.Companion.AUTHORITY
import org.koin.core.KoinComponent
import org.koin.core.get

class FavoriteContentProvider : ContentProvider(), KoinComponent {
    private lateinit var dao: FavoriteDao

    companion object {
        private const val FAVORITES = 1
        private const val FAVORITES_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, Favorite.TABLE_NAME, FAVORITES)
            //no need actualy
            uriMatcher.addURI(AUTHORITY, Favorite.TABLE_NAME + "/#", FAVORITES_ID)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        dao = get()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor =  when (uriMatcher.match(uri)) {
            FAVORITES -> dao.getAllMoviesFavCursor()
            else -> null
        }
        cursor?.setNotificationUri(context?.contentResolver,uri)
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
