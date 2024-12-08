package com.example.projectcontactinfo.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.projectcontactinfo.ui.models.UserContactInfoItem

@Dao
interface ContactsDao {

    @Insert
    suspend fun insertContacts(contacts: List<UserContactInfoItem>)

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<UserContactInfoItem>
}