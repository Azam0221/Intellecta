package com.example.intellecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellecta.model.AuthState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AuthViewModel(
    private val supabase: SupabaseClient
): ViewModel() {


    private val  _authState  = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    var authState : StateFlow<AuthState> = _authState

    init {
        checkSession()
    }

    private fun checkSession(){
        viewModelScope.launch {
            try {
                val session = supabase.auth.currentSessionOrNull()
                if(session != null){
                    _authState.value = AuthState.Authenticated
                }
                else{
                    _authState.value = AuthState.Unauthenticated
                }
            }
            catch (e: Exception) {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String){

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email){
                    this.email = email
                    this.password = password
                }
                _authState.value = AuthState.Authenticated
            }catch (e: Exception){
                _authState.value = AuthState.Error("Login Failed")
            }
        }
    }

    fun  signUp(email:String , password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Signup failed")
            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
            }catch (e: Exception){
                println(e.message)
            }
            finally {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun getCurrentUserId(): String? {
        return supabase.auth.currentUserOrNull()?.id
    }


}
