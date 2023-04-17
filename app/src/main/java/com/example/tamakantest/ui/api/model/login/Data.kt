package com.example.tamakantest.ui.api.model.login

data class Data(
    val access_token: String,
    val expires_at: String,
    val token_type: String,
    val user: User
)