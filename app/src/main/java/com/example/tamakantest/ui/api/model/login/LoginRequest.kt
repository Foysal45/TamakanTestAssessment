package com.example.tamakantest.ui.api.model.login


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    var email: String = "",
    var password: String = ""
)