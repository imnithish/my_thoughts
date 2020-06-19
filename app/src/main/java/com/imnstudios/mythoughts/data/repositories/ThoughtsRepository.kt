package com.imnstudios.mythoughts.data.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.imnstudios.mythoughts.data.db.ThoughtsDao
import com.imnstudios.mythoughts.data.db.ThoughtsDatabase
import com.imnstudios.mythoughts.data.db.entities.Thoughts

class ThoughtsRepository(app: Application) {

    var thoughtsDao: ThoughtsDao? = ThoughtsDatabase.getDatabase(app)?.thoughtsDao()

    fun insert(thoughts: Thoughts): Long? {
        return InsertAsync(thoughtsDao).execute(thoughts).get()
    }

    fun update(thoughts: Thoughts) {
        UpdateAsync(thoughtsDao).execute(thoughts)
    }

    fun delete(thoughts: Thoughts) {
        DeleteAsync(thoughtsDao).execute(thoughts)
    }

    fun getAllThoughts(): LiveData<List<Thoughts>> {
        return GetAllThoughtsAsync(thoughtsDao).execute().get()
    }


    //background operations
    class InsertAsync(var thoughtsDao: ThoughtsDao?) : AsyncTask<Thoughts, Void, Long?>() {
        override fun doInBackground(vararg params: Thoughts): Long? {
            return thoughtsDao?.insert(params[0])
        }
    }

    class UpdateAsync(var thoughtsDao: ThoughtsDao?) : AsyncTask<Thoughts, Void, Unit>() {
        override fun doInBackground(vararg params: Thoughts) {
            thoughtsDao?.update(params[0])
        }
    }

    class DeleteAsync(var thoughtsDao: ThoughtsDao?) : AsyncTask<Thoughts, Void, Unit>() {
        override fun doInBackground(vararg params: Thoughts) {
            thoughtsDao?.delete(params[0])
        }
    }

    class GetAllThoughtsAsync(var thoughtsDao: ThoughtsDao?) :
        AsyncTask<Unit, Void, LiveData<List<Thoughts>>>() {
        override fun doInBackground(vararg params: Unit?): LiveData<List<Thoughts>>? {
            return thoughtsDao?.getAllThoughts()
        }
    }


}