package by.home.testtaskmovie.popularMovieDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

import by.home.testtaskmovie.R
import by.home.testtaskmovie.dataManager.RoomDataBaseManager
import by.home.testtaskmovie.databinding.ActivityPopularMovieDetailsBinding

import by.home.testtaskmovie.network.RetrofitNetworkManager
import by.home.testtaskmovie.popularMovieDetails.interactor.MovieDetailsInteractor
import by.home.testtaskmovie.popularMovieDetails.model.MovieDetail
import by.home.testtaskmovie.popularMovieDetails.presenter.MovieDetailPresenter
import by.home.testtaskmovie.utils.showMessage
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi

class PopularMovieDetailsActivity : AppCompatActivity(), MovieDetailView{

    private lateinit var binding:ActivityPopularMovieDetailsBinding
    private lateinit var presenter:MovieDetailPresenter

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_popular_movie_details)

        binding.favoriteBtn.setOnClickListener {
            presenter.setFavorite(binding.favoriteBtn.isChecked)
        }

        //FIXME: implement inject layer
        presenter= MovieDetailPresenter(this,intent.getLongExtra(KEY_ID,-1), MovieDetailsInteractor(RetrofitNetworkManager(),RoomDataBaseManager(applicationContext)))
        presenter.onCreate()
    }

    override fun setMovieDetail(model: MovieDetail) {
        binding.model=model
        Picasso.get().load(model.borderUrl).placeholder(R.drawable.ic_launcher_background).into(binding.borderImg)
        Picasso.get().load(model.imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(binding.imageView3)
        binding.executePendingBindings()
    }

    override fun setFavoriteState(isFavorite: Boolean) {
        binding.model?.isFavorite=isFavorite
        binding.favoriteBtn.isChecked=isFavorite
    }

    override fun hideProgress() {
        binding.progressBar.visibility=View.GONE
    }

    override fun showError(message: String?, action: (() -> Unit)?) {
        showMessage(binding.root,message,action)
    }

    override fun toBack() {
        onBackPressed()
    }

    override fun showProgress() {
       binding.progressBar.visibility=View.VISIBLE
    }

    companion object{
        const val KEY_ID:String="key_id"
    }
}
