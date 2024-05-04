package org.d3if3132.assesment02.readact.ui.presentation.sign_in

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.d3if3132.assesment02.readact.model.SignInResult
import org.d3if3132.assesment02.readact.model.SignInState

class SignInViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult){
        _state.update { it.copy(
            isSignSuccessful = result.data != null,
            signInError = result.errorMessage,
        )}
    }

    fun resetState(){
        _state.update {
            SignInState()
        }
    }
}