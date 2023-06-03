package com.one.web3

import com.one.coreapp.data.usecase.ResultState
import com.one.task.executeAsyncByFast
import com.one.web3.task.allowance.AllowanceParam
import com.one.web3.task.allowance.TokenAllowanceEvmCallTask
import com.one.web3.task.allowance.TokenAllowanceTask
import com.one.web3.task.balance.BalanceEvmCallTask
import com.one.web3.task.balance.BalanceParam
import com.one.web3.task.balance.BalanceSolCallTask
import com.one.web3.task.balance.BalanceTask
import com.one.web3.task.balancemulti.BalanceMultiEvmCallTask
import com.one.web3.task.balancemulti.BalanceMultiParam
import com.one.web3.task.balancemulti.BalanceMultiTask
import com.one.web3.task.balancenative.BalanceNativeEvmCallTask
import com.one.web3.task.balancenative.BalanceNativeParam
import com.one.web3.task.balancenative.BalanceNativeSolCallTask
import com.one.web3.task.balancenative.BalanceNativeTask
import com.one.web3.task.bonus.BonusFeeTask
import com.one.web3.task.bonus.approve.BonusFeeApproveParam
import com.one.web3.task.bonus.sign.BonusFeeSignParam
import com.one.web3.task.bonus.transfer.BonusFeeTransferParam
import com.one.web3.task.decimal.DecimalCallTask
import com.one.web3.task.decimal.DecimalEvmCallTask
import com.one.web3.task.decimal.DecimalParam
import com.one.web3.task.decimal.DecimalSolCallTask
import com.one.web3.task.decimalmulti.DecimalMultiEvmCallTask
import com.one.web3.task.decimalmulti.DecimalMultiParam
import com.one.web3.task.decimalmulti.DecimalMultiTask
import com.one.web3.task.gaslimit.GasLimitTask
import com.one.web3.task.gaslimit.approve.GasLimitApproveParam
import com.one.web3.task.gaslimit.sign.GasLimitSignParam
import com.one.web3.task.gaslimit.transfer.GasLimitTransferParam
import com.one.web3.task.gasprice.GasPriceCallTask
import com.one.web3.task.gasprice.GasPriceEvmCallTask
import com.one.web3.task.gasprice.GasPriceParam
import com.one.web3.task.gasprice.GasPriceSolCallTask
import com.one.web3.task.minednonce.MinedNonceParam
import com.one.web3.task.minednonce.MinedNonceTask
import com.one.web3.task.pendingnonce.PendingNonceParam
import com.one.web3.task.pendingnonce.PendingNonceTask
import com.one.web3.task.priorityfee.PriorityFeeParam
import com.one.web3.task.priorityfee.PriorityFeeTask
import com.one.web3.task.privatekey.PrivateKeyParam
import com.one.web3.task.privatekey.PrivateKeyTask
import com.one.web3.task.status.TransactionStatusParam
import com.one.web3.task.status.TransactionStatusTask
import com.one.web3.task.transaction.approve.TokenApproveParam
import com.one.web3.task.transaction.approve.TokenApproveTask
import com.one.web3.task.transaction.sign.SignTransactionParam
import com.one.web3.task.transaction.sign.SignTransactionTask
import com.one.web3.task.transaction.transfer.TransferParam
import com.one.web3.task.transaction.transfer.TransferTask
import retrofit2.Retrofit
import java.math.BigDecimal
import java.math.BigInteger

open class Web3(val retrofit: Retrofit, private val tasks: List<Web3Task<*, *>> = emptyList()) {

    val taskList: List<Web3Task<*, *>> by lazy {

        arrayListOf<Web3Task<*, *>>().apply {

            add(TokenAllowanceEvmCallTask())

            add(DecimalMultiEvmCallTask())

            add(BalanceMultiEvmCallTask())

            add(BalanceEvmCallTask())
            add(BalanceSolCallTask())

            add(BalanceNativeEvmCallTask())
            add(BalanceNativeSolCallTask())

            add(DecimalEvmCallTask())
            add(DecimalSolCallTask())

            add(GasPriceEvmCallTask())
            add(GasPriceSolCallTask())

            addAll(tasks)
        }
    }


    suspend fun allowance(tokenAddress: String, walletAddress: String, contractAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigDecimal {

        return execute<AllowanceParam, BigDecimal, TokenAllowanceTask>(AllowanceParam(tokenAddress, walletAddress, contractAddress, chainId, rpcUrls, sync))
    }

    suspend fun balance(tokenAddress: String, walletAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigDecimal {

        return execute<BalanceParam, BigDecimal, BalanceTask>(BalanceParam(tokenAddress, walletAddress, chainId, rpcUrls, sync))
    }

    suspend fun balanceMulti(tokenAddressList: List<String>, walletAddressList: List<String>, multiCallAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): Map<Pair<String, String>, BigDecimal> {

        return execute<BalanceMultiParam, Map<Pair<String, String>, BigDecimal>, BalanceMultiTask>(BalanceMultiParam(tokenAddressList, walletAddressList, multiCallAddress, chainId, rpcUrls, sync))
    }

    suspend fun balanceNative(walletAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigInteger {

        return execute<BalanceNativeParam, BigInteger, BalanceNativeTask>(BalanceNativeParam(walletAddress, chainId, rpcUrls, sync))
    }

    suspend fun bonusApprove(smartContractAddress: String, tokenAmount: BigInteger, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigDecimal {

        return execute<Param, BigDecimal, BonusFeeTask>(BonusFeeApproveParam(smartContractAddress, tokenAmount, chainId, rpcUrls, sync))
    }

    suspend fun bonusSign(data: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigDecimal {

        return execute<Param, BigDecimal, BonusFeeTask>(BonusFeeSignParam(data, chainId, rpcUrls, sync))
    }

    suspend fun bonusTransfer(walletAddress: String, receiverAddress: String, tokenId: BigInteger, tokenAmount: BigInteger, tokenAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigDecimal {

        return execute<Param, BigDecimal, BonusFeeTask>(BonusFeeTransferParam(walletAddress, receiverAddress, tokenId, tokenAmount, tokenAddress, chainId, rpcUrls, sync))
    }

    suspend fun decimal(tokenAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): Int {

        return execute<DecimalParam, Int, DecimalCallTask>(DecimalParam(tokenAddress, chainId, rpcUrls, sync))
    }

    suspend fun decimalMulti(tokenAddressList: List<String>, multiCallAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): Map<String, Int> {

        return execute<DecimalMultiParam, Map<String, Int>, DecimalMultiTask>(DecimalMultiParam(tokenAddressList, multiCallAddress, chainId, rpcUrls, sync))
    }

    suspend fun gasLimitApprove(walletAddress: String, smartContractAddress: String, tokenAmount: BigInteger, tokenAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigInteger {

        return execute<Param, BigInteger, GasLimitTask>(GasLimitApproveParam(walletAddress, smartContractAddress, tokenAmount, tokenAddress, chainId, rpcUrls, sync))
    }

    suspend fun gasLimitSign(to: String, from: String, data: String, value: BigInteger, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigInteger {

        return execute<Param, BigInteger, GasLimitTask>(GasLimitSignParam(to, from, data, value, chainId, rpcUrls, sync))
    }

    suspend fun gasLimitTransfer(
        walletAddress: String, receiverAddress: String,
        tokenId: BigInteger? = null, tokenAmount: BigInteger, tokenAddress: String, isNativeToken: Boolean,
        chainId: Long, rpcUrls: List<String>, sync: Boolean = false
    ): BigInteger {

        return execute<Param, BigInteger, GasLimitTask>(GasLimitTransferParam(walletAddress, receiverAddress, tokenId, tokenAmount, tokenAddress, isNativeToken, chainId, rpcUrls, sync))
    }

    suspend fun gasPrice(chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigInteger {

        return execute<GasPriceParam, BigInteger, GasPriceCallTask>(GasPriceParam(chainId, rpcUrls, sync))
    }

    suspend fun mineNonce(walletAddress: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigInteger {

        return execute<MinedNonceParam, BigInteger, MinedNonceTask>(MinedNonceParam(walletAddress, chainId, rpcUrls, sync))
    }

    suspend fun getPendingNonce(chainId: Long, walletAddress: String): BigInteger {

        val list = taskList.filterIsInstance<PendingNonceTask>()

        if (list.isEmpty()) {
            error("need override PendingNonceTask")
        }

        return execute<PendingNonceParam, BigInteger, PendingNonceTask>(PendingNonceParam(walletAddress, chainId))
    }

    suspend fun priorityNonce(chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigDecimal {

        return execute<PriorityFeeParam, BigDecimal, PriorityFeeTask>(PriorityFeeParam(chainId, rpcUrls, sync))
    }

    suspend fun getPrivateKey(walletAddress: String): String {

        val list = taskList.filterIsInstance<PrivateKeyTask>()

        if (list.isEmpty()) {
            error("need override PrivateKeyTask")
        }

        return execute<PrivateKeyParam, String, PrivateKeyTask>(PrivateKeyParam(walletAddress))
    }

    suspend fun status(txHash: String, chainId: Long, rpcUrls: List<String>, sync: Boolean = false): BigInteger {

        return execute<TransactionStatusParam, BigInteger, TransactionStatusTask>(TransactionStatusParam(txHash, chainId, rpcUrls, sync))
    }

    suspend fun transactionApprove(
        walletAddress: String, smartContractAddress: String,
        tokenAmount: BigInteger, tokenAddress: String,
        to: String, from: String,
        data: String, value: BigInteger,
        nonce: BigInteger?, gasLimit: BigInteger, gasPrice: BigDecimal, priorityFee: BigDecimal,
        chainId: Long, rpcUrls: List<String>, sync: Boolean = false
    ): Pair<String, BigInteger> {

        return execute<TokenApproveParam, Pair<String, BigInteger>, TokenApproveTask>(
            TokenApproveParam(
                walletAddress, smartContractAddress,
                tokenAmount, tokenAddress,
                to, from,
                data, value,
                nonce, gasLimit, gasPrice, priorityFee,
                chainId, rpcUrls, sync
            )
        )
    }

    suspend fun transactionSign(
        to: String, from: String,
        data: String, value: BigInteger,
        nonce: BigInteger?, gasLimit: BigInteger, gasPrice: BigDecimal, priorityFee: BigDecimal,
        isFromDApp: Boolean,
        chainId: Long, rpcUrls: List<String>, sync: Boolean = false
    ): Pair<String, BigInteger> {

        return execute<SignTransactionParam, Pair<String, BigInteger>, SignTransactionTask>(
            SignTransactionParam(
                to, from,
                data, value,
                nonce, gasLimit, gasPrice, priorityFee,
                isFromDApp,
                chainId, rpcUrls, sync
            )
        )
    }

    suspend fun transactionTransfer(
        walletAddress: String, receiverAddress: String,
        tokenId: BigInteger? = null, tokenLogo: String, tokenAmount: BigInteger, tokenSymbol: String, tokenDecimal: Int, tokenAddress: String,
        isTokenNative: Boolean,
        nonce: BigInteger?, gasLimit: BigInteger, gasPrice: BigDecimal, priorityFee: BigDecimal,
        chainId: Long, rpcUrls: List<String>, sync: Boolean = false
    ): Pair<String, BigInteger> {

        return execute<TransferParam, Pair<String, BigInteger>, TransferTask>(
            TransferParam(
                walletAddress, receiverAddress, tokenId, tokenLogo, tokenAmount, tokenSymbol, tokenDecimal, tokenAddress, isTokenNative,
                nonce, gasLimit, gasPrice, priorityFee,
                chainId, rpcUrls, sync
            )
        )
    }

    suspend inline fun <Param : com.one.web3.Param, Result, reified T : Web3Task<Param, Result>> execute(param: Param): Result {

        val state = taskList.filterIsInstance<T>().executeAsyncByFast(param)

        if (state is ResultState.Failed) {

            throw state.error
        }

        if (state is ResultState.Success) {

            return state.data
        } else {

            error("not found")
        }
    }

    companion object {

        private var instance: Web3? = null

        fun init(retrofit: Retrofit, tasks: List<Web3Task<*, *>> = emptyList()) {

            instance = Web3(retrofit, tasks)
        }

        fun instance(): Web3 {

            return instance!!
        }
    }
}