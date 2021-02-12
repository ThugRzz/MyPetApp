package com.thugrzz.mypetapp.data.validation

data class AcceptableValue<T>(
    val value: T,
    val status: Acceptance = Acceptance.ACCEPTED
) {
    val isAccepted: Boolean
        get() = status == Acceptance.ACCEPTED
}

