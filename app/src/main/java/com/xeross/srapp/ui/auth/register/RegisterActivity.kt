package com.xeross.srapp.ui.auth.register

import com.xeross.srapp.R
import com.xeross.srapp.ui.auth.BaseAuthActivity
import com.xeross.srapp.ui.auth.register.exceptions.ExceptionRegisterTypes
import com.xeross.srapp.ui.auth.types.AuthTextInputTypes
import com.xeross.srapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseAuthActivity() {
    
    override fun getFragmentId() = R.layout.activity_register
    override fun getViewModelClass() = RegisterViewModel::class.java
    
    private var viewModel: RegisterViewModel? = null
    
    override fun build() {
        viewModel = (vm as RegisterViewModel)
        viewModel?.build()
        buildUI()
    }
    
    private fun buildUI() {
        onClick()
        setInputText()
    }
    
    private fun setInputText() {
        textInputs.apply {
            add(AuthTextInputTypes.PSEUDO, pseudo_edit_text)
            add(AuthTextInputTypes.EMAIL, email_edit_text)
            add(AuthTextInputTypes.PASSWORD, password_edit_text)
            add(AuthTextInputTypes.CONFIRM_PASSWORD, confirm_password_edit_text)
        }
    }
    
    private fun onClick() {
        login_text_button.setOnClickListener {
/*            val intent = Intent(this, LoginActivity::class.java).also {
                it.sendExtra(pseudo_edit_text, PSEUDO_EXTRA_REGISTER)
                it.sendExtra(email_edit_text, EMAIL_EXTRA_REGISTER)
                it.sendExtra(password_edit_text, PASSWORD_EXTRA_REGISTER)
                it.sendExtra(confirm_password_edit_text, CONFIRM_PASSWORD_EXTRA_REGISTER)
            }*/
            
            finish()
        }
        
        register_button.setOnClickListener {
            register()
        }
        
        // TODO("Update icon password")
    }
    
    private fun successRegister() {
        goToActivity<MainActivity>()
    }
    
    private fun register() {
        
        register_button.antiSpam()
        clearTextInputError()
        
        val pseudo = getField(AuthTextInputTypes.PSEUDO) ?: return
        val email = getField(AuthTextInputTypes.EMAIL) ?: return
        val password = getField(AuthTextInputTypes.PASSWORD) ?: return
        val confirmPassword = getField(AuthTextInputTypes.CONFIRM_PASSWORD) ?: return
        
        viewModel?.register(pseudo, email, password, confirmPassword)?.observe(this, { ex ->
            ex?.let {
                when (ExceptionRegisterTypes.SUCCESS) {
                    it[0] -> successRegister()
                    else -> errorEditText(*it)
                }
            }
        })
    }
}