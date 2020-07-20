package com.kotlindemo.kotlinbase.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.kotlinbase.R
import com.kotlindemo.kotlinbase.adapter.HomeAdapter
import com.kotlindemo.kotlinbase.model.Data
import com.kotlindemo.kotlinbase.model.DataItem
import com.kotlindemo.kotlinbase.model.EmpResponse
import com.kotlindemo.kotlinbase.model.Employee
import com.kotlindemo.kotlinbase.network.ApiClient
import com.kotlindemo.kotlinbase.network.Network
import com.kotlindemo.kotlinbase.room.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.qualifiedName
    private val job = Job()
    private val scopeMainThread = CoroutineScope(job + Dispatchers.Main)
    private val scopeIO = CoroutineScope(job + Dispatchers.IO)
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var create_emp: Button
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        db = AppDatabase.getInstance(requireContext())
        // db = DatabaseClient.getInstance(requireContext())!!.appDatabase
        // var db= Room.databaseBuilder(requireContext(),AppDatabase::class.java,"KotlinBase").build()
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        create_emp = view.findViewById(R.id.addEmp) as Button
        create_emp.setOnClickListener(View.OnClickListener {
            if (Network.checkNetwork(requireContext())) {
                createEmployee()
            } else Toast.makeText(getContext(), "Check network connection", Toast.LENGTH_SHORT)
                .show()
        })

        setupUI()

        if (Network.checkNetwork(requireContext())) {
            // getAllEmployees(db)
            getAllEmployeesRxJava()
        } else {
            Toast.makeText(getContext(), "Check network connection", Toast.LENGTH_SHORT).show()
            scopeIO.launch {
                val empList = db.employeeDao().all
                Log.e(TAG, "List Size: not working " + empList.size)

                scopeMainThread.launch {
                    if (empList.size > 0) {
                        shimmerFrameLayout.stopShimmerAnimation()
                        shimmerFrameLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        homeAdapter.addData(empList)
                        homeAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        return view;
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter(requireContext(), arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = homeAdapter
    }

    private fun createEmployee() {
        val emp = Data()
        emp.age = "25"
        emp.salary = "40000"
        emp.name = "Sunny"
        emp.id = 2

        val call: Call<Employee> = ApiClient.getClient.createEmployee(emp)
        call.enqueue(object : Callback<Employee> {

            override fun onResponse(call: Call<Employee>?, response: Response<Employee>?) {
                if (response != null && response.isSuccessful) {
                    if (response.body()!!.status.equals("success")) {
                        Toast.makeText(
                            context,
                            "Employee Data Created Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(context, "Failed to create Employee Data", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<Employee>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(context, "Something Went Wrong:" + t.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    // Retrofit Enqueue Request
    private fun getAllEmployees(db: AppDatabase) {
        val call: Call<EmpResponse> = ApiClient.getClient.getEmployees()
        call.enqueue(object : Callback<EmpResponse> {

            override fun onResponse(call: Call<EmpResponse>?, response: Response<EmpResponse>?) {
                shimmerFrameLayout.stopShimmerAnimation()
                shimmerFrameLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                if (response!!.body()!!.data != null && response.body()!!.data!!.size > 0) {
                    homeAdapter.addData(response.body()!!.data as List<DataItem>)
                    homeAdapter.notifyDataSetChanged()

                    scopeIO.launch {
                        if (db.employeeDao().all.isEmpty()) {
                            db.employeeDao()
                                .insertAll(empList = response.body()!!.data as List<DataItem>)
                        } else {
                            db.employeeDao().nukeTable()
                            Log.e(TAG, "Table Size : " + db.employeeDao().all.size)
                            db.employeeDao()
                                .insertAll(empList = response.body()!!.data as List<DataItem>)
                        }
                    }
                } else {
                    scopeIO.launch {
                        val empList = db.employeeDao().all
                        Log.e(TAG, "List Size: " + empList.size)
                        scopeMainThread.launch {
                            homeAdapter.addData(empList)
                            homeAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EmpResponse>?, t: Throwable?) {
                if (t != null) {
                    shimmerFrameLayout.visibility = View.GONE
                    Toast.makeText(context, "Something Went Wrong:" + t.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun getAllEmployeesRxJava() {
        ApiClient!!.getRxClient.getAllEmployees().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(data: EmpResponse) {
        val dataList = data.data as List<DataItem>
        shimmerFrameLayout.stopShimmerAnimation()
        shimmerFrameLayout.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        if (dataList.size > 0) {
            homeAdapter.addData(dataList)
            homeAdapter.notifyDataSetChanged()
            scopeIO.launch {
                if (db.employeeDao().all.isEmpty()) {
                    db.employeeDao().insertAll(empList = dataList)
                } else {
                    db.employeeDao().nukeTable()
                    Log.e(TAG, "Table Size : " + db.employeeDao().all.size)
                    db.employeeDao().insertAll(empList = dataList)
                }
            }
        } else {
            scopeIO.launch {
                val empList = db.employeeDao().all
                Log.e(TAG, "List Size: " + empList.size)
                scopeMainThread.launch {
                    homeAdapter.addData(empList)
                    homeAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun handleError(error: Throwable) {
        Log.d(TAG, error.localizedMessage)
        Toast.makeText(requireContext(), "Error ${error.localizedMessage}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
}


