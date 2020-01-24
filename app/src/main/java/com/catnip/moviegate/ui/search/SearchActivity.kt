package com.catnip.moviegate.ui.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.moviegate.R
import com.catnip.moviegate.data.local.entity.ContentType
import com.catnip.moviegate.data.network.ResultState
import com.catnip.moviegate.di.ScopeNames
import com.catnip.moviegate.ui.detailmovie.DetailMovieActivity
import com.catnip.moviegate.ui.detailtvshow.DetailTvShowActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchActivity : AppCompatActivity() {

    private val scopeId = "searchActivityScope"
    private val scopeName = getKoin().createScope(scopeId, named(ScopeNames.SearchActivityScope))
    private val viewModel: SearchViewModel by scopeName.viewModel(this)
    private val TAG = SearchActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchMenu = menu?.findItem(R.id.action_search)
        val searchView = searchMenu?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.txt_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //todo : submit to api
                query?.let {
                    viewModel.getSearchResult(it)
                }
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    fun setupSearch() {
        rv_search_result.layoutManager = LinearLayoutManager(this)
        val adapter = SearchListAdapter {
            if (it?.contentType == ContentType.MOVIE) {
                DetailMovieActivity.run(this, it.id)
            } else {
                DetailTvShowActivity.run(this, it?.id)
            }
        }
        rv_search_result.adapter = adapter
        viewModel.searchResult.observe(this) {
            when (it) {
                is ResultState.Progress -> {
                    Log.d(TAG, "On progress")
                    adapter.items = mutableListOf()
                    txt_msg_error.visibility = View.GONE
                    pb_loading_search.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    it.data.let { sr ->
                        pb_loading_search.visibility = View.GONE
                        val data = sr.mapToContents()
                        if (data.size != 0) {
                            adapter.items = data
                        } else {
                            txt_msg_error.visibility = View.VISIBLE
                            txt_msg_error.text = getString(R.string.txt_search_not_found)
                        }
                    }

                }
                is ResultState.Failure -> {
                    Log.d(TAG, "Error")
                    pb_loading_search.visibility = View.GONE
                    txt_msg_error.visibility = View.VISIBLE
                    txt_msg_error.text = getString(R.string.txt_search_not_found)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeName.close()
    }


}
