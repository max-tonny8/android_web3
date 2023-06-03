package com.one.web3.task.priorityfee

import com.one.web3.task.SolCall
import java.math.BigDecimal
import java.math.BigInteger

class PriorityFeeSolCallTask : PriorityFeeTask, SolCall {

    override suspend fun executeTask(param: PriorityFeeParam): BigDecimal {

        return BigDecimal.ZERO
    }
}