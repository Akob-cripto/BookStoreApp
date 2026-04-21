package com.example.domain.usecase

import com.example.domain.models.NewBookParam
import com.example.domain.repository.UserRepository
import com.sun.jndi.toolkit.url.Uri

class SaveBookUseCase(private val userRepository: UserRepository) {

    suspend fun execute(param: NewBookParam){
        userRepository.saveBook(param = param)
    }
}