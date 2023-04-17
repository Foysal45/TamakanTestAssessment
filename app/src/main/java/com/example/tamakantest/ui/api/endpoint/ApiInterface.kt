package com.example.tamakantest.ui.api.endpoint


import com.example.tamakantest.ui.api.model.ErrorResponse
import com.example.tamakantest.ui.api.model.add_meeting.MeetingAddRequestBody
import com.example.tamakantest.ui.api.model.add_meeting.MeetingAddResponse
import com.example.tamakantest.ui.api.model.delete_meeting.MeetingDeleteResponse
import com.example.tamakantest.ui.api.model.login.LoginRequest
import com.example.tamakantest.ui.api.model.login.LoginResponse
import com.example.tamakantest.ui.api.model.meeting_data_list.MeetingDataBaseModel
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateRequestBody
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.Retrofit
import retrofit2.http.*

interface ApiInterface {

    companion object {
        operator fun invoke(retrofit: Retrofit): ApiInterface {
            return retrofit.create(ApiInterface::class.java)
        }
    }


    @GET("meetings")
    suspend fun getMeetingDataList(@Query("status") status: String): NetworkResponse<MeetingDataBaseModel, ErrorResponse>

    @PUT("update-meeting/{meetingId}")
    suspend fun updateMeeting(@Path("meetingId") meetingId: Int, @Body requestBody: MeetingUpdateRequestBody): NetworkResponse<MeetingUpdateResponse, ErrorResponse>

    @POST("login")
    suspend fun userLogin(@Body requestBody: LoginRequest): NetworkResponse<LoginResponse, ErrorResponse>

    @POST("store-meetings")
    suspend fun addMeeting(@Body requestBody: MeetingAddRequestBody): NetworkResponse<MeetingAddResponse, ErrorResponse>

    @DELETE("delete-meeting/{id}")
    suspend fun deleteMeeting(@Path("id") id: Int): NetworkResponse<MeetingDeleteResponse, ErrorResponse>


}