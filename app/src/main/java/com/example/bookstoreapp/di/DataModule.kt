package com.example.bookstoreapp.di

import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.UserStorage
import com.example.data.storage.database.FireBase
import com.example.domain.repository.UserRepository

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module



val dataModule = module {
    single <FirebaseAuth>{ FirebaseAuth.getInstance() }

    single<UserStorage> {
        FireBase(auth = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(fire = get())
    }
}
