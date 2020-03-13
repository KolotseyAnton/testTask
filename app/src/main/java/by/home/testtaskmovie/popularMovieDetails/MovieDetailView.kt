package by.home.testtaskmovie.popularMovieDetails

import by.home.testtaskmovie.popularMovieDetails.model.MovieDetail

interface MovieDetailView {
    fun setMovieDetail(model:MovieDetail)
    fun setFavoriteState(isFavorite:Boolean)
    fun showProgress()
    fun hideProgress()
    fun showError(message: String?,action:(()->Unit)?)
    fun toBack()
}