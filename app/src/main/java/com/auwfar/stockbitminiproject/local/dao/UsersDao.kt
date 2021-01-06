package com.auwfar.stockbitminiproject.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.auwfar.stockbitminiproject.local.entities.UserEntity

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = (:email) AND password = (:password)")
    suspend fun getUser(email: String, password: String): List<UserEntity>

    @Query("SELECT * FROM users WHERE email = (:email)")
    suspend fun getUser(email: String): List<UserEntity>
}