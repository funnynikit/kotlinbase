package com.kotlindemo.kotlinbase.model

data class Contact(
    val id: String?,
    val user_id: String,
    val contact_name: String,
    val contact_number: String
) {
    constructor() : this("", "", "", "")
}