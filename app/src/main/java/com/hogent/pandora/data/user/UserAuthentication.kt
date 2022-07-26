package com.hogent.pandora.data.user

object UserAuthentication  {

    var user: User? = null

    fun isLoggedIn(): Boolean = user != null

    fun login(user: User){
        this.user = user
    }

    fun logout(){
        this.user = null
    }
}