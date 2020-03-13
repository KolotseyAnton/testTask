package by.home.testtaskmovie.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import by.home.testtaskmovie.dataManager.DatabaseManager
import by.home.testtaskmovie.network.NetworkManager
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow

@FlowPreview
fun DatabaseManager.addToFavorites(networkManager: NetworkManager, idMovie:Long): Flow<Boolean>{
    return networkManager.getMovieDetails(idMovie).flatMapConcat { saveMovieDetails(it)}
}
@FlowPreview
fun DatabaseManager.isFavoriteMovie(idMovie:Long):Flow<Boolean>{
    return findMovieDetailById(idMovie).flatMapConcat(transform = { val  isFav:Boolean= it?.let { true } ?: false
        flow { emit(isFav) }
    })
}

fun AppCompatActivity.showMessage(view: View, message:String?, action:(()->Unit)?, nameButton:String="Ok"){
    message?.apply {
        action?.apply {
            Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE)
                .setAction(nameButton) { action.invoke() }
                .show()
        }?: Snackbar.make(view,message, Snackbar.LENGTH_SHORT).show()
    }
}