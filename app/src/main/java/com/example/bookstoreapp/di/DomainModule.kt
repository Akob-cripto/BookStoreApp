package com.example.bookstoreapp.di

import com.example.domain.usecase.cart.AddBookToCartUseCase
import com.example.domain.usecase.favorite.AddBookToFavoritesUseCase
import com.example.domain.usecase.auth.CheckIsAdminUseCase
import com.example.domain.usecase.order.CreateOrderUseCase
import com.example.domain.usecase.book.GetBooksUseCase
import com.example.domain.usecase.cart.GetCartBookIdsUseCase
import com.example.domain.usecase.order.GetMyOrdersUseCase
import com.example.domain.usecase.cart.RemoveBookFromCartUseCase
import com.example.domain.usecase.favorite.RemoveBookFromFavoritesUseCase
import com.example.domain.usecase.auth.SignInUseCase
import com.example.domain.usecase.auth.SignUpUseCase
import org.koin.dsl.module
import com.example.domain.usecase.book.SaveBookUseCase
import com.example.domain.usecase.auth.SignOutUseCase
import com.example.domain.usecase.order.CancelOrderUseCase


val domainModule = module {
    factory { SignInUseCase(userRepository = get()) }
    factory { SignUpUseCase(userRepository = get()) }
    factory { SignOutUseCase(userRepository = get()) }
    factory { CheckIsAdminUseCase(userRepository = get()) }

    factory { GetBooksUseCase(bookRepository = get()) }
    factory { SaveBookUseCase(bookRepository = get()) }

    factory { AddBookToFavoritesUseCase(bookRepository = get()) }
    factory { RemoveBookFromFavoritesUseCase(bookRepository = get()) }

    factory { AddBookToCartUseCase(cartRepository = get()) }
    factory { RemoveBookFromCartUseCase(cartRepository = get()) }
    factory { GetCartBookIdsUseCase(cartRepository = get()) }

    factory { CreateOrderUseCase(orderRepository = get()) }
    factory { GetMyOrdersUseCase(orderRepository = get()) }
    factory { CancelOrderUseCase(orderRepository = get()) }
}