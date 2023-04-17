package com.example.tamakantest.ui.api.model.login

data class User(
    val created_at: String,
    val department: String,
    val email: String,
    val id: Int,
    val name: String,
    val type: String,
    val updated_at: String
)