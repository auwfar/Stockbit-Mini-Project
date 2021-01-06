package com.auwfar.stockbitminiproject.modules

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.auwfar.stockbitminiproject.local.dao.UsersDao
import com.auwfar.stockbitminiproject.local.entities.UserEntity
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

@Database(version = 1, entities = [UserEntity::class])
abstract class StockbitMiniDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
}

val databaseModule = module {
    fun provideDatabase(application: Application): StockbitMiniDatabase {
        return Room.databaseBuilder(application, StockbitMiniDatabase::class.java, "users")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUsersDao(database: StockbitMiniDatabase): UsersDao {
        return database.usersDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUsersDao(get()) }
}