package com.one.web3.task.gaslimit.sign

import com.one.web3.Param
import com.one.web3.task.SolCall
import com.one.web3.task.gaslimit.GasLimitTask
import java.math.BigInteger

class GasLimitSignSolCallTask : GasLimitTask, SolCall {

    override suspend fun executeTask(param: Param): BigInteger {

        return BigInteger.ONE
    }
}