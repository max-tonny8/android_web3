package com.one.web3.task.gasprice

import com.one.web3.Param
import com.one.web3.Web3Task
import java.math.BigInteger

interface GasPriceCallTask : Web3Task<GasPriceParam, BigInteger>

data class GasPriceParam(override val chainId: Long, override val rpcUrls: List<String>) : Param(chainId, rpcUrls)
