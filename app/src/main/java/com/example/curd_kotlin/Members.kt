package com.example.curd_kotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo
    val email: String,
    @ColumnInfo
    val password: String
)