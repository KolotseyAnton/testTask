package by.home.testtaskmovie.popularMovieDetails.presenter

import by.home.testtaskmovie.popularMovieDetails.MovieDetailView
import by.home.testtaskmovie.popularMovieDetails.interactor.MovieDetailsInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MovieDetailPresenter(private var view:MovieDetailView?,val id:Long,val interactor:MovieDetailsInteractor) {

    @ExperimentalCoroutinesApi
    fun onCreate(){
        view?.showProgress()
        getMovieDetails()
    }

    @ExperimentalCoroutinesApi
    fun setFavorite(isFavorite: Boolean){
        if(isFavorite) addToFavorites() else removeFromFavorites()
    }

    @ExperimentalCoroutinesApi
    private fun removeFromFavorites(){
        CoroutineScope(Dispatchers.Main).launch {
            interactor.removeFromFavorites(id).catch {
                view?.hideProgress()
            }.flowOn(Dispatchers.Main).collect {
                view?.setFavoriteState(it)
            }
        }
    }
    @ExperimentalCoroutinesApi
    private fun addToFavorites(){
        CoroutineScope(Dispatchers.Main).launch {
            interactor.addToFavorites(id)
                .flowOn(Dispatchers.Main)
                .catch {
                    view?.setFavoriteState(false)
                    view?.hideProgress()}
                .collect{
                    view?.setFavoriteState(it)
                    view?.hideProgress()
                }
        }
    }

    @ExperimentalCoroutinesApi
    private fun getMovieDetails(){
        CoroutineScope(Dispatchers.Main).launch {
            interactor.getMovieDetails(id)
                .flowOn(Dispatchers.Main)
                .catch {
                    view?.hideProgress()
                    view?.showError(it.message) {view?.toBack()}
                }
                .collect {
                    view?.setMovieDetail(it)
                    view?.hideProgress()
                }
        }
    }
}