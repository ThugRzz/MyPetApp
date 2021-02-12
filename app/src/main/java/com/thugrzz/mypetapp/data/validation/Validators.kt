package com.thugrzz.mypetapp.data.validation

object Validators {

    private val nameRegex = Regex("^[a-zA-Zа-яА-Я\\s]+$")
    private const val MIN_NAME_LENGTH = 2
    private const val MAX_NAME_LENGTH = 100
    private val phoneRegex = Regex("^\\+7[0-9]{10}$")
    private val emailRegex = Regex("^[0-9a-z\\.]+@[a-z]+\\.[a-z]{2,4}$")

    fun validateName(name: String) = AcceptableValue(
        value = name,
        status = when {
            name.isEmpty() -> Acceptance.EMPTY
            name.length < MIN_NAME_LENGTH -> Acceptance.LENGTH_SMALL
            name.length > MAX_NAME_LENGTH -> Acceptance.LENGTH_LARGE
            nameRegex.matches(name) -> Acceptance.ACCEPTED
            else -> Acceptance.DECLINED
        }
    )

    fun validatePhone(phone: String) = AcceptableValue(
        value = phone,
        status = when {
            phone.isEmpty() -> Acceptance.EMPTY
            phone.length in 11..15 -> Acceptance.ACCEPTED
            //todo позже нужно переделать валидность масок
            //PhoneNumberTool.isValidNumber(phone) -> Acceptance.ACCEPTED
            else -> Acceptance.DECLINED
        }
    )

    fun validateEmailAcceptEmpty(email: String) = validateEmail(email, true)

    fun validateEmail(
        email: String,
        isAcceptEmpty: Boolean = false
    ) = AcceptableValue(
        value = email,
        status = when {
            email.isEmpty() -> if (isAcceptEmpty) Acceptance.ACCEPTED else Acceptance.EMPTY
            emailRegex.matches(email) -> Acceptance.ACCEPTED
            else -> Acceptance.DECLINED
        }
    )

    fun validateNotEmpty(text: String) = AcceptableValue(
        value = text,
        status = when {
            text.isEmpty() -> Acceptance.EMPTY
            else -> Acceptance.ACCEPTED
        }
    )
}