package com.example.bookstoreapp.di


import com.example.bookstoreapp.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module



val appModule = module {
    viewModel {
        MainViewModel(
            signInUseCase = get(),
            signUpUseCase = get(),
            checkIsAdminUseCase = get()
        )
    }
}