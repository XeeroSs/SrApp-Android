package com.xeross.srapp.ui.auth.login

import android.content.Intent
import com.xeross.srapp.R
import com.xeross.srapp.ui.auth.BaseAuthActivity
import com.xeross.srapp.ui.auth.register.RegisterActivity
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes
import com.xeross.srapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseAuthActivity() {
    
    override fun getFragmentId() = R.layout.activity_login
    
    override fun getViewModelClass() = LoginViewModel::class.java
    
    private var viewModel: LoginViewModel? = null
    override fun build() {
        viewModel = (vm as LoginViewModel)
        viewModel?.build()
        buildUI()
        
    }
    
    private fun setInputText() {
        textInputs.apply {
            add(AuthTextInputTypes.EMAIL, email_edit_text)
            add(AuthTextInputTypes.PASSWORD, password_edit_text)
        }
    }
    
    private fun buildUI() {
        onClick()
        setInputText()
    }
    
    private fun onClick() {
        login_text_button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            
            startActivity(intent)
        }
        
        login_button.setOnClickListener {
            login()
        }
        
        // TODO("Update icon password")
    }
    
    private fun successLogin() {
        goToActivity<MainActivity>()
    }
    
    private fun login() {
        login_button.antiSpam()
        clearTextInputError()
        
        val email = getField(AuthTextInputTypes.EMAIL) ?: return
        getField(AuthTextInputTypes.PASSWORD) ?: return
        
        viewModel?.login(email)?.observe(this, { ex ->
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successLogin()
                    else -> errorEditText(*it)
                }
            }
        })
    }
    
    
}