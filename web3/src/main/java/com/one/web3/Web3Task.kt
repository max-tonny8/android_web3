package com.one.web3

import com.one.coreapp.data.usecase.ResultState
import com.one.task.Task

interface Web3Task<Param : com.one.web3.Param, Result> : Task<Param, Result> {

    override suspend fun execute(param: Param): ResultState<Result> {

        return runCatching {

            if (this is Web3Call) {

                this.checkSupportChain(param.chainId)
            }

            ResultState.Success(executeTask(param))
        }.getOrElse {

            ResultState.Failed(it)
        }
    }
}

