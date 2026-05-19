package com.example.bookstoreapp.di

import com.example.domain.usecase.AddBookToFavoritesUseCase
import com.example.domain.usecase.CheckIsAdminUseCase
import com.example.domain.usecase.GetBooksUseCase
import com.example.domain.usecase.RemoveBookFromFavoritesUseCase
import com.example.domain.usecase.SignInUseCase
import com.example.domain.usecase.SignUpUseCase
import org.koin.dsl.module
import com.example.domain.usecase.SaveBookUseCase


val domainModule = module {
    factory<SignInUseCase> {
        SignInUseCase(
            userRepository = get()
        )
    }

    factory<SignUpUseCase>{
        SignUpUseCase(
            userRepository = get()
        )
    }

    factory<CheckIsAdminUseCase>{
        CheckIsAdminUseCase(
            userRepository = get()
        )
    }

    factory<SaveBookUseCase>{
        SaveBookUseCase(
            bookRepository = get()
        )
    }

    factory<GetBooksUseCase>{
        GetBooksUseCase(bookRepository = get())
    }

    factory<AddBookToFavoritesUseCase>{
        AddBookToFavoritesUseCase(bookRepository = get())
    }

    factory<RemoveBookFromFavoritesUseCase>{
        RemoveBookFromFavoritesUseCase(bookRepository = get())
    }
}