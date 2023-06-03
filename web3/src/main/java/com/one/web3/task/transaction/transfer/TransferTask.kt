package com.one.web3.task.transaction.transfer

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal
import java.math.BigInteger


interface TransferTask : Web3Task<TransferParam, Pair<String, BigInteger>>

data class TransferParam(
    val walletAddress: String,
    val receiverAddress: String,

    val tokenId: BigInteger? = null,
    val tokenLogo: String,
    val tokenAmount: BigInteger,
    val tokenSymbol: String,
    val tokenDecimal: Int,
    val tokenAddress: String,

    val isTokenNative: Boolean,

    val nonce: BigInteger?,
    val gasLimit: BigInteger,
    val gasPrice: BigDecimal, // maxFee
    val priorityFee: BigDecimal,

    override var chainId: Long = 0L,
    override var rpcUrls: List<String> = emptyList(),
    override val sync: Boolean
) : Param(
    chainId,
    rpcUrls,
    sync
)