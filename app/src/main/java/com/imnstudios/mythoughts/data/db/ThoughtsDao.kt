package com.imnstudios.mythoughts.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.imnstudios.mythoughts.data.db.entities.Thoughts

@Dao
interface ThoughtsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(thoughts: Thoughts): Long

    @Update
    fun update(thoughts: Thoughts)

    @Delete
    fun delete(thoughts: Thoughts)

    @Query("select * from thoughts_table ORDER BY id DESC")
    fun getAllThoughts(): LiveData<List<Thoughts>>


}