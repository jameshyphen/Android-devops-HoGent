package com.hogent.pandora.utils

import android.text.TextUtils
import java.math.BigInteger
import java.security.MessageDigest

fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun checkPassword(password: String): Boolean {
    return password.length < 6
}

fun inputCheck(userName: String, password: String): Boolean {
    return !(TextUtils.isEmpty(userName) && TextUtils.isEmpty(password))
}