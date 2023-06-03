package com.one.web3.task.bonus.approve

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.functionApproveEvmOf
import com.one.web3.task.bonus.L1FeeEvmCallTask
import org.web3j.abi.datatypes.Function

class L1FeeApproveEvmCallTask : L1FeeEvmCallTask() {

    override suspend fun provideFunction(param: Param): Function {

        if (param !is BonusFeeApproveParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }

        return functionApproveEvmOf(param.smartContractAddress, param.tokenAmount)
    }
}