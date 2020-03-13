package by.home.testtaskmovie.popularMovie.interactor

import by.home.testtaskmovie.dataManager.DatabaseManager
import by.home.testtaskmovie.network.NetworkManager
import by.home.testtaskmovie.popularMovie.model.PopularMovieItem
import by.home.testtaskmovie.utils.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class ListPopularMovieInteractor(private val networkManager: NetworkManager,private val databaseManager: DatabaseManager) {

    private val list= mutableListOf<PopularMovieItem>()
    private var currentPage: Int = 0

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getPage(): Flow<List<PopularMovieItem>> {
        currentPage++
        val apiFlow=networkManager.getPopularMovies(currentPage)
            .flatMapConcat(transform = { it -> flow { it.results.forEach { emit(it) }}})
            .map { val item=resultToPopularMovieItem(it,false)
                list.add(item)
                item}
            .catch {
                currentPage--
                error("error: connection to server")
            }
        return flow { emit(mergeWithFavorites(apiFlow).toList()) }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun search(query:String):Flow<List<PopularMovieItem>>{
        return flow {  emit(networkManager.search(query)
            .flatMapConcat(transform = { it -> flow { it.results.forEach { emit(it) }}})
            .map { resultToPopularMovieItem(it,false)}.toList())}
            .catch { error("error: connection to server") }
    }

   @FlowPreview
   fun getFavorites():Flow<List<PopularMovieItem>>{
        return flow { emit(databaseManager.getAllMovieDetail().flatMapConcat { it->
            flow {
                it.forEach { emit(it) }
            }.map {
                val m= movieDetailsToPopularMovieItem(it,true)
                list.add(m)
                m
            }}.toList())}
    }

    fun getCash()=list

    fun addToFavorites(id:Long):Flow<Boolean>{
       return databaseManager.addToFavorites(networkManager,id)
    }

    @ExperimentalCoroutinesApi
    fun removeFromFavorites(id:Long):Flow<Boolean>{
        return databaseManager.removeMovieDetails(id).flowOn(Dispatchers.IO)
    }

    private fun mergeWithFavorites(flow: Flow<PopularMovieItem>):Flow<PopularMovieItem>{
        return flow.map { item ->
            databaseManager.findMovieDetailById(item.id).collect {
                it?.apply {
                    item.isFavorite = true
                }
            }
            item
        }
    }
}
