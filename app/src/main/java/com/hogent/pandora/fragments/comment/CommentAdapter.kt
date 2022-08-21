package com.hogent.pandora.fragments.comment

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hogent.pandora.R
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.post.PostComment
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserViewModel
import com.hogent.pandora.utils.md5
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter


class CommentAdapter : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    private lateinit var currentPost: Post
    private var userList = emptyList<User>()
    private var commentList = emptyList<PostComment>()
    private lateinit var mUserViewModel: UserViewModel

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        )

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentComment = commentList[position]
        val user = userList.find { user -> user.userId == currentComment.userParentId }
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        val hash = user!!.email.trim().lowercase().md5()
        val url = "https://www.gravatar.com/avatar/$hash?s=50"
        val imageView = holder.itemView.findViewById<ImageView>(R.id.user_avatar_post)

        Picasso.get()
            .load(url)
            .resize(25, 25)
            .centerCrop()
            .into(imageView)

        holder.itemView.findViewById<TextView>(R.id.txt_username).text = user.userName
        holder.itemView.findViewById<TextView>(R.id.txt_datecreated).text =
            currentComment.dateAdded.format(formatter)
        holder.itemView.findViewById<TextView>(R.id.txt_content).text = currentComment.content

        if (currentComment.usersFavorite != null && currentComment.usersFavorite.isNotEmpty()) {
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).text =
                currentComment.usersFavorite.size.toString()
        } else {
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).text = "0"
        }
    }

    fun setData(
        post: Post,
        comments: List<PostComment>,
        viewModel: UserViewModel
    ) {
        this.currentPost = post
        this.commentList = comments
        this.mUserViewModel = viewModel

        notifyDataSetChanged()
    }

    private fun userLikedPost(user: User, post: Post): Boolean {
        return post.usersFavorite.contains(user.userId)
    }

    override fun getItemCount(): Int {
        return this.commentList.size
    }

    fun setUsers(map: List<User>) {
        this.userList = map
        notifyDataSetChanged()
    }
}