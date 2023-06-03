package com.one.web3.task.bonus.sign

import com.one.task.LowException
import com.one.web3.Param
import com.one.web3.task.functionApproveEvmOf
import com.one.web3.task.bonus.L1FeeEvmCallTask
import org.web3j.abi.datatypes.Function

class L1FeeSignEvmCallTask : L1FeeEvmCallTask() {

    override suspend fun provideData(param: Param): String {

        if (param !is BonusFeeSignParam) {

            throw LowException("${this.javaClass.simpleName} not support")
        }

        return param.data
    }
}