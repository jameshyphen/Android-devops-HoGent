package com.hogent.pandora.fragments.post.list

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
import com.hogent.pandora.data.user.User
import com.hogent.pandora.data.user.UserViewModel
import com.hogent.pandora.utils.md5
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter


class PostListAdapter : RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {
    private var postList = emptyList<Post>()
    private var userList = emptyList<User>()
    private lateinit var mUserViewModel: UserViewModel

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        )

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentPost = postList[position]
        val user = userList.find { user -> user.userId == currentPost.userCreatorId }
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        val hash = user!!.email.trim().lowercase().md5()
        val url = "https://www.gravatar.com/avatar/$hash?s=50"
        val imageView = holder.itemView.findViewById<ImageView>(R.id.user_avatar_post)

        changeLikedPost(user, currentPost, holder)

        Picasso.get()
            .load(url)
            .resize(25, 25)
            .centerCrop()
            .into(imageView)

        holder.itemView.findViewById<TextView>(R.id.txt_username).text = user.userName
        holder.itemView.findViewById<TextView>(R.id.txt_datecreated).text =
            currentPost.dateAdded.format(formatter)
        holder.itemView.findViewById<TextView>(R.id.txt_content).text = currentPost.content

        if(currentPost.usersFavorite != null && currentPost.usersFavorite.isNotEmpty()){
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).text = currentPost.usersFavorite.size.toString()
        } else {
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).text = "0"
        }

        holder.itemView.findViewById<MaterialButton>(R.id.btn_like).setOnClickListener {
            if (currentPost.usersFavorite.contains(user.userId)) {
                currentPost =
                    currentPost.copy(usersFavorite = currentPost.usersFavorite.minus(user.userId))
            } else {
                currentPost =
                    currentPost.copy(usersFavorite = currentPost.usersFavorite.plus(user.userId))
            }
            mUserViewModel.updatePost(currentPost)
            postList = postList.filter { el -> el.postId != currentPost.postId }.plus(currentPost)
            changeLikedPost(user, currentPost, holder)
        }
    }

    private fun changeLikedPost(user: User, post: Post, holder: PostListAdapter.MyViewHolder) {
        if (userLikedPost(user, post)) {
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).iconTint =
                ColorStateList.valueOf(
                    Color.rgb(255, 50, 50)
                )
        } else {
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).iconTint =
                ColorStateList.valueOf(
                    Color.rgb(255, 255, 255)
                )
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setData(posts: List<Post>, users: List<User>, viewModel: UserViewModel) {
        this.postList = posts
        this.userList = users
        this.mUserViewModel = viewModel
        notifyDataSetChanged()
    }

    private fun userLikedPost(user: User, post: Post): Boolean {
        return post.usersFavorite.contains(user.userId)
    }
}