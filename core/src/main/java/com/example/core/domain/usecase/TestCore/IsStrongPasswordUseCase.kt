package com.example.core.domain.usecase.TestCore

class IsStrongPasswordUseCase {

    operator fun invoke(string: String): Boolean {
        var isDigit = false
        var isUpperCase = false
        var isLowerCase = false
        var isSpecialCharacter = false

        string.forEach {
            if (it.isDigit()) isDigit = true
            if (it.isLowerCase()) isLowerCase = true
            if (it.isUpperCase()) isUpperCase = true
            if (
                it.toString() == "@" ||
                it.toString() == "." ||
                it.toString() == "," ||
                it.toString() == "_" ||
                it.toString() == "/"
            ) isSpecialCharacter =
                true
        }

        return isDigit && isLowerCase && isUpperCase && isSpecialCharacter
    }
}