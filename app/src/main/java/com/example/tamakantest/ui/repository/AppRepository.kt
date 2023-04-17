package com.example.tamakantest.ui.repository

import com.example.tamakantest.ui.api.endpoint.ApiInterface
import com.example.tamakantest.ui.api.model.add_meeting.MeetingAddRequestBody
import com.example.tamakantest.ui.api.model.login.LoginRequest
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateRequestBody

class AppRepository(private val apiInterface: ApiInterface) {


   suspend fun getMeetingDataList(status: String) = apiInterface.getMeetingDataList(status)

   suspend fun updateMeeting(meetingId: Int, requestBody: MeetingUpdateRequestBody) = apiInterface.updateMeeting(meetingId, requestBody)

   suspend fun employeeLogin(requestBody: LoginRequest) = apiInterface.userLogin(requestBody)

   suspend fun addMeeting(requestBody: MeetingAddRequestBody) = apiInterface.addMeeting(requestBody)

   suspend fun  deleteMeeting(id: Int) = apiInterface.deleteMeeting(id)


}