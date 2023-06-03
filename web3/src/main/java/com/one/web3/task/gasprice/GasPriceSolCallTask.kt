package com.one.web3.task.gasprice

import com.one.web3.task.SolCall
import java.math.BigInteger

class GasPriceSolCallTask() : GasPriceCallTask, SolCall {

    override suspend fun executeTask(param: GasPriceParam): BigInteger {

        return call("getRecentBlockhash", param.rpcUrls, param.sync).let {

            it.get("value")?.get("feeCalculator")?.get("lamportsPerSignature")?.toString()?.toBigInteger() ?: BigInteger.ZERO
        }
    }
}