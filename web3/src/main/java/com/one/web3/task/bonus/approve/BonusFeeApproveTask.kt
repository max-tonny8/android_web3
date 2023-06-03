package com.one.web3.task.bonus.approve

import com.one.web3.Param
import java.math.BigInteger

data class BonusFeeApproveParam(

    val smartContractAddress: String,

    val tokenAmount: BigInteger,

    override var chainId: Long = 0L,
    override var rpcUrls: List<String> = emptyList(),
    override val sync: Boolean
) : Param(
    chainId,
    rpcUrls,
    sync
)