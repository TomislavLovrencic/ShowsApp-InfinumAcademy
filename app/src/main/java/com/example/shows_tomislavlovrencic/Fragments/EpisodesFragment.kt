package com.example.shows_tomislavlovrencic.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Activity.twoPane
import com.example.shows_tomislavlovrencic.Adapters.IME
import com.example.shows_tomislavlovrencic.Adapters.LIKES
import com.example.shows_tomislavlovrencic.Adapters.SHOW_ID
import com.example.shows_tomislavlovrencic.Adapters.SeriesAdapter
import com.example.shows_tomislavlovrencic.Models.EpisodesViewModel
import com.example.shows_tomislavlovrencic.Models.LikeShowViewModel
import com.example.shows_tomislavlovrencic.Models.ShowDetailsViewModel
import com.example.shows_tomislavlovrencic.R
import kotlinx.android.synthetic.main.fragment_episodes.*

var emailString :String? = null
var showIdResult: String? = null
var boolean : Boolean = false
const val DESCRIPTION_LIMIT = 50
class EpisodesFragment : Fragment() {

    private lateinit var adapter: SeriesAdapter
    private lateinit var viewModelLike : LikeShowViewModel
    private lateinit var viewModel: EpisodesViewModel
    private lateinit var viewModel2: ShowDetailsViewModel

    var showName: String = ""
    var showId: String = "100"
    var showLikes : Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val bundle = this.arguments
        if (bundle != null) {
            showLikes = bundle.getInt(LIKES)!!
            showName = bundle.getString(IME)!!
            showId = bundle.getString(SHOW_ID)!!

        }
        return inflater.inflate(R.layout.fragment_episodes, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mProgressDialog = ProgressDialog(requireContext())

        showIdResult = showId




        btnBackEpisodes.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        buttonAddEpisode.setOnClickListener {
            val fragmentManager = fragmentManager
            val fragment = AddEpisodeFragment()
            val fragmentTransaction = fragmentManager?.beginTransaction()
            if (twoPane) {
                fragmentTransaction?.add(R.id.detailsFragmentContainer, fragment)
                fragmentTransaction?.addToBackStack("Add episode")
                fragmentTransaction?.commit()
            } else {
                fragmentTransaction?.add(R.id.fragmentContainer, fragment)
                fragmentTransaction?.addToBackStack("Add episode")
                fragmentTransaction?.commit()
            }

        }



        viewModelLike = ViewModelProviders.of(this).get(LikeShowViewModel::class.java)
        viewModel2 = ViewModelProviders.of(this).get(ShowDetailsViewModel::class.java)

        viewModel2.getData()

        viewModel2.liveData2.observe(this, Observer {
            if (it != null) {
                if (it.isSuccessful) {
                    textViewShowName.text = it.apiShow!!.title
                    textViewNumberOfLikes.text = it.apiShow.likes.toString()
                    textViewShowDescription.text = it.apiShow.description
                } else {
                    viewModel2.resetLiveData()
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle(it.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        })

        SwipreRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                refreshItems()
            }

        })

        myRecyclerViewSeries.layoutManager = LinearLayoutManager(requireContext())
        adapter = SeriesAdapter(arrayListOf())
        myRecyclerViewSeries.adapter = adapter


        viewModel = ViewModelProviders.of(this).get(EpisodesViewModel::class.java)
        tokenString = viewModel.getToken()!!
        emailString = viewModel.getEmail()

        viewModel.getData()

        viewModel.liveData.observe(this, Observer {
            if (it != null) {
                if (it.isSuccessful) {
                    if(it.apiEpisodesList?.isNotEmpty()!!){
                        adapter.setData(it.apiEpisodesList, showId)
                    }
                    else{
                        textViewLabel1.visibility = View.VISIBLE
                        textViewLabel2.visibility = View.VISIBLE
                        textViewLabel3.visibility = View.VISIBLE
                        imgLogoEpisodes.visibility = View.VISIBLE
                    }

                } else {
                    viewModel.resetLiveData()
                    val dialog = AlertDialog.Builder(requireContext())
                    dialog.setTitle(it.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        })


        markIcon(showId)


        imgThumbsUp.setOnClickListener{
            if(viewModelLike.getLikeStatus(showId) == 0 || viewModelLike.getLikeStatus(showId) == 2 ) {
                viewModelLike.likeShow(tokenString, showId)
                imgThumbsUp.isEnabled = false
                imgThumbsUp.setBackgroundResource(R.drawable.round_color_button)
                imgThumbsDown.isEnabled = true
                imgThumbsDown.setBackgroundResource(R.drawable.rounded_button)
                textViewNumberOfLikes.text = (textViewNumberOfLikes.text.toString().toInt() + 1).toString()
            }
            else{
                Toast.makeText(requireContext(),"You have already voted",Toast.LENGTH_SHORT).show()
            }


        }
        imgThumbsDown.setOnClickListener{
            if(viewModelLike.getLikeStatus(showId) == 0 || viewModelLike.getLikeStatus(showId) == 1) {
                viewModelLike.dislikeShow(tokenString, showId)
                imgThumbsDown.isEnabled = false
                imgThumbsDown.setBackgroundResource(R.drawable.round_color_button)
                imgThumbsUp.isEnabled = true
                imgThumbsUp.setBackgroundResource(R.drawable.rounded_button)
                textViewNumberOfLikes.text = (textViewNumberOfLikes.text.toString().toInt() - 1).toString()
            }
            else{
                Toast.makeText(requireContext(),"You have already voted",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun markIcon(showId : String){
        if(viewModelLike.getLikeStatus(showId) == 1){
            imgThumbsDown.isEnabled = true
            imgThumbsDown.setBackgroundResource(R.drawable.rounded_button)
            imgThumbsUp.isEnabled = false
            imgThumbsUp.setBackgroundResource(R.drawable.round_color_button)
        }
        else if(viewModelLike.getLikeStatus(showId) == 2){
            imgThumbsDown.isEnabled = false
            imgThumbsDown.setBackgroundResource(R.drawable.round_color_button)
            imgThumbsUp.isEnabled = true
            imgThumbsUp.setBackgroundResource(R.drawable.rounded_button)
        }

    }

    fun refreshItems(){
        if(episodeLocal != null && boolean) {
            adapter.addEpisode(episodeLocal!!)
            adapter.notifyDataSetChanged()
            SwipreRefreshLayout.isRefreshing = false
            boolean = false
        }
        else{
            SwipreRefreshLayout.isRefreshing = false
        }
    }


}