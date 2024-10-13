package com.zohaibraza.composetest.model

data class LoginCredentials(
    var userName: String = "",
    var password: String = ""
) {
    fun isNotEmpty(): Boolean {
        return userName.isNotEmpty() && password.isNotEmpty()
    }

    fun errorMessage(): String {
        return if (userName.isEmpty())
            "Enter username"
        else if (password.isEmpty())
            "Enter password"
        else
            ""
    }
}
