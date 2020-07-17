package com.kotlindemo.kotlinbase.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.kotlinbase.R
import com.kotlindemo.kotlinbase.model.Data
import com.kotlindemo.kotlinbase.model.DataItem
import com.kotlindemo.kotlinbase.model.EmpDelete
import com.kotlindemo.kotlinbase.model.Employee
import com.kotlindemo.kotlinbase.network.ApiClient
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.shimmer_placeholder_layout.view.textViewUserEmail
import kotlinx.android.synthetic.main.shimmer_placeholder_layout.view.textViewUserName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAdapter(
    private val ctx: Context,
    private val employeeList: ArrayList<DataItem>
) : RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(emp: DataItem) {
            itemView.textViewUserName.text = emp.employeeName
            itemView.textViewUserEmail.text = emp.employeeSalary
//            Glide.with(itemView.imageViewAvatar.context)
//                .load(emp.profileImage)
//                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = employeeList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.updateBtn.setOnClickListener(View.OnClickListener {

            updateEmployee(employeeList[position].id)

        })
        holder.itemView.deleteBtn.setOnClickListener(View.OnClickListener {
            Log.e("check", "Deleted id :" + employeeList[position].employeeName)
            deleteEmployee(employeeList[position].id)
        })
        holder.bind(employeeList[position])
    }


    fun addData(empList: List<DataItem>) {
        employeeList.addAll(empList)
    }

    private fun updateEmployee(id: String?) {

        val emp = Data()
        emp.name = "Ankit"
        emp.age = "30"
        emp.salary = "50000"

        val call: Call<Employee> = ApiClient.getClient.updateRecord(emp,id)
        call.enqueue(object : Callback<Employee> {

            override fun onResponse(call: Call<Employee>?, response: Response<Employee>?) {

                if (response!!.isSuccessful) {
                    if (response.body()!!.status.equals("success")) {
                        Toast.makeText(ctx, "Employee Data Updated Successfully", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(ctx, "Failed to Update Employee Data", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(ctx, "Something Went Wrong:" + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun deleteEmployee(id: String?) {
        val call: Call<EmpDelete> = ApiClient.getClient.deleteEmployee(id)
        call.enqueue(object : Callback<EmpDelete> {

            override fun onResponse(call: Call<EmpDelete>?, response: Response<EmpDelete>?) {

                if (response!!.isSuccessful) {
                    if (response.body()!!.status.equals("success")) {
                        Toast.makeText(ctx, "Employee Data Deleted Successfully", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(ctx, "Failed to Delete Employee Data", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<EmpDelete>, t: Throwable) {
                Toast.makeText(ctx, "Something Went Wrong:" + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

}