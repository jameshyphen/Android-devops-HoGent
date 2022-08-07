package com.hogent.pandora.fragments.post.list

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hogent.pandora.R
import com.hogent.pandora.data.post.Post
import com.hogent.pandora.data.user.User
import com.hogent.pandora.utils.md5
import com.squareup.picasso.Picasso
import java.net.URL
import java.time.format.DateTimeFormatter


class PostListAdapter : RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {
    private var postList = emptyList<Post>()
    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPost = postList[position]
        val user = userList.find { user -> user.userId == currentPost.userCreatorId }
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        val hash = user!!.email.trim().lowercase().md5()
        val url = "https://www.gravatar.com/avatar/$hash?s=50"
        val imageView = holder.itemView.findViewById<ImageView>(R.id.user_avatar_post)

//        val imgUrl = URL(url)
//        val mIcon = BitmapFactory.decodeStream(imgUrl.openConnection().getInputStream())
//        imageView.setImageBitmap(mIcon)

        if(userLikedPost(user, currentPost)){
            holder.itemView.findViewById<MaterialButton>(R.id.btn_like).iconTint = ColorStateList.valueOf(
                Color.rgb(255, 50, 50))
        }

        Picasso.get()
            .load(url)
            .resize(25, 25)
            .centerCrop()
            .into(imageView)

        holder.itemView.findViewById<TextView>(R.id.txt_username).text = user.userName
        holder.itemView.findViewById<TextView>(R.id.txt_datecreated).text =
            currentPost.dateAdded.format(formatter)
        holder.itemView.findViewById<TextView>(R.id.txt_content).text = currentPost.content
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setData(posts: List<Post>, users: List<User>) {
        this.postList = posts
        this.userList = users
        notifyDataSetChanged()
    }

    private fun userLikedPost(user: User, post: Post): Boolean{
        return post.usersFavorite.contains(user.userId)
    }
}