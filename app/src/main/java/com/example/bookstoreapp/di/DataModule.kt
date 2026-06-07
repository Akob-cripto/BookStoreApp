package com.example.bookstoreapp.di

import com.example.data.repository.BookRepositoryImpl
import com.example.data.repository.CartRepositoryImpl
import com.example.data.repository.OrderRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.BookStorage
import com.example.data.storage.CartStorage
import com.example.data.storage.OrderStorage
import com.example.data.storage.UserStorage
import com.example.data.storage.database.FireBaseStorage
import com.example.data.storage.database.FirebaseBookStorage
import com.example.data.storage.database.FirebaseCartStorage
import com.example.data.storage.database.FirebaseOrderStorage
import com.example.domain.repositories.BookRepository
import com.example.domain.repositories.CartRepository
import com.example.domain.repositories.OrderRepository
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

    single<CartRepository>{
        CartRepositoryImpl(cartStorage = get())
    }

    single<CartStorage>{
        FirebaseCartStorage(
            firestore = get(),
            auth = get()
        )
    }

    single<OrderStorage> {
        FirebaseOrderStorage(
            auth = get(),
            firestore = get()
        )
    }

    single<OrderRepository> {
        OrderRepositoryImpl(
            orderStorage = get()
        )
    }
}
