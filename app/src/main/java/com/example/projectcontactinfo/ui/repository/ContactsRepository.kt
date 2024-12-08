package com.example.projectcontactinfo.ui.repository

import com.example.projectcontactinfo.ui.db.ContactsDao
import com.example.projectcontactinfo.ui.db.ContactsDatabase
import com.example.projectcontactinfo.ui.models.UserContactInfoItem
import com.example.projectcontactinfo.ui.network.RetrofitInstance

class ContactsRepository(
    private val contactsDb: ContactsDatabase
) {

    // Fetch data from API
    suspend fun getContactsFromApi(): List<UserContactInfoItem> {
        return RetrofitInstance.api.getContacts()
    }

    // Fetch data from Room Database (offline)
    suspend fun getContactsFromDb(): List<UserContactInfoItem> {
        return contactsDb.contactsDao().getAllContacts()
    }

    // Save data to Room Database (offline)
    suspend fun saveContactsToDb(contacts: List<UserContactInfoItem>) {
        val contactEntities = contacts.map {
            UserContactInfoItem(email= it.email, id = it.id, name = it.name, phone = it.phone, username = it.username, website = it.website)
        }
        contactsDb.contactsDao().insertContacts(contactEntities)
    }
}