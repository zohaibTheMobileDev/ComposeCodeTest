package com.zohaibraza.composetest.model

data class LoadingState(
    var inProgress: Boolean = true,
    var isError: Boolean = false,
    var errorMessage: String? = null,
    var isSuccess: Boolean = false
)
