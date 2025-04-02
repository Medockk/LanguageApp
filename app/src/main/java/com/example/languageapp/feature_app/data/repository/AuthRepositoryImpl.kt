package com.example.languageapp.feature_app.data.repository

import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.data_source.remote.Supabase.client
import com.example.languageapp.feature_app.data.model.UserDataModelEntity
import com.example.languageapp.feature_app.domain.repository.AuthRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest

class AuthRepositoryImpl(
    private val userDataDao: UserDataDao
) : AuthRepository {

    override suspend fun signIn(mail: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = mail
            this.password = password
        }
    }

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        mail: String,
        password: String
    ) {
        client.auth.signUpWith(Email) {
            this.email = mail
            this.password = password
        }
        val userId = client.auth.currentUserOrNull()?.id ?: ""

        client.postgrest["Users"].insert(
            mapOf(
                "userID" to userId,
                "firstName" to firstName,
                "lastName" to lastName
            )
        )

        userDataDao.upsertData(
            UserDataModelEntity(
                0, userId, firstName, lastName, "",
            )
        )
    }

    override suspend fun signOut() {
        client.auth.signOut()
    }
}