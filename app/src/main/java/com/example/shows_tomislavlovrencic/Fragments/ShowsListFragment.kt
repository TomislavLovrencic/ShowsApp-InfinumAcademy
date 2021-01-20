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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shows_tomislavlovrencic.Activity.LoginActivity
import com.example.shows_tomislavlovrencic.Activity.mProgressDialog
import com.example.shows_tomislavlovrencic.Adapters.MainAdapter
import com.example.shows_tomislavlovrencic.Models.SharedPrefViewModel
import com.example.shows_tomislavlovrencic.Models.ShowsViewModel
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.apiResponses.ApiResponse
import kotlinx.android.synthetic.main.fragment_shows_list.*


var layoutManager: GridLayoutManager? = null

class ShowsListFragment : Fragment() {
    private lateinit var viewModel: ShowsViewModel
    private lateinit var viewModelPrefs : SharedPrefViewModel
    private lateinit var adapter: MainAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_shows_list, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mProgressDialog = ProgressDialog(requireContext())


        layoutManager = GridLayoutManager(requireContext(),2)

        myRecyclerViewShows.layoutManager = layoutManager

        //myRecyclerViewShows.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter()
        myRecyclerViewShows.adapter = adapter


        viewModel = ViewModelProviders.of(this).get(ShowsViewModel::class.java)
        viewModel.getData()


        viewModel.liveData.observe(this, Observer {
            val fragmentManager2 = fragmentManager
            if(it != null) {
                if (it.isSuccessful) {
                    adapter.setData2(it.apiShowList!!, fragmentManager2!!)
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

        changeMyLayout.setOnClickListener{
            switchlayout()
            switchicon()
        }
        changeMyLayoutGrid.setOnClickListener {
            switchlayout()
            switchicon()
        }

        viewModelPrefs = ViewModelProviders.of(this).get(SharedPrefViewModel::class.java)

        buttonLogOut.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Are you sure you want to log out?")
            dialog.setNegativeButton("YES") { dialog, which ->
                val intent = Intent(requireContext(),LoginActivity::class.java)
                viewModelPrefs.setBoolean()
                startActivity(intent)


            }
            dialog.setPositiveButton("CANCEL") { dialog, which ->
                dialog.dismiss()
            }
            dialog.show()
        }


    }

    fun switchlayout(){
        if(layoutManager?.spanCount == 1){
            layoutManager!!.spanCount = 2
        }
        else{
            layoutManager?.spanCount = 1
        }
        adapter.notifyItemRangeChanged(0,adapter.itemCount)
    }

    fun switchicon(){
        if(layoutManager?.spanCount == 1){
            changeMyLayout.visibility = View.GONE
            changeMyLayoutGrid.visibility = View.VISIBLE
        }
        else{
            changeMyLayout.visibility = View.VISIBLE
            changeMyLayoutGrid.visibility = View.GONE
        }
    }


}