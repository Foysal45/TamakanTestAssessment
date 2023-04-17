package com.example.tamakantest.ui.home_meeting.update_delete_meeting

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.tamakantest.R
import com.example.tamakantest.databinding.FragmentMeetingDataListBinding
import com.example.tamakantest.databinding.FragmentUpdateOrDeleteMeetingBinding
import com.example.tamakantest.ui.api.model.meeting_data_list.Data
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateRequestBody
import com.example.tamakantest.ui.home_meeting.view_model.MeetingCommonViewModel
import com.example.tamakantest.ui.utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import timber.log.Timber

class UpdateOrDeleteMeetingFragment : Fragment() {
    private var binding: FragmentUpdateOrDeleteMeetingBinding? = null
    private val viewModel: MeetingCommonViewModel by inject()
    private var model: Data? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentUpdateOrDeleteMeetingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        meetingDataInputValidation()
        receivedDataModel()
        meetingDataLoad()
        model?.let { deleteMeeting(it) }
        //initially cursor Focus & visible at Name Input Field
        binding?.userIdInputUpdateDelete?.let { requestFocus(it) }
        binding?.userIdInputUpdateDelete?.isCursorVisible = true



    }


    //here initially load data from the Data model that received from MeetingDataListFragment
    private fun meetingDataLoad(){
        model?.user_id?.let { userId ->
            binding?.userIdInputUpdateDelete?.setText(userId)
        }

        model?.room_id?.let { roomId ->
            binding?.roomIdInputUpdateDelete?.setText(roomId)
        }

        model?.client_name?.let { clientName ->
            binding?.clientNameInputUpdateDelete?.setText(clientName)
        }

        model?.company_name?.let { companyName ->
            binding?.companyNameInputUpdateDelete?.setText(companyName)
        }

        model?.date?.let { date ->
            binding?.dateInputUpdateDelete?.setText(date)
        }

        model?.start_time?.let { startTime ->
            binding?.startTimeInputUpdateDelete?.setText(startTime)
        }

        model?.end_time?.let { endTime ->
            binding?.endTimeInputUpdateDelete?.setText(endTime)
        }

        model?.created_at?.let { createdAt ->
            binding?.createdAtInput?.setText(createdAt)
        }

        model?.updated_at?.let { updateddAt ->
            binding?.updatedAtInput?.setText(updateddAt)
        }

        model?.description?.let { description ->
            binding?.descriptionInputUpdateDelete?.setText(description)
        }

    }

    //function for meetingDataInputValidation
    private fun meetingDataInputValidation() {
        addTextChangedListener()
        binding?.updateBtn?.setOnClickListener {
            Timber.d("Validation check 1  ${validateUserId()}")
            if (validateUserId()) {
                Timber.d("Validation check 2  ${validateRoomId()}")
                if (validateRoomId()) {
                    Timber.d("Validation check 3  ${validateClientName()}")
                    if (validateClientName()) {
                        Timber.d("Validation check 4  ${validateCompanyName()}")
                        if (validateCompanyName()) {
                            Timber.d("Validation check 5  ${validateDate()}")
                            if (validateDate()){
                                Timber.d("Validation check 6  ${validateStartTime()}")
                                if (validateStartTime()){
                                    Timber.d("Validation check 7  ${validateEndTime()}")
                                    if (validateEndTime()){
                                        Timber.d("Validation check 8  ${validateCreatedAt()}")
                                        if (validateCreatedAt()){
                                            Timber.d("Validation check 9  ${validateUpdatedAt()}")
                                            if (validateUpdatedAt()){
                                                Timber.d("Validation check 10 ${validateDescription()}")
                                                if (validateDescription()){
                                                    addTextChangedListener()
                                                    model?.id?.let { meetingId -> updateMeetingData(meetingId) }
                                                }
                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }
                }

            }

        }
    }

    //function for update meeting Data
    private fun updateMeetingData(meetingId: Int){

        val requestBody = MeetingUpdateRequestBody(
            binding?.clientNameInputUpdateDelete?.text?.trim().toString(),
            binding?.companyNameInputUpdateDelete?.text?.trim().toString(),
            binding?.createdAtInput?.text?.trim().toString(),
            binding?.dateInputUpdateDelete?.text?.trim().toString(),
            binding?.descriptionInputUpdateDelete?.text?.trim().toString(),
            binding?.endTimeInputUpdateDelete?.text?.trim().toString(),
            binding?.roomIdInputUpdateDelete?.text?.trim().toString(),
            binding?.startTimeInputUpdateDelete?.text?.trim().toString(),
            binding?.updatedAtInput?.text?.trim().toString(),
            binding?.userIdInputUpdateDelete?.text?.trim().toString()

        )
        viewModel.updateMeeting(meetingId,requestBody).observe(viewLifecycleOwner) { response->
            if (response.data){
                context?.toast(response.message)
                findNavController().navigate(R.id.action_updateOrDeleteMeetingFragment_to_meetingDataListFragment)

            }else{
                context?.toast(response.message)
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.ShowMessage -> {
                    requireContext().toast(state.message)
                }
                is ViewState.KeyboardState -> {
                    hideKeyboard()
                }
                is ViewState.ProgressState -> {
                    if (state.isShow) {
                        binding?.progressBarSubmitFeedback?.visibility = View.VISIBLE
                    } else {
                        binding?.progressBarSubmitFeedback?.visibility = View.GONE
                    }
                }
                else -> {}
            }
        }

    }

    //function for delete meetin data
    private fun deleteMeeting(model: Data){
        binding?.deleteBtn?.setOnClickListener {
            alert("Direction", "Want to delete this Meeting?", true, "Yes", "Cancel") {
                if (it == AlertDialog.BUTTON_POSITIVE) {
                    viewModel.deleteMeeting(model).observe(viewLifecycleOwner, Observer {response ->
                        if (response.message !=null) {
                            context?.toast(response.message)
                            findNavController().navigate(R.id.action_updateOrDeleteMeetingFragment_to_meetingDataListFragment)
                        }
                    })
                }
            }.show()
        }
    }

    private fun validateUserId(): Boolean {
        if (binding?.userIdInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.userIdLayoutUpdateDelete?.isErrorEnabled = true
            binding?.userIdLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.userIdInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.userIdLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateRoomId(): Boolean {
        if (binding?.roomIdInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.roomIdLayoutUpdateDelete?.isErrorEnabled = true
            binding?.roomIdLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.roomIdInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.roomIdLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateClientName(): Boolean {
        if (binding?.clientNameInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.companyNameLayoutUpdateDelete?.isErrorEnabled = true
            binding?.companyNameLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.clientNameInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.companyNameLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateCompanyName(): Boolean {
        if (binding?.companyNameInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.companyNameLayoutUpdateDelete?.isErrorEnabled = true
            binding?.companyNameLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.companyNameInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.companyNameLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateDate(): Boolean {
        if (binding?.dateInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.dateLayoutUpdateDelete?.isErrorEnabled = true
            binding?.dateLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.dateInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.dateLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateStartTime(): Boolean {
        if (binding?.startTimeInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.startTimeLayoutUpdateDelete?.isErrorEnabled = true
            binding?.startTimeLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.startTimeInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.startTimeLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateEndTime(): Boolean {
        if (binding?.endTimeInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.endTimeLayoutUpdateDelete?.isErrorEnabled = true
            binding?.endTimeLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.endTimeInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.endTimeLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }

    private fun validateCreatedAt(): Boolean {
        if (binding?.createdAtInput.getString().trim().isEmpty()) {
            binding?.createdAtLayout?.isErrorEnabled = true
            binding?.createdAtLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.createdAtInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.createdAtLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateUpdatedAt(): Boolean {
        if (binding?.updatedAtInput.getString().trim().isEmpty()) {
            binding?.updatedAtLayout?.isErrorEnabled = true
            binding?.updatedAtLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.updatedAtInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.updatedAtLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateDescription(): Boolean {
        if (binding?.descriptionInputUpdateDelete.getString().trim().isEmpty()) {
            binding?.descriptionLayoutUpdateDelete?.isErrorEnabled = true
            binding?.descriptionLayoutUpdateDelete?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.descriptionInputUpdateDelete?.let { requestFocus(it) }
            return false
        } else {
            binding?.descriptionLayoutUpdateDelete?.isErrorEnabled = false
            return true
        }

    }



    private fun addTextChangedListener() {
        binding?.userIdInputUpdateDelete?.let { addTextChangedListener(it, binding?.userIdLayoutUpdateDelete!!) }
        binding?.userIdInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.userIdInputUpdateDelete!!))

        binding?.roomIdInputUpdateDelete?.let { addTextChangedListener(it, binding?.roomIdLayoutUpdateDelete!!) }
        binding?.roomIdInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.roomIdInputUpdateDelete!!))

        binding?.clientNameInputUpdateDelete?.let { addTextChangedListener(it, binding?.clientNameLayoutUpdateDelete!!) }
        binding?.clientNameInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.clientNameInputUpdateDelete!!))

        binding?.companyNameInputUpdateDelete?.let { addTextChangedListener(it, binding?.companyNameLayoutUpdateDelete!!) }
        binding?.companyNameInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.companyNameInputUpdateDelete!!))

        binding?.dateInputUpdateDelete?.let { addTextChangedListener(it, binding?.dateLayoutUpdateDelete!!) }
        binding?.dateInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.dateInputUpdateDelete!!))

        binding?.startTimeInputUpdateDelete?.let { addTextChangedListener(it, binding?.startTimeLayoutUpdateDelete!!) }
        binding?.startTimeInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.startTimeInputUpdateDelete!!))

        binding?.endTimeInputUpdateDelete?.let { addTextChangedListener(it, binding?.endTimeLayoutUpdateDelete!!) }
        binding?.endTimeInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.endTimeInputUpdateDelete!!))

        binding?.createdAtInput?.let { addTextChangedListener(it, binding?.createdAtLayout!!) }
        binding?.createdAtInput?.addTextChangedListener(TW.CrossIconBehave(binding?.createdAtInput!!))

        binding?.updatedAtInput?.let { addTextChangedListener(it, binding?.updatedAtLayout!!) }
        binding?.updatedAtInput?.addTextChangedListener(TW.CrossIconBehave(binding?.updatedAtInput!!))

        binding?.descriptionInputUpdateDelete?.let { addTextChangedListener(it, binding?.descriptionLayoutUpdateDelete!!) }
        binding?.descriptionInputUpdateDelete?.addTextChangedListener(TW.CrossIconBehave(binding?.descriptionInputUpdateDelete!!))
    }

    private fun addTextChangedListener(editText: TextInputEditText, inputLayout: TextInputLayout) {
        editText.easyOnTextChangedListener { charSequence ->
            inputValidation(charSequence.toString(), editText, inputLayout)
        }

    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    //here received bundle model from MeetingDataListFragment
    private fun receivedDataModel(){
        val bundle: Bundle? = arguments
        bundle?.let {
            this.model = it.getParcelable("model")
            Timber.d("bundleModel $model")
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}