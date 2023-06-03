package com.one.web3.task.gaslimit.sign

import com.one.web3.Param
import java.math.BigInteger

data class GasLimitSignParam(

    val to: String,
    val from: String,

    val data: String,
    val value: BigInteger,

    override var chainId: Long = 0L,
    override var rpcUrls: List<String> = emptyList(),
    override val sync: Boolean
) : Param(
    chainId,
    rpcUrls,
    sync
)