package com.iamkdblue.randomjoke.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iamkdblue.randomjoke.R
import com.iamkdblue.randomjoke.model.Joke
import kotlinx.android.synthetic.main.item_joke.view.*

class CardStackAdapter(
        private var jokeList: List<Joke> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_joke, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(jokeList[position])
    }

    override fun getItemCount(): Int {
        return getJokes().size
    }

    fun setJokes(spots: List<Joke>) {
        this.jokeList = spots
        //notifyDataSetChanged()

    }

    fun getJokes(): List<Joke> {
        return jokeList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(joke:Joke)
        {
            itemView.tvJoke.text = joke.setup
            itemView.tvPunchline.text = joke.punchline
        }

        /*val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)*/
    }

}
