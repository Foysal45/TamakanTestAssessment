package com.example.tamakantest.ui.home_meeting.meeting_data_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tamakantest.databinding.ItemViewMeetingDataListBinding
import com.example.tamakantest.ui.api.model.meeting_data_list.Data

class MeetingDataListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val meetingDataList: MutableList<Data> = mutableListOf()
    var onItemClick: ((meetingDataList: Data, position: Int) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemViewMeetingDataListBinding = ItemViewMeetingDataListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewModel) {
            val model = meetingDataList[position]
            val binding = holder.binding

            if (model !=null){

                binding.dateId.text = "Date : "+ model.date
                binding.meetingId.text = "Meeting Id : "+ model.id
                binding.roomId.text = "Room Id : "+ model.room_id
                binding.userId.text = "User Id : "+ model.user_id
                binding.clientNameId.text = "Client Name : "+ model.client_name
                binding.companyNameId.text = "Company Name : "+ model.company_name
                binding.startTimeId.text = "Start Time : "+ model.start_time
                binding.endTimeId.text = "End Time : "+ model.end_time
                binding.descriptionId.text = "Description : "+ model.description
            }

        }
    }

    override fun getItemCount(): Int =  meetingDataList.size

    inner class ViewModel(val binding: ItemViewMeetingDataListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(meetingDataList[absoluteAdapterPosition], absoluteAdapterPosition)
                }
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun initLoad(list: List<Data>) {
        meetingDataList.clear()
        meetingDataList.addAll(list)
        notifyDataSetChanged()
    }
}