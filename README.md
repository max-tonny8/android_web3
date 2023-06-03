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
	implementation 'com.github.hoanganhtuan95ptit:web3-ktx:$last-release'
}
```

# Chain Support:
  * All EVM chain (https://chainlist.org/)
  * SOL chain

# Function support:

Function | Example | Support EVM | Support Solana
--- | ---  | --- | --- 
[Allowance](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL87C3-L87C3) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/allowance/TokenAllowanceEvmCallTask.kt) | 
[Balance](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L92) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/balance/BalanceEvmCallTask.kt)| ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/balance/BalanceEvmCallTask.kt) 
[Balance-Multi](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L97) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/balancemulti/BalanceMultiEvmCallTask.kt)| 
[BalanceNative](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L102) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/balancenative/BalanceNativeEvmCallTask.kt)| ✅[Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/balancenative/BalanceNativeSolCallTask.kt)
[Bonus-Approve (L1 Fee In Optimism)](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL107C17-L107C29) | [Example] | ✅ [L1 Fee](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/bonus/approve/L1FeeApproveEvmCallTask.kt)| 
[Bonus-Sign (L1 Fee In Optimism)](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL112C17-L112C26) | [Example] | ✅ [L1 Fee](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/bonus/sign/L1FeeSignEvmCallTask.kt)| 
[Bonus-Transfer (L1 Fee In Optimism And TokenAccount In Solana)](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL117C17-L117C30) | [Example] | ✅ [L1 Fee](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/bonus/transfer/L1FeeTransferEvmCallTask.kt)| ✅ [Create Token Account](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/bonus/transfer/MintBalanceForRentExemptionFeeTransferSolCallTask.kt)
[Decimal](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L122) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/decimal/DecimalEvmCallTask.kt)| ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/decimal/DecimalSolCallTask.kt)
[Decimal-Multi](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L127) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/decimalmulti/DecimalMultiEvmCallTask.kt)| 
[GasLimit-Approve](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L132) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/gaslimit/approve/GasLimitApproveEvmCallTask.kt)| 
[GasLimit-Sign](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L137) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/gaslimit/sign/GasLimitSignEvmCallTask.kt)| 
[GasLimit-Transfer](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L142) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/gaslimit/transfer/GasLimitTransferEvmCallTask.kt)| 
[GasPrice](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L151) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/gasprice/GasPriceEvmCallTask.kt)| 
[MineNonce](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L156) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/minednonce/MinedNonceEvmCallTask.kt)| 
[PriorityNonce](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L172) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/priorityfee/PriorityFeeEvmCallTask.kt)| 
[Transaction-Status](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L188) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/status/TransactionStatusEvmCallTask.kt)| ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/status/TransactionStatusSolCallTask.kt)
[Transaction-Approve](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L193) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/transaction/approve/TokenApproveEvmCallTask.kt)|  
[Transaction-Sign](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#L214) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/transaction/sign/SignTransactionEvmCallTask.kt)|  
[Transaction-Transfer](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/17086f6cf822a43994801fe353295588f2745955/web3/src/main/java/com/one/web3/Web3.kt#LL233C17-L233C36) | [Example] | ✅ [Code Handle](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/main/web3/src/main/java/com/one/web3/task/transaction/transfer/TransferEvmTask.kt)| 
    
    
Functions will be added over time, if you need to add any more please create us "issues"
  
# Architecture


  
# Libary:

  * Web3j
  * Retrofit
  * Jackson
  
