package com.example.tamakantest.ui.home_meeting.view_model

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamakantest.ui.api.model.add_meeting.MeetingAddRequestBody
import com.example.tamakantest.ui.api.model.add_meeting.MeetingAddResponse
import com.example.tamakantest.ui.api.model.delete_meeting.MeetingDeleteResponse
import com.example.tamakantest.ui.api.model.meeting_data_list.MeetingDataBaseModel
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateRequestBody
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateResponse
import com.example.tamakantest.ui.repository.AppRepository
import com.example.tamakantest.ui.utils.ViewState
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MeetingCommonViewModel(private val repository: AppRepository) : ViewModel() {
    /*ViewModel Architecture component calls the repository layer on the main thread to trigger the network request.
      This guide iterates through various solutions that use coroutines keep the main thread unblocked.*/

    val viewState = MutableLiveData<ViewState>(ViewState.NONE)

    //function for get the MeetingList
    fun getMeetingDataList(status: String): LiveData<List<com.example.tamakantest.ui.api.model.meeting_data_list.Data>> {

        val responseData: MutableLiveData<List<com.example.tamakantest.ui.api.model.meeting_data_list.Data>> = MutableLiveData()
        viewState.value = ViewState.ProgressState(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMeetingDataList(status)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        responseData.value = response.body.data
                        Timber.d("response:${response.body}")
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                        Timber.d(response.error)
                    }
                }
            }
        }
        return responseData
    }

    //function for Update the Meeting Data
    fun updateMeeting(meetingId: Int, requestBody: MeetingUpdateRequestBody): LiveData<MeetingUpdateResponse> {

        viewState.value = ViewState.ProgressState(true)
        val responseData = MutableLiveData<MeetingUpdateResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateMeeting(meetingId, requestBody)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        if (response.body.data) {
                            responseData.value = response.body
                        }
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                        Timber.d(response.error)
                    }
                }
            }
        }
        return responseData
    }


    //function for add new Meeting Data
    fun addMeeting(requestBody: MeetingAddRequestBody): LiveData<MeetingAddResponse> {

        viewState.value = ViewState.ProgressState(true)
        val responseData = MutableLiveData<MeetingAddResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.addMeeting(requestBody)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        if (response.body !=null) {
                            responseData.value = response.body
                            Timber.d("meetingSubmitResponse:${response.body}")
                        }
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                        Timber.d(response.error)
                    }
                }
            }
        }
        return responseData
    }

    //function for delete Meeting
    fun deleteMeeting(requestBody: com.example.tamakantest.ui.api.model.meeting_data_list.Data): LiveData<MeetingDeleteResponse> {

        viewState.value = ViewState.ProgressState(true)
        val responseData = MutableLiveData<MeetingDeleteResponse>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.deleteMeeting(requestBody.id)
            withContext(Dispatchers.Main) {
                viewState.value = ViewState.ProgressState(false)
                when (response) {
                    is NetworkResponse.Success -> {
                        if (response.body != null) {
                            responseData.value = response.body
                        }
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                        viewState.value = ViewState.ShowMessage(message)
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                        viewState.value = ViewState.ShowMessage(message)
                        Timber.d(response.error)
                    }
                }
            }
        }
        return responseData
    }

}