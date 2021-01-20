package com.example.shows_tomislavlovrencic.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shows_tomislavlovrencic.Activity.CommentActivity
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Adapters.DESC
import com.example.shows_tomislavlovrencic.Adapters.EPISODENUMBER
import com.example.shows_tomislavlovrencic.Adapters.EPISODE_ID
import com.example.shows_tomislavlovrencic.Adapters.IMAGE
import com.example.shows_tomislavlovrencic.Adapters.MAIN_TITLE
import com.example.shows_tomislavlovrencic.Adapters.SEASON
import com.example.shows_tomislavlovrencic.Models.EpisodeInfoViewModel
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_episode_info.*


var episodeId : String = ""

class EpisodeInfoFragment : Fragment() {

    var episodeTitle: String = ""
    var episodeSeason : String = ""
    var episodeEpisode : String = ""

    var description : String = ""
    var imagePath : String = ""

    private lateinit var viewModel:EpisodeInfoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val bundle = this.arguments

        if (bundle != null) {
            episodeTitle = bundle.getString(MAIN_TITLE)!!
            episodeSeason = bundle.getString(SEASON)!!
            episodeEpisode = bundle.getString(EPISODENUMBER)!!
            episodeId = bundle.getString(EPISODE_ID)!!
            description = bundle.getString(DESC)!!
            imagePath = bundle.getString(IMAGE)!!
            bundle.clear()

        }


        return inflater.inflate(R.layout.fragment_episode_info, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mProgressDialog = ProgressDialog(requireContext())


        btnBackEpisodeInfo.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        imgComments.setOnClickListener{
            var intent = Intent(requireContext(),CommentActivity::class.java)
            startActivity(intent)
        }


        viewModel = ViewModelProviders.of(this).get(EpisodeInfoViewModel::class.java)

        viewModel.getEpisodeInfo(episodeId)

        imagePath = RetrofitClient.BASE_URL + imagePath
        viewModel.liveData.observe(this, Observer {
            if(it != null) {
                if (it.isSuccessful) {
                    episodeInfoTitle.text = episodeTitle
                    episodeInfoDescription.text = description
                    episodeSeasonAndEpisode.text = "S$episodeSeason E$episodeEpisode"
                    Picasso.get().load(imagePath).into(imageEpisode)
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

    }


}

