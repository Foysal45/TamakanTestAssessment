package com.example.tamakantest.ui.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object Validator {
    val VALID_EMAIL_ADDRESS_REGEX: Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    val VALID_MOBILE_NUMBER_REGEX: Pattern = Pattern.compile("(^(\\+88|0088)?(01){1}[3456789]{1}(\\d){8})$", Pattern.CASE_INSENSITIVE)
    //Pattern.compile("^(01|০১)?[0-9০১২৩৪৫৬৭৮৯]{9}$", Pattern.CASE_INSENSITIVE)

    val VALID_MOBILE_NUMBER_REGEXAgain: Pattern = Pattern.compile("(8801|৮৮০১)?[0-9০১২৩৪৫৬৭৮৯]{9}$", Pattern.CASE_INSENSITIVE)

    fun isValidEmail(email: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email)
        return matcher.find()
    }

    fun isValidMobileNumber(mobileNumber: String?): Boolean {
        val matcher: Matcher = VALID_MOBILE_NUMBER_REGEX.matcher(mobileNumber)
        return matcher.find()
    }

    fun isValidMobileNumberAgain(mobileNumber1: String?): Boolean {
        val matcher: Matcher =
            VALID_MOBILE_NUMBER_REGEXAgain.matcher(mobileNumber1)
        return matcher.find()
    }

}