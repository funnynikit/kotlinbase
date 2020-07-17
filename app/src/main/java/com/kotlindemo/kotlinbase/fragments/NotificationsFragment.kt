package com.kotlindemo.kotlinbase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.kotlinbase.R
import com.kotlindemo.kotlinbase.adapter.NotificationAdapter
import com.kotlindemo.kotlinbase.model.Notification

class NotificationsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_notification, container, false)
        val recyclerView = view.findViewById(R.id.notification_recyclerView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val notificationList = ArrayList<Notification>()

        //adding some dummy data to the list
        notificationList.add(
            Notification(
                "Nikit Kumar",
                "Nikit Kumar is professional App Developer"
            )
        )
        notificationList.add(
            Notification(
                "Sumit Kumar",
                "Sumit Kumar is professional App Developer"
            )
        )
        notificationList.add(
            Notification(
                "Ankit Kumar",
                "Ankit Kumar is professional App Developer"
            )
        )
        notificationList.add(
            Notification(
                "Rahul Kumar",
                "Rahul Kumar is professional App Developer"
            )
        )

        //creating our adapter
        val adapter = NotificationAdapter(notificationList)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
        return view
    }

}