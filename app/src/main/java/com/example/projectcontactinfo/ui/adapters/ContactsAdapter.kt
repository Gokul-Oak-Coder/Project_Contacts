package com.example.projectcontactinfo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontactinfo.R
import com.example.projectcontactinfo.ui.models.UserContactInfoItem

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    inner class ContactsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

        val name : TextView = itemView.findViewById(R.id.nameTextView)
        val phoneNumber : TextView = itemView.findViewById(R.id.phoneTextView)
        val emailAddress : TextView = itemView.findViewById(R.id.emailTextView)
        val website: TextView = itemView.findViewById(R.id.website)

    }

    private val differUtil = object : DiffUtil.ItemCallback<UserContactInfoItem>(){
        override fun areItemsTheSame(oldItem: UserContactInfoItem, newItem: UserContactInfoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserContactInfoItem, newItem: UserContactInfoItem): Boolean {
            return oldItem == newItem
        }
    }

     val differ = AsyncListDiffer(this, differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {

        return ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_list, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contacts = differ.currentList[position]

        holder.apply {
            name.text = "Name: ${contacts.name.toString()}"
            phoneNumber.text = "Phone Number: ${contacts.phone.toString()}"
            emailAddress.text = "Email:  ${contacts.email.toString()}"
            website.text = "Website:  ${contacts.website.toString()}"
        }
    }


}