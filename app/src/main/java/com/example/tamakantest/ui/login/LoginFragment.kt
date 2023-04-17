package com.example.tamakantest.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ktx.BuildConfig
import androidx.lifecycle.Observer
import com.example.tamakantest.databinding.FragmentLoginBinding
import com.example.tamakantest.ui.api.model.login.LoginRequest
import com.example.tamakantest.ui.utils.hideKeyboard
import com.example.tamakantest.ui.utils.toast
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by inject()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentLoginBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.loginBtn?.setOnClickListener {
            login()
        }
    }

    private fun login() {
         hideKeyboard()
        val email = binding?.inputEmail?.text.toString().trim()
        val password =  binding?.inputPassword?.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()){
            context?.toast("Enter email and password")
        }else{
            val requestBody = LoginRequest(email, password)
            viewModel.userLogin(requestBody).observe(viewLifecycleOwner, Observer { model->
                if (model.data.user.name == "demo" && model.data.user.email == "demo@gmail.com"){
                    context?.toast("login success")
                    if (activity != null) {
                        (activity as LoginActivity).goToHome()
                    }
                }else{
                    context?.toast("Email or password is not correct")
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}