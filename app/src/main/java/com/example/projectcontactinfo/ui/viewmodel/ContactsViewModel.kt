package com.example.projectcontactinfo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectcontactinfo.ui.models.UserContactInfoItem
import com.example.projectcontactinfo.ui.repository.ContactsRepository
import com.example.projectcontactinfo.ui.util.NetworkUtil
import com.example.projectcontactinfo.ui.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val networkUtil: NetworkUtil,
    private val repository: ContactsRepository
) : ViewModel() {

    private val _contacts = MutableLiveData<Resource<List<UserContactInfoItem>>>()
    val contacts: LiveData<Resource<List<UserContactInfoItem>>> get() = _contacts

    // Load contacts from API or Room database
    fun getContacts() {
        _contacts.postValue(Resource.Loading())

        viewModelScope.launch(Dispatchers.IO) {
            // First, try to fetch from Room (offline)
            val contactsFromDb = repository.getContactsFromDb()

            // If there are no contacts in DB, try to fetch from API
            if (contactsFromDb.isNotEmpty()) {
                _contacts.postValue(Resource.Success(contactsFromDb.map {
                    UserContactInfoItem(email= it.email, id = it.id, name = it.name, phone = it.phone, username = it.username, website = it.website) }))
            } else {
                try {
                    if(networkUtil.isNetworkAvailable()) {
                        val contactsFromApi = repository.getContactsFromApi()
                        repository.saveContactsToDb(contactsFromApi) // Save to Room for offline access
                        _contacts.postValue(Resource.Success(contactsFromApi))
                    }else{
                        _contacts.postValue(Resource.Error("There is no network"))
                    }
                } catch (e: Exception) {
                    _contacts.postValue(Resource.Error("Error fetching data from API"))
                }
            }
        }
    }
}