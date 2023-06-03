package com.one.web3.task.bonus.transfer

import com.one.web3.Param
import java.math.BigInteger

data class BonusFeeTransferParam(

    val walletAddress: String,
    val receiverAddress: String,

    val tokenId: BigInteger? = null,
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