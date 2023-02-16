package com.one.web3.ktx.entities


data class Chain(

    var id: Long = 0,
    var name: String = "",

    var logo: String = "",

    var nativeToken: Token? = null,

    var urls: List<Url> = emptyList(),
    var configs: List<Config> = emptyList(),
    var smartContracts: List<SmartContract> = emptyList(),
)

data class Token(
    var name: String = "",
    var symbol: String = "",
    var decimals: Long = 0
)

data class Url(
    var url: String = "",
    var type: String = "",
    var name: String = "",
    var priority: String? = null
)

data class Config(
    var name: String = "",
    var value: String = ""
)

data class SmartContract(
    var address: String = "",
    var type: String = ""
)