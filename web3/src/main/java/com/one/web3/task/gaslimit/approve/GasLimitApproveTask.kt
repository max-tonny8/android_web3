package com.one.web3.task.gaslimit.approve

import com.one.web3.Param
import java.math.BigInteger

data class GasLimitApproveParam(
    val walletAddress: String,
    val smartContractAddress: String,

    val tokenAmount: BigInteger,
    val tokenAddress: String,

    override var chainId: Long = 0L,
    override var rpcUrls: List<String> = emptyList(),
    override val sync: Boolean
) : Param(
    chainId,
    rpcUrls,
    sync
)