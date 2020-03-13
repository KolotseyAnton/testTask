package by.home.testtaskmovie.popularMovie

import by.home.testtaskmovie.popularMovie.model.PopularMovieItem

interface ListPopularMovieView {

    fun addToList(position:Int, list: List<PopularMovieItem>)
    fun showLoading()
    fun hideLoading()
    fun setList(list:List<PopularMovieItem>)
    fun showError(message: String?,action:(()->Unit)?)
    fun changeFavoriteStateItem(id:Long,isFavorite:Boolean)
    fun startDetailsMovieActivity(id: Long)
    fun notifyList()
}