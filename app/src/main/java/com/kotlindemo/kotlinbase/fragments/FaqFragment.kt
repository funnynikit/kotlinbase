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
import com.kotlindemo.kotlinbase.adapter.FaqAdapter
import com.kotlindemo.kotlinbase.model.Faq

class FaqFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_faq, container, false)
        val recyclerView = view.findViewById(R.id.faq_recyclerView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val faqList = ArrayList<Faq>()

        //adding some dummy data to the list
        faqList.add(Faq("Question 1", "Answer 1"))
        faqList.add(Faq("Question 2", "Answer 2"))
        faqList.add(Faq("Question 3", "Answer 3"))
        faqList.add(Faq("Question 4", "Answer 4"))

        //creating our adapter
        val adapter = FaqAdapter(faqList)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
        return view;
    }

}