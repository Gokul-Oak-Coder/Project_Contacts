package com.example.projectcontactinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectcontactinfo.ui.repository.ContactsRepository
import com.example.projectcontactinfo.ui.util.NetworkUtil

class ContactsViewModelFactory(
    private val networkUtil: NetworkUtil,
   private val repository: ContactsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            return ContactsViewModel(networkUtil, repository) as T
        }

        throw IllegalArgumentException("unknown viewmodel class")
    }
}