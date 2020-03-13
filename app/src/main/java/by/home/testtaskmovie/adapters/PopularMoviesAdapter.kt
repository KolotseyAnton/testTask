package by.home.testtaskmovie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import by.home.testtaskmovie.R
import by.home.testtaskmovie.popularMovie.model.PopularMovieItem
import com.squareup.picasso.Picasso

class PopularMoviesAdapter(private val list:MutableList<PopularMovieItem>,
                           private val onClick:((PopularMovieItem)->Unit)?,
                           private val onClickFav:((PopularMovieItem, Boolean)->Unit)?) : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {

        val item=list[position]

        holder.itemView.setOnClickListener { onClick?.invoke(item) }

        holder.toogleBtn.setOnClickListener { onClickFav?.invoke(item,holder.toogleBtn.isChecked) }

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
      return PopularMoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_item,parent,false))
    }

    fun addToList(items:List<PopularMovieItem>){
        this.list.addAll(items)
    }

    fun refreshList(items:List<PopularMovieItem>){
        this.list.clear()
        this.list.addAll(items)
    }
    fun changeFavItem(id:Long, isFav:Boolean){
        val index= list.indexOf(list.firstOrNull { popularMovieItem -> popularMovieItem.id==id}?.apply {
            isFavorite=isFav
        })
        notifyItemChanged(index)
    }

    class PopularMoviesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        private val image:ImageView=itemView.findViewById(R.id.imageView)
        private val nameTv:TextView=itemView.findViewById(R.id.nameTv)
        private val yearTv:TextView=itemView.findViewById(R.id.yearTv)
        private val ratingTv:TextView=itemView.findViewById(R.id.ratingTv)
        val toogleBtn:ToggleButton=itemView.findViewById(R.id.toggleButton)

        fun bind(item:PopularMovieItem){
            nameTv.text=item.name
            yearTv.text=item.year
            ratingTv.text=item.rating.toString()
            toogleBtn.isChecked=item.isFavorite

            Picasso.get().load(item.imageUrl).placeholder(R.drawable.ic_launcher_background).into(image)
        }
    }

}