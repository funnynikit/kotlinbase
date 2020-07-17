package com.kotlindemo.kotlinbase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.kotlinbase.R
import com.kotlindemo.kotlinbase.model.Faq

class FaqAdapter(val faqList: ArrayList<Faq>) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.faq_row_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: FaqAdapter.ViewHolder, position: Int) {
        holder.bindItems(faqList[position])
        holder.faqLayout.setOnClickListener(View.OnClickListener {

            if (holder.faqDescription.getVisibility() == View.VISIBLE) {
                holder.faqDescription.setVisibility(View.GONE)
            } else holder.faqDescription.setVisibility(View.VISIBLE)
        })
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return faqList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var faqLayout: RelativeLayout
        lateinit var faqTital: TextView
        lateinit var faqDescription: TextView

        fun bindItems(faq: Faq) {
            faqTital = itemView.findViewById(R.id.faqTital) as TextView
            faqDescription = itemView.findViewById(R.id.faqDescraption) as TextView
            faqLayout = itemView.findViewById(R.id.faqLayout) as RelativeLayout
            faqTital.text = faq.question
            faqDescription.text = faq.answer
        }
    }
}