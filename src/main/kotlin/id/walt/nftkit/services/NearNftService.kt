package id.walt.nftkit.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class NearContractMetadata(
    val spec: String, // required, essentially a version like "nft-2.0.0", replacing "2.0.0" with the implemented version of NEP-177
    val name: String, // required, ex. "Mochi Rising — Digital Edition" or "Metaverse 3"
    val symbol: String, // required, ex. "MOCHI"
    val icon: String?= null, // Data URL
    val base_uri: String?= null, // Centralized gateway known to have reliable access to decentralized storage assets referenced by `reference` or `media` URLs
    val reference: String?= null, // URL to a JSON file with more info
    val reference_hash: String?= null, // Base64-encoded sha256 hash of JSON from reference field. Required if `reference` is included.

)

@Serializable
data class NearTokenMetadata(
    val title: String,
    val description: String?= null,
    val media: String,
    val media_hash: String?= null,
    val copies: String?= null,
    val issued_at: String?= null,
    val expires_at: String?= null,
    val starts_at: String?= null,
    val updated_at: String?= null,
    val extra: String?= null,
    val reference: String?= null,
    val reference_hash: String?= null,


)
@Serializable
data class OperationResult(
    val operationHash: String,
    val operationExternalUrl: String
)
@Serializable
data class NearOperationResponse(
    val opHash: String
)

data class NearMintingParameter(
    val receiver_id: String,
    val token_id: String,
    val title: String,
    val description: String?= null,
    val media: String,
    val media_hash: String?= null,
    val reference: String?= null,
    val reference_hash: String?= null,
)


object NearNftService {

    val client = HttpClient(CIO.create{requestTimeout = 0}) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
            // LogLevel.BODY
        }
        expectSuccess = false
    }

    fun mintNftToken(account_id :String,contract_id : String  ,token_id: String , title: String , description: String , media: String , media_hash: String,reference: String , reference_hash: String , receiver_id: String): NearOperationResponse {
        return runBlocking {
            val values = mapOf(
                "account_id" to account_id,
                "contract_id" to contract_id,
                "token_id" to token_id,
                "title" to title,
                "description" to description,
                "media" to media,
                "media_hash" to media_hash,
                "reference" to reference,
                "reference_hash" to reference_hash,
                "receiver_id" to receiver_id
            )
            val nearOperationResponse = NftService.client.post("${WaltIdServices.loadTezosConfig().tezosBackendServer}/near/contract/mintToken") {
                contentType(ContentType.Application.Json)

                setBody(
                    values
                )
            }
                .body<NearOperationResponse>()
            return@runBlocking nearOperationResponse
        }
    }

    fun deployContractDefault(account_id :String): NearOperationResponse {
        return runBlocking {
            val values = mapOf(
                "account_id" to account_id,

            )
            val nearOperationResponse = NftService.client.post("${WaltIdServices.loadTezosConfig().tezosBackendServer}/near/contract/deploywithdefaultmetadata") {
                contentType(ContentType.Application.Json)

                setBody(
                    values
                )
            }
                .body<NearOperationResponse>()
            return@runBlocking nearOperationResponse
        }
    }
}