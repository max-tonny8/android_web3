package com.one.web3.task.minednonce

import com.one.web3.task.SolCall
import java.math.BigInteger

class MinedNonceSolCallTask : MinedNonceTask, SolCall {

    override suspend fun executeTask(param: MinedNonceParam): BigInteger {

        return BigInteger.ZERO
    }
}