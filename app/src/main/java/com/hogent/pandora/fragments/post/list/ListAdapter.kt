package com.hogent.pandora.fragments.post.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hogent.pandora.R
import com.hogent.pandora.data.user.User
import java.time.LocalDate
import java.time.Period

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.findViewById<TextView>(R.id.txt_id).text = currentItem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.txt_username).text = currentItem.userName
        holder.itemView.findViewById<TextView>(R.id.txt_age).text = Period.between(currentItem.birthdate, LocalDate.now()).years.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users: List<User>){
        this.userList = users
        notifyDataSetChanged()
    }
}