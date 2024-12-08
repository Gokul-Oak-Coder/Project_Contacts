package com.example.projectcontactinfo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontactinfo.databinding.ActivityMainBinding
import com.example.projectcontactinfo.ui.adapters.ContactsAdapter
import com.example.projectcontactinfo.ui.db.ContactsDao
import com.example.projectcontactinfo.ui.db.ContactsDatabase
import com.example.projectcontactinfo.ui.repository.ContactsRepository
import com.example.projectcontactinfo.ui.util.NetworkUtil
import com.example.projectcontactinfo.ui.util.Resource
import com.example.projectcontactinfo.ui.viewmodel.ContactsViewModel
import com.example.projectcontactinfo.ui.viewmodel.ContactsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsViewModel: ContactsViewModel
    private var networkUtil= NetworkUtil(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        getContactsObserverCall()
        setUpRecycler()


        binding.refreshButton.setOnClickListener {
            contactsViewModel.getContacts()
        }

        // Initial load of contacts
        contactsViewModel.getContacts()
    }

    private fun initViewModel(){
        val newsRepository = ContactsRepository(ContactsDatabase(this))
        val viewModelProviderFactory = ContactsViewModelFactory(networkUtil,newsRepository)
        contactsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(ContactsViewModel::class.java)
    }

    private fun getContactsObserverCall(){
        contactsViewModel.contacts.observe(this, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE
                    contactsAdapter.differ.submitList(response.data ?: emptyList())
                }

                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE

                    response.message?.let {
                        binding.emptyView.text = response.message
                        Toast.makeText(this, "${response.message}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        })
    }

    private fun setUpRecycler(){
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        contactsAdapter = ContactsAdapter()
        recyclerView.adapter = contactsAdapter
    }

}