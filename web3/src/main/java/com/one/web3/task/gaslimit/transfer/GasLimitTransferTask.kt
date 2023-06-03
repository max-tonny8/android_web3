package com.one.web3.task.gaslimit.transfer

import com.one.web3.Param
import java.math.BigInteger

data class GasLimitTransferParam(
    val walletAddress: String,
    val receiverAddress: String,

    val tokenId: BigInteger? = null,
    val tokenAmount: BigInteger,
    val tokenAddress: String,

    val isNativeToken: Boolean,

    override var chainId: Long = 0L,
    override var rpcUrls: List<String> = emptyList(),
    override val sync: Boolean
) : Param(
    chainId,
    rpcUrls,
    sync
)