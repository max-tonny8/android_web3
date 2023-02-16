package com.one.web3.task

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function


interface EvmMultiCallV2 : EvmCall {

    suspend fun readMultiCall(multiCallAddress: String, staticStruct: List<DynamicStruct>, rpcUrls: List<String>): List<Result> {

        val aggregateFunction = Function(
            "tryAggregate",
            listOf(Bool(false), DynamicArray(DynamicStruct::class.java, staticStruct)),
            listOf(object : TypeReference<DynamicArray<Result>>() {})
        )


        val response = read(METHOD_NAME_ETH_CALL, aggregateFunction, null, multiCallAddress, rpcUrls)


        val dynamicArray = response.getOrNull(0) as? DynamicArray<*> ?: throw RuntimeException("Can't call multi")

        return dynamicArray.value as List<Result>
    }

    class Result(val success: Bool, val returnData: DynamicBytes) : DynamicStruct(success, returnData)
}