package com.kotlindemo.kotlinbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.kotlinbase.R
import com.kotlindemo.kotlinbase.model.Notification

class NotificationAdapter(val notificationList: ArrayList<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_row_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bindItems(notificationList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return notificationList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(notification: Notification) {
            val textViewTitle = itemView.findViewById(R.id.textViewTitle) as TextView
            val textViewDescription = itemView.findViewById(R.id.textViewDescription) as TextView
            textViewTitle.text = notification.title
            textViewDescription.text = notification.description
        }
    }
}