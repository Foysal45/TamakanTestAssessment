package com.example.tamakantest.ui.api.model.meeting_data_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val client_name: String,
    val company_name: String,
    val created_at: String,
    val date: String,
    val description: String,
    val end_time: String,
    val id: Int,
    val room_id: String,
    val start_time: String,
    val updated_at: String,
    val user_id: String
): Parcelable