package com.one.web3.task.status

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigInteger


interface TransactionStatusTask : Web3Task<TransactionStatusParam, BigInteger>

data class TransactionStatusParam(
    val txHash: String,

    override val chainId: Long,
    override val rpcUrls: List<String>,
    override val sync: Boolean
) : Param(chainId, rpcUrls, sync)
