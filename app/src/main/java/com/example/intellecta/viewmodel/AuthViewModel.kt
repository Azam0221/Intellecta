package com.example.intellecta.viewmodel

import androidx.lifecycle.ViewModel
import com.example.intellecta.model.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent


class AuthViewModel: ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val  _authState  = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    var authState : StateFlow<AuthState> = _authState

    init {
        if(auth.currentUser==null){
            _authState.value = AuthState.Unauthenticated
        }
        else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String){
        _authState.value = AuthState.Loading

        if(email.isEmpty()||password.isEmpty()){
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }
                else{
                    _authState.value = AuthState.Error("Something went wrong")
                }
            }
    }

    fun  signUp(email:String , password: String){
        _authState.value = AuthState.Loading
        if(email.isEmpty()||password.isEmpty()){
            _authState.value = AuthState.Error("Email or password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated

                }
                else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")

                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


}
