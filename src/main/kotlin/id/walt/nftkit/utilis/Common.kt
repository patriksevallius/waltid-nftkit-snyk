package id.walt.nftkit.utilis

import id.walt.nftkit.Values
import id.walt.nftkit.services.*
import org.web3j.protocol.core.methods.response.TransactionReceipt

object Common {

    fun getTransactionResponse(chain: EVMChain, transactionReceipt: TransactionReceipt): TransactionResponse {
        val url = WaltIdServices.getBlockExplorerUrl(chain)
        return TransactionResponse(transactionReceipt.transactionHash, "$url/tx/${transactionReceipt.transactionHash}")
    }


    fun getChain(chain: String): Chain{
        return chain.let {
            if (it.isEmpty()){
                throw Exception("No chain defined")
            }
            Chain.valueOf(it.uppercase())
        }
    }

    fun getTezosChain(chain: String): TezosChain{
        return chain.let {
            if (it.isEmpty()){
                throw Exception("No chain defined")
            }
            TezosChain.valueOf(it.uppercase())
        }
    }

    fun getNearChain(chain: String): NearChain{
        return chain.let {
            if (it.isEmpty()){
                throw Exception("No chain defined")
            }
            NearChain.valueOf(it.uppercase())
        }
    }

    fun getFlowChain(chain: String): FlowChain{ return chain.let {
        if (it.isEmpty()){
            throw Exception("No chain defined")
        }
        FlowChain.valueOf(it.uppercase())
    }}


    fun getUniqueChain(chain: String): UniqueNetwork {

        return chain.let {
            if (it.isEmpty()){
                throw Exception("No chain defined")
            }
            UniqueNetwork.valueOf(it.uppercase())
        }
    }
    fun getEVMChain(chain: String): EVMChain{
        return chain.let {
            if (it.isEmpty()){
                throw Exception("No chain defined")
            }
            EVMChain.valueOf(it.uppercase())
        }
    }

    fun getAlgorandChain(chain: String): AlgorandChain{
        return chain.let {
            if (it.isEmpty()){
                throw Exception("No chain defined")
            }
            AlgorandChain.valueOf(it.uppercase())
        }
    }
    fun getFa2SmartContractType(type: String): Fa2SmartContractType{
        return type.let {
            if (it.isEmpty()){
                throw Exception("No type defined")
            }
            Fa2SmartContractType.valueOf(it.uppercase())
        }
    }

    fun getMetadataType(uri: String): MetadataStorageType {
        if(uri.contains("data:application/json;base64", true)){
            return MetadataStorageType.ON_CHAIN
        }else{
            return MetadataStorageType.OFF_CHAIN
        }
    }

    fun getNetworkBlockExplorerApiUrl(chain: EVMChain): String{
        return when (chain) {
            EVMChain.ETHEREUM -> Values.ETHEREUM_MAINNET_SCAN_API_URL
            EVMChain.GOERLI -> Values.ETHEREUM_TESTNET_GOERLI_SCAN_API_URL
            EVMChain.SEPOLIA -> Values.ETHEREUM_TESTNET_SEPOLIA_SCAN_API_URL
            EVMChain.POLYGON -> Values.POLYGON_MAINNET_SCAN_API_URL
            EVMChain.MUMBAI -> Values.POLYGON_TESTNET_MUMBAI_SCAN_API_URL
            else -> {throw Exception("$chain is not supported")}
        }
    }

    fun getNetworkBlockExplorerApiKey(chain: EVMChain): String{
        return when (chain) {
            EVMChain.ETHEREUM -> WaltIdServices.loadApiKeys().apiKeys.ethereumBlockExplorer
            EVMChain.GOERLI -> WaltIdServices.loadApiKeys().apiKeys.ethereumBlockExplorer
            EVMChain.SEPOLIA -> WaltIdServices.loadApiKeys().apiKeys.ethereumBlockExplorer
            EVMChain.POLYGON -> WaltIdServices.loadApiKeys().apiKeys.polygonBlockExplorer
            EVMChain.MUMBAI -> WaltIdServices.loadApiKeys().apiKeys.polygonBlockExplorer
            EVMChain.SHIMMEREVM -> WaltIdServices.loadApiKeys().apiKeys.shimmerBlockExplorer
            else -> {throw Exception("$chain is not supported")}
        }
    }

    fun isEVMChain(chain: Chain): Boolean{
        val EVMChains= listOf(Chain.ETHEREUM, Chain.POLYGON, Chain.GOERLI, Chain.SEPOLIA, Chain.MUMBAI, Chain.SHIMMEREVM)
        return chain in EVMChains
    }

    fun isTezosChain(chain: Chain): Boolean{
        val TezosChains= listOf(Chain.TEZOS, Chain.GHOSTNET)
        return chain in TezosChains
    }

    fun isNearChain(chain: Chain): Boolean{
        val NearChains= listOf(Chain.MAINNET, Chain.TESTNET)
        return chain in NearChains
    }

    fun isPolkadotParachain(chain: Chain): Boolean =
        chain in listOf(Chain.ASTAR, Chain.MOONBEAM)

    fun isUniqueParachain(chain: Chain): Boolean =
        chain in listOf(Chain.UNIQUE, Chain.OPAL)
    fun isAlgorand(chain: Chain): Boolean =
        chain in listOf(Chain.ALGORAND_MAINNET, Chain.ALGORAND_TESTNET, Chain.ALGORAND_BETANET)

}

