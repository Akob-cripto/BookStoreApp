package com.example.bookstoreapp.di

import com.example.data.repository.BookRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.BookStorage
import com.example.data.storage.UserStorage
import com.example.data.storage.database.FireBaseStorage
import com.example.data.storage.database.FirebaseBookStorage
import com.example.domain.repositories.BookRepository
import com.example.domain.repositories.UserRepository
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

    single<BookStorage> {
        FirebaseBookStorage(
            firestore = get(),
            auth = get())
    }

    single<BookRepository> {
        BookRepositoryImpl(bookStorage = get())
    }

}
