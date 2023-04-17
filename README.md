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
   
  * decimal - [example](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/f1da7a9f43515e22c0d5e63bd31675cb05dd651d/app/src/androidTest/java/com/one/web3/ktx/Web3Test.kt#L86)
  * gasPrice
  * balance - [example](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/f1da7a9f43515e22c0d5e63bd31675cb05dd651d/app/src/androidTest/java/com/one/web3/ktx/Web3Test.kt#L146)
  * balanceMulti - [example](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/f1da7a9f43515e22c0d5e63bd31675cb05dd651d/app/src/androidTest/java/com/one/web3/ktx/Web3Test.kt#L146)
  * balanceNative - [example](https://github.com/hoanganhtuan95ptit/web3-ktx/blob/f1da7a9f43515e22c0d5e63bd31675cb05dd651d/app/src/androidTest/java/com/one/web3/ktx/Web3Test.kt#L117)
  
    Functions will be added over time, if you need to add any more please create us "issues"
  
# Architecture


  
# Libary:

  * Web3j
  * Retrofit
  * Jackson
  
