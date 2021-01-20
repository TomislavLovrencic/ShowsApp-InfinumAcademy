package com.example.shows_tomislavlovrencic.Adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shows_tomislavlovrencic.Fragments.EpisodeInfoFragment
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.classes.EpisodeApi
import kotlinx.android.synthetic.main.layout_listitem2.view.*

var nameOfShow: String = "100"
const val MAIN_TITLE = "title"
const val SEASON = "season"
const val EPISODE_ID = "ASDAS"
const val EPISODENUMBER = "episode"
const val DESC = "DESC"
const val IMAGE = "image"


class SeriesAdapter(private var listaApiEpizoda: MutableList<EpisodeApi>) :
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            var fragment2 = EpisodeInfoFragment()
            val bundle = Bundle()

            val item = v.tag as EpisodeApi
            bundle.putString(MAIN_TITLE, item.title)
            bundle.putString(DESC, item.description)
            bundle.putString(SEASON, item.season)
            bundle.putString(EPISODENUMBER, item.episodeNmb)
            bundle.putString(EPISODE_ID, item.episodeId)
            bundle.putString(IMAGE, item.imageUrl)
            fragment2.arguments = bundle

            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.add(R.id.fragmentContainer, fragment2,"episode-info")
            fragmentTransaction?.addToBackStack("Episode info")
            fragmentTransaction?.commit()



        }
    }


    override fun getItemCount(): Int {
        return listaApiEpizoda.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_listitem2, parent, false)
        return ViewHolder(cellForRow)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaApiEpizoda[position])

        with(holder.itemView) {
            tag = listaApiEpizoda[position]
            setOnClickListener(onClickListener)
        }

    }


    fun setData(list: List<EpisodeApi>, showId: String) {
        nameOfShow = showId
        this.listaApiEpizoda = list as MutableList<EpisodeApi>
        notifyDataSetChanged()
    }

    fun addEpisode(episode: EpisodeApi) {
        this.listaApiEpizoda.add(episode)
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(episode: EpisodeApi) {
            with(itemView) {

                labelaBrojac.text = "S" + episode.season.toString() + "E" + episode.episodeNmb.toString()
                labelaPoredBrojaca.text = episode.title
            }


        }
    }


}



