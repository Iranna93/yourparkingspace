package com.reddit.test.domain.sealed

data class DomainErrorResponse(
    val errorCode: Int?,
    val errorMessage: String?
)