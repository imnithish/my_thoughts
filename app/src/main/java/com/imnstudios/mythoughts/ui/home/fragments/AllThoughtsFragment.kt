package com.imnstudios.mythoughts.ui.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.data.db.entities.Thoughts
import com.imnstudios.mythoughts.ui.home.HomeActivity
import com.imnstudios.mythoughts.ui.home.adapters.AllThoughtsAdapter
import com.imnstudios.mythoughts.ui.splashScreen.SplashScreenActivity


class AllThoughtsFragment : Fragment(), AllThoughtsAdapter.RecycleClick {

    private lateinit var recyclerView: RecyclerView
    lateinit var getAllThoughts: LiveData<List<Thoughts>>
    lateinit var allThoughts: List<Thoughts>
    lateinit var allThoughtsAdapter: AllThoughtsAdapter

    private lateinit var userUid: String
    private lateinit var collectionReference: CollectionReference
    private lateinit var thoughtsList: List<Thoughts>


    private val TAG = "Debug014589"
//    private val TAG = "AllThoughtsFragmentDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, " onCreate AllThoughtsFragment")
        userUid = SplashScreenActivity.auth.currentUser?.uid.toString()
        collectionReference = HomeActivity.firestoreDb.collection(userUid)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, " onCreateView AllThoughtsFragment")
        val v = inflater.inflate(R.layout.fragment_all_thoughts, container, false)

        recyclerView = v.findViewById(R.id.recycler_view)

        getAllThoughts = HomeActivity.viewModel.getAllThoughts()
        getAllThoughts.observe(activity!!, Observer {

            //updating recyclerview
            allThoughts = getAllThoughts.value!!
            recyclerView.layoutManager = LinearLayoutManager(activity!!)
            allThoughtsAdapter = AllThoughtsAdapter(allThoughts, this)
            recyclerView.adapter = allThoughtsAdapter
        })

        //getting data from firestore
        populateData()

        return v
    }

    private fun populateData() {
        collectionReference.orderBy("id", Query.Direction.DESCENDING).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.data["id"].toString()
                        val thought = document.data["thought"].toString()
                        val thoughtDescription = document.data["thoughtDescription"].toString()
                        val color = document.data["color"].toString()
                        val thoughts = Thoughts(id.toInt(), thought, thoughtDescription, color)
                        HomeActivity.viewModel.insert(thoughts)
                    }
                }
            }

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "hella", Toast.LENGTH_SHORT).show()
    }


}