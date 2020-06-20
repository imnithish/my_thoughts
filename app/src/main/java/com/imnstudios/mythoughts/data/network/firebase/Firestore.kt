package com.imnstudios.mythoughts.data.network.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirestoreInstance {

    companion object{
        var instance: FirebaseDatabase? = null
        var databaseReference: DatabaseReference?=null

        fun getDatabase(user:String): DatabaseReference?{
            if (instance==null){
                instance = FirebaseDatabase.getInstance()
                instance?.setPersistenceEnabled(true)
                databaseReference?.keepSynced(true)
            }
            return instance?.getReference(user)
        }
    }

}