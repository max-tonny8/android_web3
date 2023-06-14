package com.one.web3.task.transaction.approve

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal
import java.math.BigInteger

interface TokenApproveTask : Web3Task<TokenApproveParam, Pair<String, BigInteger>>

data class TokenApproveParam(

    val walletAddress: String,
    val smartContractAddress: String,

    val tokenAmount: BigInteger,
    val tokenAddress: String,

    val data: String,
    val value: BigInteger,

    val nonce: BigInteger?,
    val gasLimit: BigInteger,
    val gasPrice: BigDecimal,
    val priorityFee: BigDecimal,

    override var chainId: Long,
    override var rpcUrls: List<String>,
    override var sync: Boolean,
) : Param(
    chainId,
    rpcUrls,
    sync
)