package com.imnstudios.mythoughts.ui.home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.imnstudios.mythoughts.data.db.entities.Thoughts
import com.imnstudios.mythoughts.data.repositories.ThoughtsRepository

class ThoughtsViewModel(app: Application) : AndroidViewModel(app) {

    private var repository: ThoughtsRepository = ThoughtsRepository(app)

    //Database operations in ViewModel
    fun insert(thoughts: Thoughts): Long? {
        return repository.insert(thoughts)
    }

    fun update(thoughts: Thoughts) {
        repository.update(thoughts)
    }

    fun delete(thoughts: Thoughts) {
        repository.delete(thoughts)
    }

    fun getAllNotes(): LiveData<List<Thoughts>> {
        return repository.getAllThoughts()
    }
}