package com.auwfar.stockbitminiproject.repositories

import com.auwfar.stockbitminiproject.local.dao.UsersDao
import com.auwfar.stockbitminiproject.local.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val dao: UsersDao) {
    suspend fun getUser(email: String, password: String? = null): UserEntity? {
        return withContext(Dispatchers.IO) {
            password?.let {
                dao.getUser(email, it).getOrNull(0)
            } ?: run {
                dao.getUser(email).getOrNull(0)
            }
        }
    }

    suspend fun addUser(userEntity: UserEntity): Boolean {
        return withContext(Dispatchers.IO) {
            (dao.insert(userEntity) != 0L)
        }
    }
}