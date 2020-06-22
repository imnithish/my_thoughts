package com.imnstudios.mythoughts.ui.home.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.imnstudios.mythoughts.R
import com.imnstudios.mythoughts.data.db.entities.Thoughts
import com.imnstudios.mythoughts.ui.home.EditThoughtsActivity
import com.imnstudios.mythoughts.ui.home.HomeActivity
import com.imnstudios.mythoughts.ui.home.adapters.AllThoughtsAdapter
import com.imnstudios.mythoughts.ui.login.LoginActivity
import com.imnstudios.mythoughts.ui.splashScreen.SplashScreenActivity
import com.imnstudios.mythoughts.utils.hide
import com.imnstudios.mythoughts.utils.show


class AllThoughtsFragment : Fragment(), AllThoughtsAdapter.RecycleClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noThoughtsMessage: TextView
    private lateinit var getAllThoughts: LiveData<List<Thoughts>>
    lateinit var allThoughts: List<Thoughts>
    lateinit var allThoughtsAdapter: AllThoughtsAdapter

    private lateinit var userUid: String
    private lateinit var collectionReference: CollectionReference


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
        noThoughtsMessage = v.findViewById(R.id.no_thoughts_message)

        getAllThoughts = HomeActivity.viewModel.getAllThoughts()
        getAllThoughts.observe(activity!!, Observer {

            //updating recyclerview
            allThoughts = getAllThoughts.value!!
            recyclerView.layoutManager = LinearLayoutManager(activity!!)
            allThoughtsAdapter = AllThoughtsAdapter(allThoughts, this)
            recyclerView.adapter = allThoughtsAdapter
            val swipe = ItemTouchHelper(helper)
            swipe.attachToRecyclerView(recyclerView)
            if (allThoughts.isEmpty()) {
                noThoughtsMessage.show()
            } else {
                noThoughtsMessage.hide()
            }
        })


        //getting data from firestore
        populateData()

        return v
    }

    //swipe right to delete a thought
    private val helper by lazy {
        object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val thoughts = allThoughts[position]
                val id = thoughts.id


                val dialog = Dialog(activity!!)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.logout_dialog)
                dialog.setCancelable(true)
                dialog.show()
                val deleteConfirm = dialog.findViewById<Button>(R.id.log_out_confirm_btn)
                val cancel = dialog.findViewById<Button>(R.id.cancel_btn)
                val message = dialog.findViewById<TextView>(R.id.log_out_prompt)
                message.text = getString(R.string.delete_message)
                deleteConfirm.text = getString(R.string.delete)
                deleteConfirm.setOnClickListener {
                    HomeActivity.viewModel.delete(allThoughts[position])
                    collectionReference.document("$id").delete().addOnSuccessListener {
                        Log.d(TAG, " delete success")
                    }.addOnFailureListener {
                        Log.d(TAG, " delete failure")
                    }
                    dialog.dismiss()
                }
                cancel.setOnClickListener {
                    dialog.dismiss()
                    allThoughtsAdapter.notifyItemChanged(viewHolder.adapterPosition)
                }

            }
        }
    }


    private fun populateData() {
        collectionReference.orderBy("id", Query.Direction.DESCENDING).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.data["id"].toString()
                        val thought = document.data["thought"].toString()
                        val thoughtDescription =
                            document.data["thoughtDescription"].toString()
                        val color = document.data["color"].toString()
                        val thoughts =
                            Thoughts(id.toInt(), thought, thoughtDescription, color)
                        HomeActivity.viewModel.insert(thoughts)
                    }
                }
            }

    }


    //click listeners for items
    override fun onItemClick(position: Int) {
        val thoughts = allThoughtsAdapter.getThoughtAt(position)

        val id = thoughts.id
        val thought = thoughts.thought
        val thoughtDescription = thoughts.thoughtDescription
        val color = thoughts.color


        Intent(activity!!, EditThoughtsActivity::class.java).also {
            it.putExtra("id", id)
            it.putExtra("thought", thought)
            it.putExtra("thoughtDescription", thoughtDescription)
            it.putExtra("color", color)
            startActivity(it)
            activity!!.overridePendingTransition(
                R.anim.activity_fade_in_animation,
                R.anim.activity_fade_out_animation
            )
        }
    }

}