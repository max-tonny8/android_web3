package com.one.web3.task.gaslimit.approve

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.functionApproveEvmOf
import com.one.web3.task.EvmCall
import com.one.web3.task.gaslimit.GasLimitEvmCallTask
import org.web3j.abi.datatypes.Function
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

class GasLimitApproveEvmCallTask : GasLimitEvmCallTask(), EvmCall {

    override suspend fun provideTransaction(param: Param): Transaction {

        if (param !is GasLimitApproveParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }

        return Transaction(param.walletAddress, null, null, null, param.tokenAddress, BigInteger.ZERO, provideData(param))
    }

    override suspend fun provideFunction(param: Param): Function {

        if (param !is GasLimitApproveParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }

        return functionApproveEvmOf(param.smartContractAddress, param.tokenAmount)
    }
}