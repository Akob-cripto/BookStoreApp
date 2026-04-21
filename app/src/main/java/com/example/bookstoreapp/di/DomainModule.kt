package com.example.bookstoreapp.di

import com.example.domain.usecase.CheckIsAdminUseCase
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
            userRepository = get()
        )
    }
}