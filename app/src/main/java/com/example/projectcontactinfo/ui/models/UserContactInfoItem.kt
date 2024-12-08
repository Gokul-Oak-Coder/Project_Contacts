package com.example.projectcontactinfo.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class UserContactInfoItem(
    val email: String? = null,
    @PrimaryKey val id: Int? = null,
    val name: String? = null,
    val phone: String? = null,
    val username: String? = null,
    val website: String? = null
)