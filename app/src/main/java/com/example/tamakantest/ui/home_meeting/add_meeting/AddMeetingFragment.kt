package com.example.tamakantest.ui.home_meeting.add_meeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.tamakantest.R
import com.example.tamakantest.databinding.FragmentAddMeetingBinding
import com.example.tamakantest.ui.api.model.add_meeting.MeetingAddRequestBody
import com.example.tamakantest.ui.api.model.meeting_data_list.Data
import com.example.tamakantest.ui.api.model.update_meeting.MeetingUpdateRequestBody
import com.example.tamakantest.ui.home_meeting.view_model.MeetingCommonViewModel
import com.example.tamakantest.ui.utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import timber.log.Timber.d

class AddMeetingFragment : Fragment() {
    private var binding: FragmentAddMeetingBinding? = null
    private val viewModel: MeetingCommonViewModel by inject()
    private var data: Data? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentAddMeetingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        meetingDataInputValidation()

        //initially cursor Focus & visible at Name Input Field
        binding?.userIdInput?.let { requestFocus(it) }
        binding?.userIdInput?.isCursorVisible = true
    }

    //function for meetingDataInputValidation
    private fun meetingDataInputValidation() {
        addTextChangedListener()
        binding?.submitFeedbackBtn?.setOnClickListener {
            //binding.layoutAddMeeting.hideKeyboard()
            d("Validation check 1  ${validateUserId()}")
            if (validateUserId()) {
                d("Validation check 2  ${validateRoomId()}")
                if (validateRoomId()) {
                    d("Validation check 3  ${validateClientName()}")
                    if (validateClientName()) {
                        d("Validation check 4  ${validateCompanyName()}")
                        if (validateCompanyName()) {
                            d("Validation check 5  ${validateDate()}")
                            if (validateDate()){
                                d("Validation check 6  ${validateStartTime()}")
                                if (validateStartTime()){
                                    d("Validation check 7  ${validateEndTime()}")
                                    if (validateEndTime()){
                                        d("Validation check 8  ${validateDescription()}")
                                        if (validateDescription()){
                                            addTextChangedListener()
                                            addMeeting()
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

    //function for add new meeting data
    private fun addMeeting(){

        val requestBody = MeetingAddRequestBody(
            binding?.clientNameInput?.text?.trim().toString(),
            binding?.companyNameInput?.text?.trim().toString(),
            binding?.dateInput?.text?.trim().toString(),
            binding?.startTimeInput?.text?.trim().toString(),
            binding?.endTimeInput?.text?.trim().toString(),
            binding?.descriptionInput?.text?.trim().toString(),
            binding?.roomIdInput?.text?.trim().toString(),
            binding?.userIdInput?.text?.trim().toString()

        )
        viewModel.addMeeting(requestBody).observe(viewLifecycleOwner) { response->
            if (response.data !=null){
                if ((data?.room_id != response.data.room_id ) && (data?.start_time != response.data.start_time))
                {
                    Toast.makeText(activity,"Room add successfully", Toast.LENGTH_SHORT).show()
                    clearInputFieldData()
                }else{
                    Toast.makeText(activity, "Room booked by another user", Toast.LENGTH_SHORT).show()
                }

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

    //function for clear InputField after submitted UserFeedback
    private fun clearInputFieldData(){
        binding?.clientNameInput?.clearText()
        binding?.clientNameLayout?.isErrorEnabled = false

        binding?.companyNameInput?.clearText()
        binding?.companyNameLayout?.isErrorEnabled = false

        binding?.dateInput?.clearText()
        binding?.dateLayout?.isErrorEnabled = false

        binding?.startTimeInput?.clearText()
        binding?.startTimeLayout?.isErrorEnabled = false

        binding?.endTimeInput?.clearText()
        binding?.endTimeLayout?.isErrorEnabled = false

        binding?.descriptionInput?.clearText()
        binding?.descriptionLayout?.isErrorEnabled = false

        binding?.roomIdInput?.clearText()
        binding?.roomIdLayout?.isErrorEnabled = false

        binding?.userIdInput?.clearText()
        binding?.userIdInput?.let { requestFocus(it) }
        binding?.userIdLayout?.isErrorEnabled = false
    }

    private fun validateUserId(): Boolean {
        if (binding?.userIdInput.getString().trim().isEmpty()) {
            binding?.userIdLayout?.isErrorEnabled = true
            binding?.userIdLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.userIdInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.userIdLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateRoomId(): Boolean {
        if (binding?.roomIdInput.getString().trim().isEmpty()) {
            binding?.roomIdLayout?.isErrorEnabled = true
            binding?.roomIdLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.roomIdInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.roomIdLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateClientName(): Boolean {
        if (binding?.clientNameInput.getString().trim().isEmpty()) {
            binding?.clientNameLayout?.isErrorEnabled = true
            binding?.clientNameLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.clientNameInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.clientNameLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateCompanyName(): Boolean {
        if (binding?.companyNameInput.getString().trim().isEmpty()) {
            binding?.companyNameLayout?.isErrorEnabled = true
            binding?.companyNameLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.companyNameInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.companyNameLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateDate(): Boolean {
        if (binding?.dateInput.getString().trim().isEmpty()) {
            binding?.dateLayout?.isErrorEnabled = true
            binding?.dateLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.dateInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.dateLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateStartTime(): Boolean {
        if (binding?.startTimeInput.getString().trim().isEmpty()) {
            binding?.startTimeLayout?.isErrorEnabled = true
            binding?.startTimeLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.startTimeInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.startTimeLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateEndTime(): Boolean {
        if (binding?.endTimeInput.getString().trim().isEmpty()) {
            binding?.endTimeLayout?.isErrorEnabled = true
            binding?.endTimeLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.endTimeInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.endTimeLayout?.isErrorEnabled = false
            return true
        }

    }

    private fun validateDescription(): Boolean {
        if (binding?.descriptionInput.getString().trim().isEmpty()) {
            binding?.descriptionLayout?.isErrorEnabled = true
            binding?.descriptionLayout?.error = resources.getString(R.string.field_empty_error_message_common)
            binding?.descriptionInput?.let { requestFocus(it) }
            return false
        } else {
            binding?.descriptionLayout?.isErrorEnabled = false
            return true
        }

    }



    private fun addTextChangedListener() {
        binding?.userIdInput?.let { addTextChangedListener(it, binding?.userIdLayout!!) }
        binding?.userIdInput?.addTextChangedListener(TW.CrossIconBehave(binding?.userIdInput!!))

        binding?.roomIdInput?.let { addTextChangedListener(it, binding?.roomIdLayout!!) }
        binding?.roomIdInput?.addTextChangedListener(TW.CrossIconBehave(binding?.roomIdInput!!))

        binding?.clientNameInput?.let { addTextChangedListener(it, binding?.clientNameLayout!!) }
        binding?.clientNameInput?.addTextChangedListener(TW.CrossIconBehave(binding?.clientNameInput!!))

        binding?.companyNameInput?.let { addTextChangedListener(it, binding?.companyNameLayout!!) }
        binding?.companyNameInput?.addTextChangedListener(TW.CrossIconBehave(binding?.companyNameInput!!))

        binding?.dateInput?.let { addTextChangedListener(it, binding?.dateLayout!!) }
        binding?.dateInput?.addTextChangedListener(TW.CrossIconBehave(binding?.dateInput!!))

        binding?.startTimeInput?.let { addTextChangedListener(it, binding?.startTimeLayout!!) }
        binding?.startTimeInput?.addTextChangedListener(TW.CrossIconBehave(binding?.startTimeInput!!))

        binding?.endTimeInput?.let { addTextChangedListener(it, binding?.endTimeLayout!!) }
        binding?.endTimeInput?.addTextChangedListener(TW.CrossIconBehave(binding?.endTimeInput!!))

        binding?.descriptionInput?.let { addTextChangedListener(it, binding?.descriptionLayout!!) }
        binding?.descriptionInput?.addTextChangedListener(TW.CrossIconBehave(binding?.descriptionInput!!))
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}