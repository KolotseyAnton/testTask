package by.home.testtaskmovie.popularMovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.home.testtaskmovie.R
import by.home.testtaskmovie.adapters.PopularMoviesAdapter

import by.home.testtaskmovie.dataManager.RoomDataBaseManager
import by.home.testtaskmovie.network.RetrofitNetworkManager
import by.home.testtaskmovie.popularMovie.interactor.ListPopularMovieInteractor
import by.home.testtaskmovie.popularMovie.model.PopularMovieItem
import by.home.testtaskmovie.popularMovie.presenter.PopularMoviePresenter
import by.home.testtaskmovie.popularMovieDetails.PopularMovieDetailsActivity
import by.home.testtaskmovie.utils.showMessage

import kotlinx.android.synthetic.main.activity_list_popular_movie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


class ListPopularMovieActivity : AppCompatActivity(),ListPopularMovieView,TextWatcher{
    private lateinit var presenter:PopularMoviePresenter

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_popular_movie)
        searchEdTx.addTextChangedListener(this)
        recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=PopularMoviesAdapter(mutableListOf(),
                { presenter.onClickItem(it.id) },
                {item,isFav-> presenter.setFavoritesMovie(item.id,isFav) })

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        presenter.addPage(query)
                    }
                }
            })
        }
        presenter=PopularMoviePresenter(this,ListPopularMovieInteractor(RetrofitNetworkManager(),RoomDataBaseManager(applicationContext)))
        presenter.onCreate()
    }

    override fun startDetailsMovieActivity(id:Long){
        val intent=Intent(this,PopularMovieDetailsActivity::class.java)
        intent.putExtra(PopularMovieDetailsActivity.KEY_ID,id)
        startActivity(intent)
    }
    override fun addToList(position: Int, list: List<PopularMovieItem>) {
        (recyclerView.adapter as PopularMoviesAdapter).addToList(list)
    }

    override fun showLoading() {
        progress.visibility= View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility=View.GONE

    }

    override fun setList(list: List<PopularMovieItem>) {
        (recyclerView.adapter as PopularMoviesAdapter).refreshList(list)
    }

    override fun showError(message: String?,action:(()->Unit)?) {
        showMessage(recyclerView,message,action)
    }

    override fun changeFavoriteStateItem(id: Long, isFavorite: Boolean) {
        (recyclerView.adapter as PopularMoviesAdapter).changeFavItem(id,isFavorite)
    }

    override fun notifyList() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun afterTextChanged(p0: Editable?) {

        query=if(p0.isNullOrEmpty()){
            null
        }else p0.toString()

        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable,PopularMoviePresenter.DEBOUNCE_SEARCH)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    private val handler=Handler()

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val runnable= Runnable {
        presenter.search(query?:"")
    }
    private var query:String?=null

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }


}
