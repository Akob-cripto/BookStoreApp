package com.example.bookstoreapp.di

import android.content.Context
import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.UserStorage
import com.example.data.storage.database.FireBaseStorage
import com.example.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val dataModule = module {
    single <FirebaseAuth>{ FirebaseAuth.getInstance() }

    single <FirebaseFirestore>{ FirebaseFirestore.getInstance()}

    single<UserStorage> {
        FireBaseStorage(
            auth = get(),
            firestore = get(),
            context = androidContext()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(fire = get())
    }
}
