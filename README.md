# Web3Ktx
This is a library for Android to call data from Node on Ethereum Chain or Solana Chain


# Import

```java

allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
    
    
dependencies {
	...
	implementation 'com.github.hoanganhtuan95ptit:web3-ktx:1.0.1'
}
```

# Chain Support:
  * All EVM chain (https://chainlist.org/)
  * SOL chain

# Function support:

Function | Code Use | Example | Support EVM | Support Solana
--- | --- | ---  | --- | --- 
Allowance | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL87C3-L87C3) | [Example] | ✅ | 
Balance | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L92) | [Example] | ✅ | ✅ 
BalanceMulti | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L97) | [Example] | ✅ | 
BalanceNative | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L102) | [Example] | ✅ | ✅ 
Bonus-Approve (L1 Fee In Optimism) | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL107C17-L107C29) | [Example] | ✅ | 
Bonus-Sign (L1 Fee In Optimism) | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL112C17-L112C26) | [Example] | ✅ | 
Bonus-Transfer (L1 Fee In Optimism And TokenAccount In Solana)| [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL117C17-L117C30) | [Example] | ✅ | ✅  
Decimal | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L122) | [Example] | ✅ | 
DecimalMulti | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L127) | [Example] | ✅ | 
GasLimit-Approve | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L132) | [Example] | ✅ | 
GasLimit-Sign | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L137) | [Example] | ✅ | 
GasLimit-Transfer | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L142) | [Example] | ✅ | 
GasPrice | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L151) | [Example] | ✅ | 
MineNonce | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L156) | [Example] | ✅ | 
PriorityNonce | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L172) | [Example] | ✅ | 
Transaction-Status | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L188) | [Example] | ✅ |  
Transaction-Approve | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L193) | [Example] | ✅ |  
Transaction-Sign | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L214) | [Example] | ✅ |  
Transaction-Transfer | [Code Use](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL233C17-L233C36) | [Example] | ✅ | 
    
    
Functions will be added over time, if you need to add any more please create us "issues"
  
# Architecture


  
# Libary:

  * Web3j
  * Retrofit
  * Jackson
  
