package com.imnstudios.mythoughts.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thoughts_table")
class Thoughts(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val thought: String,
    val thoughtDescription: String,
    val color: String
) {
}