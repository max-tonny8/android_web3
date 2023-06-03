package com.one.web3.utils

import java.math.BigDecimal

fun String?.toBigDecimalOrZero(): BigDecimal = this?.toBigDecimalOrNull() ?: BigDecimal.ZERO
