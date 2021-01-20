package com.example.shows_tomislavlovrencic.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shows_tomislavlovrencic.Adapters.CommentAdapter
import com.example.shows_tomislavlovrencic.Fragments.episodeId
import com.example.shows_tomislavlovrencic.Models.GetCommentsViewModel
import com.example.shows_tomislavlovrencic.Models.PostCommentViewModel
import com.example.shows_tomislavlovrencic.Models.SharedPrefViewModel
import com.example.shows_tomislavlovrencic.R
import com.example.shows_tomislavlovrencic.classes.Comment
import kotlinx.android.synthetic.main.activity_comment.*


var bool: Boolean = false
var list: List<Comment>? = null
var commentLocal: Comment? = null

var mProgressDialog: ProgressDialog? = null


class CommentActivity : AppCompatActivity() {

    private lateinit var viewModel: GetCommentsViewModel
    private lateinit var viewModel2: PostCommentViewModel
    private lateinit var viewModel3: SharedPrefViewModel
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        mProgressDialog = ProgressDialog(this)


        sharedPrefs = getSharedPreferences("login", Context.MODE_PRIVATE)

        btnBackComments.setOnClickListener {
            finish()
        }

        var layoutManager = LinearLayoutManager(this)

        viewModel3 = ViewModelProviders.of(this).get(SharedPrefViewModel::class.java)

        var token = viewModel3.getToken()

        commentsRecyler.layoutManager = layoutManager

        adapter = CommentAdapter(arrayListOf())

        commentsRecyler.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(GetCommentsViewModel::class.java)

        viewModel.getComments(episodeId)

        getComments()

        nestedScroll.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            nestedScroll.isRefreshing = false
        })


        postBtn.setOnClickListener {
            mProgressDialog = ProgressDialog(this)
            var comment = Comment(userEmail = "", text = addComment.text.toString(), episodeId = episodeId)
            viewModel2 = ViewModelProviders.of(this).get(PostCommentViewModel::class.java)

            if (addComment.text.toString().isNotEmpty()) {
                viewModel2.postComment(token!!, comment)
                viewModel2.liveData.observe(this, Observer {
                    if (it != null) {
                        if (it.isSuccessful) {
                            addComment.text = null
                            getComments()
                        } else {
                            viewModel2.resetLiveData()
                            val dialog = AlertDialog.Builder(this@CommentActivity)
                            dialog.setTitle(it.message)
                            dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                                dialog.dismiss()
                            }
                            dialog.show()
                        }
                    }
                })
            } else {
                Toast.makeText(this, "Comment should not be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getComments(){
        viewModel.liveData.observe(this, Observer {
            if (it != null) {
                if (it.isSuccessful) {
                    list = it.apiGetComments
                    if (it.apiGetComments?.isNotEmpty()!!) {
                        bzvText.visibility = View.GONE
                        bzvText2.visibility = View.GONE
                        commentImage.visibility = View.GONE
                        adapter.setData(it.apiGetComments, episodeId)
                    }
                } else {
                    viewModel.resetLiveData()
                    val dialog = AlertDialog.Builder(this@CommentActivity)
                    dialog.setTitle(it.message)
                    dialog.setPositiveButton("TRY AGAIN") { dialog, which ->
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        })
    }


    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

}




