package com.example.shows_tomislavlovrencic.Adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shows_tomislavlovrencic.Activity.twoPane
import com.example.shows_tomislavlovrencic.Fragments.EpisodesFragment
import com.example.shows_tomislavlovrencic.Fragments.layoutManager
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.RetrofitClient
import com.example.shows_tomislavlovrencic.classes.ApiShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_listitem.view.*


const val IME = "ime"
const val LIKES = "opis"
const val SHOW_ID = "showID"


var fragmentManager: FragmentManager? = null

class MainAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            var fragment2 = EpisodesFragment()
            val bundle = Bundle()

            val item = v.tag as ApiShow
            bundle.putString(LIKES, item.likes.toString())
            bundle.putString(IME, item.title)
            bundle.putString(SHOW_ID, item.id)
            fragment2.arguments = bundle

            if (twoPane) {
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.detailsFragmentContainer, fragment2)
                fragmentTransaction?.commit()
            } else {
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.add(R.id.fragmentContainer, fragment2,"episode-details")
                fragmentTransaction?.addToBackStack("Episode details")
                fragmentTransaction?.commit()
            }


        }
    }


    var listApiShows = listOf<ApiShow>()

    override fun getItemViewType(position: Int): Int {
        var spanCount = layoutManager?.spanCount
        if (spanCount == 1) {
            return 2
        } else {
            return 1
        }
    }


    override fun getItemCount(): Int {
        return listApiShows.size


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var view: View
        if (viewType == 2) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_listitem, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_listitemgrid, parent, false)
        }
        return CustomViewHolder(view)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val item2: ApiShow = listApiShows[position]

        if (layoutManager?.spanCount == 1) {
            holder.itemView.imeSerije.text = item2.title
            holder.itemView.godinaEmitiranja.text = item2.likes.toString()
            Picasso.get().load(RetrofitClient.BASE_URL + item2.imageUrl).into(holder.itemView.slikaSerije)
        } else {
            holder.itemView.imeSerije.text = item2.title
            Picasso.get().load(RetrofitClient.BASE_URL + item2.imageUrl).into(holder.itemView.slikaSerije)
        }
        with(holder.itemView) {
            tag = item2
            setOnClickListener(onClickListener)
        }
    }


    fun setData2(shows: List<ApiShow>, fragmentManager2: FragmentManager) {
        fragmentManager = fragmentManager2
        listApiShows = shows
        notifyDataSetChanged()

    }

}


class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

