package com.thugrzz.mypetapp.data.validation

enum class Acceptance {
    ACCEPTED,
    DECLINED,
    SYMBOLS_INCORRECT,
    LENGTH_SMALL,
    LENGTH_LARGE,
    EMPTY,
    DUPLICATE;

    val isAccepted: Boolean
        get() = this == ACCEPTED
}