package com.example.bookstoreapp.di

import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.UserStorage
import com.example.data.storage.database.FireBaseStorage
import com.example.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.dsl.module



val dataModule = module {
    single <FirebaseAuth>{ FirebaseAuth.getInstance() }

    single <FirebaseFirestore>{ FirebaseFirestore.getInstance()}

    single<UserStorage> {
        FireBaseStorage(
            auth = get(),
            firestore = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(fire = get())
    }
}
