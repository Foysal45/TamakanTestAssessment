package com.example.tamakantest.ui.home_meeting.meeting_data_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tamakantest.ui.utils.hideKeyboard
import com.example.tamakantest.ui.utils.toast
import com.example.tamakantest.R
import com.example.tamakantest.databinding.FragmentMeetingDataListBinding
import com.example.tamakantest.ui.api.model.meeting_data_list.Data
import com.example.tamakantest.ui.home_meeting.view_model.MeetingCommonViewModel
import com.example.tamakantest.ui.utils.ViewState
import org.koin.android.ext.android.inject
import timber.log.Timber

class MeetingDataListFragment : Fragment() {

    private var binding: FragmentMeetingDataListBinding? = null
    private var dataAdapter: MeetingDataListAdapter = MeetingDataListAdapter()
    private val viewModel: MeetingCommonViewModel by inject()
    private var status: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentMeetingDataListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        getMeetingDataList(status)
        initClickLister()
    }


    private fun initView() {
        binding?.meetingDataListRecyclerView?.let { view ->
            with(view) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = dataAdapter
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
                        binding?.progressBar?.visibility = View.VISIBLE
                    } else {
                        binding?.progressBar?.visibility = View.GONE
                    }
                }

                else -> {}
            }
        }
    }

    private fun initClickLister() {
        dataAdapter.onItemClick = { model, _ ->
            //goToMeetingUpdateOrDeleteFragment(model)
               // Toast.makeText(activity, "Products Unavailable", Toast.LENGTH_SHORT).show()
            goToMeetingUpdateOrDeleteFragment(model)

        }

    }

    //transfer model to the MeetingUpdateOrDeleteFragment by bundle
    private fun goToMeetingUpdateOrDeleteFragment(model: Data) {
        val bundle = bundleOf("model" to model)
        Timber.d("modelData $bundle")
        // Toast.makeText(activity, model.categoryDetails.first().name, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_meetingDataListFragment_to_updateOrDeleteMeetingFragment, bundle)
    }

    private fun getMeetingDataList(status: String) {

        viewModel.getMeetingDataList(status).observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding?.emptyView?.visibility = View.VISIBLE
            } else {
                binding?.emptyView?.visibility = View.GONE
                dataAdapter.initLoad(list)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}