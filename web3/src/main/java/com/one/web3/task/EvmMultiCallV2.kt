package com.one.web3.task

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.DynamicBytes
import org.web3j.abi.datatypes.DynamicStruct
import org.web3j.abi.datatypes.Function


interface EvmMultiCallV2 : EvmCall {

    suspend fun call(multiCallAddress: String, staticStruct: List<DynamicStruct>, method: String, rpcUrls: List<String>, sync: Boolean): List<Result> {

        val aggregateFunction = Function(
            method,
            listOf(Bool(false), DynamicArray(DynamicStruct::class.java, staticStruct)),
            listOf(object : TypeReference<DynamicArray<Result>>() {})
        )


        val response = call(METHOD_NAME_ETH_CALL, aggregateFunction, null, multiCallAddress, rpcUrls, sync)


        val dynamicArray = response.getOrNull(0) as? DynamicArray<*> ?: throw RuntimeException("Can't call multi")

        return dynamicArray.value as List<Result>
    }

    class Result(val success: Bool, val returnData: DynamicBytes) : DynamicStruct(success, returnData)
}