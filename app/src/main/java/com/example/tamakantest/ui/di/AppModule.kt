package com.example.tamakantest.ui.di

import com.example.tamakantest.ui.api.baseUrl.AppConstant
import com.example.tamakantest.ui.api.endpoint.ApiInterface
import com.example.tamakantest.ui.home_meeting.view_model.MeetingCommonViewModel
import com.example.tamakantest.ui.login.LoginViewModel
import com.example.tamakantest.ui.repository.AppRepository
import com.example.tamakantest.ui.utils.RetrofitUtils.createCache
import com.example.tamakantest.ui.utils.RetrofitUtils.createOkHttpClient
import com.example.tamakantest.ui.utils.RetrofitUtils.getGson
import com.example.tamakantest.ui.utils.RetrofitUtils.retrofitInstance
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { getGson() }
    single { createCache(get()) }
    single { createOkHttpClient(get()) }
    single(named("normal")) { createOkHttpClient(get()) }

    single(named("api")) { retrofitInstance(AppConstant.BASE_URL, get(), get()) }

    single { ApiInterface(get(named("api"))) }

    single { AppRepository(get()) }

    viewModel { MeetingCommonViewModel(get()) }
    viewModel { LoginViewModel(get()) }



}