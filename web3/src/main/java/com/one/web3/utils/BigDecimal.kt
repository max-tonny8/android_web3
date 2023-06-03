package com.one.web3.utils

import org.web3j.utils.Convert
import java.math.BigDecimal

fun String?.toWei(unit: Convert.Unit? = null) = Convert.toWei(this.toBigDecimalOrZero(), unit ?: Convert.Unit.GWEI)

fun BigDecimal.toWei(unit: Convert.Unit? = null) = Convert.toWei(this, unit ?: Convert.Unit.GWEI)


fun String?.fromWei(unit: Convert.Unit? = null) = Convert.fromWei(this.toBigDecimalOrZero(), unit ?: Convert.Unit.GWEI)

fun BigDecimal.fromWei(unit: Convert.Unit? = null) = Convert.fromWei(this, unit ?: Convert.Unit.GWEI)