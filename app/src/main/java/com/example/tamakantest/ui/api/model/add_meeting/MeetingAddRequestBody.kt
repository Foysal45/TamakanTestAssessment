package com.example.tamakantest.ui.api.model.add_meeting

data class MeetingAddRequestBody(
    val client_name: String,
    val company_name: String,
    val date: String,
    val start_time: String,
    val end_time: String,
    val description: String,
    val room_id: String,
    val user_id: String
)