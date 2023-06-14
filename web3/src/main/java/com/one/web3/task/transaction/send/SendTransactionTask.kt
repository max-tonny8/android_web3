package com.one.web3.task.transaction.send

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigDecimal
import java.math.BigInteger

interface SendTransactionTask : Web3Task<SendTransactionParam, Pair<String, BigInteger>>

open class SendTransactionParam(

    open val to: String,
    open val from: String,

    open val data: String,
    open val value: BigInteger,

    open val nonce: BigInteger?,
    open val gasLimit: BigInteger,
    open val gasPrice: BigDecimal,
    open val priorityFee: BigDecimal,

    override var chainId: Long,
    override var rpcUrls: List<String>,
    override var sync: Boolean
) : Param(chainId, rpcUrls, sync)
