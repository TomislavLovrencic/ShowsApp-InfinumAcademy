package com.example.shows_tomislavlovrencic.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shows_tomislavlovrencic.classes.Comment
import kotlinx.android.synthetic.main.layout_listitemcomment.view.*


var episodeNameId: String = ""

class CommentAdapter(private var comments: MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow =
            layoutInflater.inflate(com.example.shows_tomislavlovrencic.R.layout.layout_listitemcomment, parent, false)
        return ViewHolder(cellForRow)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])

    }


    fun setData(list: List<Comment>, episodeId: String) {
        episodeNameId = episodeId
        this.comments = list as MutableList<Comment>
        notifyDataSetChanged()
    }

    fun addComment(comment: Comment) {
        this.comments.add(comment)
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(comment: Comment) {
            with(itemView) {
                textViewUserEmail.text = comment.userEmail
                textViewUserComment.text = comment.text

            }


        }
    }


}