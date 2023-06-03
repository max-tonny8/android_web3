package com.one.web3.task.gaslimit.sign

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.EvmCall
import com.one.web3.task.gaslimit.GasLimitEvmCallTask
import org.web3j.protocol.core.methods.request.Transaction

class GasLimitSignEvmCallTask : GasLimitEvmCallTask(), EvmCall {

    override suspend fun provideTransaction(param: Param): Transaction {

        if (param !is GasLimitSignParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }


        return Transaction(param.from, null, null, null, param.to, param.value, param.data)
    }
}