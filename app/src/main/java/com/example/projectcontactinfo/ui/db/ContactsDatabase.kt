package com.example.projectcontactinfo.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectcontactinfo.ui.models.UserContactInfoItem

@Database(entities = [UserContactInfoItem::class], version = 2)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {

        @Volatile
        private var INSTANCE: ContactsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: getDatabase(context).also { INSTANCE = it }
        }

        fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ContactsDatabase::class.java,
            "contacts_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}