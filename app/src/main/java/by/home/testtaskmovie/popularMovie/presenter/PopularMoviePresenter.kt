package by.home.testtaskmovie.popularMovie.presenter

import by.home.testtaskmovie.popularMovie.ListPopularMovieView
import by.home.testtaskmovie.popularMovie.interactor.ListPopularMovieInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class PopularMoviePresenter(var view:ListPopularMovieView?,private val interactor:ListPopularMovieInteractor) {

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun onCreate(){
        addPage()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun addPage(query: String?=null){
        query?: CoroutineScope(Dispatchers.Main).launch {
            view?.showLoading()
            interactor.getPage()
                .flowOn(Dispatchers.Main)
                .catch {
                    if(interactor.getCash().isEmpty()) {
                        error(it.message) {
                            getFavorites()
                        }
                    }
                }
                .collect{
                    view?.addToList(0, it)
                    view?.notifyList()
                }
            view?.hideLoading()
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun search(query:String){
        if(query.isNotEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                view?.showLoading()
                interactor.search(query)
                    .flowOn(Dispatchers.Main)
                    .catch { error(it.message) }
                    .collect {
                        view?.setList(it)
                    }
                view?.notifyList()
                view?.hideLoading()
            }
        }else{
            view?.setList(interactor.getCash())
            view?.notifyList()
            view?.hideLoading()
        }

    }

    fun onClickItem(id:Long){
        view?.startDetailsMovieActivity(id)
    }

    @ExperimentalCoroutinesApi
    fun setFavoritesMovie(id:Long, isFav:Boolean){
        if(isFav) addToFavorites(id) else removeFromFavorites(id)
    }

    @ExperimentalCoroutinesApi
    private fun addToFavorites(id:Long){
        CoroutineScope(Dispatchers.Main).launch {
            view?.showLoading()
            interactor.addToFavorites(id)
                .catch { error(it.message) }
                .flowOn(Dispatchers.Main).collect{
                    view?.changeFavoriteStateItem(id,it)
            }
            view?.hideLoading()
        }
    }

    @ExperimentalCoroutinesApi
    private fun removeFromFavorites(id:Long){
        CoroutineScope(Dispatchers.Main).launch {
            view?.showLoading()
            interactor.removeFromFavorites(id)
                .catch { error(it.message) }
                .flowOn(Dispatchers.Main).collect{
                view?.changeFavoriteStateItem(id,!it)
            }
            view?.hideLoading()
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun getFavorites(){
        CoroutineScope(Dispatchers.Main).launch {
            view?.showLoading()
            interactor.getFavorites()
                .catch {
                    error(it.message)
                    view?.hideLoading()
                }.flowOn(Dispatchers.Main).collect {
                    view?.setList(it)
                    view?.notifyList()
                    view?.hideLoading()
                }

        }
    }

    private fun error(message: String?,action:(()->Unit)?=null){
        view?.hideLoading()
        view?.showError(message,action)
    }

    companion object{
        const val DEBOUNCE_SEARCH=1000L
    }

}