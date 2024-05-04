package org.d3if3132.assesment02.readact.model

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId:String,
    val userName: String?,
    val profilePictureUrl: String?
)