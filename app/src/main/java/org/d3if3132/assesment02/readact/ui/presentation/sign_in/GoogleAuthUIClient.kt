package org.d3if3132.assesment02.readact.ui.presentation.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import org.d3if3132.assesment02.readact.R
import org.d3if3132.assesment02.readact.model.SignInResult
import java.util.concurrent.CancellationException
 class GoogleAuthUIClient(
        private val context: Context,
        private val oneTapClient: SignInClient
    ){
        private val auth = Firebase.auth

        suspend fun signIn(): IntentSender?{
            val result = try {
                oneTapClient.beginSignIn(
                    buildSignInRequest()
                ).await()
            } catch (e: Exception){
                e.printStackTrace()
                if (e is CancellationException) throw  e
                null
            }
            return result?.pendingIntent?.intentSender
        }

        suspend fun signInWithIntent(intent : Intent) : SignInResult {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            return try {
                val user = auth.signInWithCredential(googleCredentials).await().user
                SignInResult(
                    data = user?.run {
                        org.d3if3132.assesment02.readact.model.UserData(
                            userId = uid,
                            userName = displayName,
                            profilePictureUrl = photoUrl?.toString()
                        )
                    },
                    errorMessage = null
                )
            }catch (e:Exception){
                e.printStackTrace()
                if (e is CancellationException) throw e
                SignInResult(
                    data = null,
                    errorMessage = e.message
                )
            }
        }

        suspend fun signOut(){
            try {
                oneTapClient.signOut().await()
                auth.signOut()
            }catch(e: Exception){
                e.printStackTrace()
                if(e is CancellationException) throw e
            }
        }

        //Get user data when authentication is successful
        fun getSignedInUser(): org.d3if3132.assesment02.readact.model.UserData? = auth.currentUser?.run {
            org.d3if3132.assesment02.readact.model.UserData(
                userId = uid,
                userName = displayName,
                profilePictureUrl = photoUrl?.toString()
            )
        }

        private fun buildSignInRequest(): BeginSignInRequest {
            return BeginSignInRequest.Builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId(context.getString(R.string.web_client_id))
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build()
        }
    }