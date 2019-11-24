package com.catnip.moviegate.ui.main.movie

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.catnip.moviegate.R
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MoviesFragment : Fragment() {

    private val scopeName = getKoin().createScope("moviesListScope",named("MoviesFragment"))
    private val moviesViewModel: MoviesViewModel by scopeName.viewModel(this)

    private lateinit var moviesAdapter : MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter()
        val gridLayoutManager = GridLayoutManager(context,3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = moviesAdapter.getItemViewType(position)
                return if (viewType == moviesAdapter.MOVIE_VIEW_TYPE ) 1 else 3
            }
        }
        rv_movies.layoutManager = gridLayoutManager
        rv_movies.setHasFixedSize(true)
        rv_movies.adapter = moviesAdapter

        moviesViewModel.movies.observe(this, Observer {
            moviesAdapter.submitList(it)
        })
        moviesViewModel.resultState.observe(this, Observer {
            d(MoviesFragment::class.java.simpleName,it.message)
        })

    }

    override fun onStop() {
        super.onStop()
        scopeName.close()
    }

}