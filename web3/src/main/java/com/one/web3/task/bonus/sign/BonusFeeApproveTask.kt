package com.one.web3.task.bonus.sign

import com.one.web3.Param
import java.math.BigInteger

data class BonusFeeSignParam(

    val data: String,

    override var chainId: Long = 0L,
    override var rpcUrls: List<String> = emptyList(),
    override val sync: Boolean
) : Param(
    chainId,
    rpcUrls,
    sync
)