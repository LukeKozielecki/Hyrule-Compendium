# One-way Api integration

Hyrule Compendium is an encyclopedia-esque app designed to communicate one-way with an api, downloading data and deserializing it into usable information. 

## Main feature implementation
Implementation of function fetching data from a server
```Kotlin
/**  A public interface that exposes the [getJsonObject] method */
interface CompendiumApiService {  
/** This function gets a JSON object. Inside said object is an array of it's type.
 * Returns a [List] of [CompendiumDataList] and this method can be called from a Coroutine.
 *The @GET annotation indicates that the "all" endpoint will be requested with the GET HTTP method */
  @GET("all")  
  suspend fun getJsonObject(): CompendiumDataList  
}
```
Implementation of Serialization for the json response
```Kotlin
@Serializable  
data class CompendiumDataList(  
    val data: List<CompendiumItem>,  
    @SerialName(value = "message") val message : String? = null,  
    @SerialName(value = "status") val status : Int? = null  
)
@Serializable  
data class CompendiumItem(  
    @SerialName(value = "category") val category: String,  
    @SerialName(value = "common_locations") val common_locations: List<String>?,  
    @SerialName(value = "description") val description: String,  
    @SerialName(value = "dlc") val dlc: Boolean,  
    @SerialName(value = "drops") val drops: List<String>? = null,  
    @SerialName(value = "id") val id: Int,  
    @SerialName(value = "image") val image: String,  
    @SerialName(value = "name") val name: String,  
    val cooking_effect: String? = null,  
    @SerialName(value = "hearts_recovered") val hearts_recovered: Double? = null,  
    @SerialName(value = "properties") val properties: Properties? = null,  
    @SerialName(value = "edible") val edible: Boolean? = null,  
    @SerialName(value = "fuse_attack_power") val fuse_attack_power: Int? = null,  
)
// remaining data classes
```
Implementation of AppContainer instance used by the rest of classes to obtain dependencies
```Kotlin
class CompendiumApplication : Application() {
    lateinit var container: AppContainer  
    override fun onCreate() {  
        super.onCreate()  
        container = DefaultAppContainer()  
    }  
}
```

Implementation of retrofit 
```Kotlin
/**  Dependency Injection container at the application level. */
interface AppContainer {  
    val compendiumRepository: CompendiumRepository  
}  
  
/**  Implementation for the Dependency Injection container at the application level. */
class DefaultAppContainer : AppContainer {  
    private val baseUrl = "https://botw-compendium.herokuapp.com/api/v3/compendium/"  
  
    private val retrofit: Retrofit = Retrofit.Builder()  
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))  
        .baseUrl(baseUrl)  
        .build()  
  
    /**  Retrofit service object for creating api calls     */
	private val retrofitService: CompendiumApiService by lazy {  
        retrofit.create(CompendiumApiService::class.java)  
    }  
  
    /**  DI implementation for [CompendiumDataList] repository     */
    override val compendiumRepository: CompendiumRepository by lazy {  
        NetworkCompendiumRepository(retrofitService)  
    }  
}
```
Implementation of repository
```Kotlin
  
/**  Repository that fetch [CompendiumDataList] list from [CompendiumRepository].  */
 interface CompendiumRepository {  
    /** Fetches list of [CompendiumDataList] from [CompendiumRepository] */ 
    suspend fun getCompendiumItem(): CompendiumDataList  
}  
  
/**  Network Implementation of Repository that fetch [CompendiumDataList] list from [CompendiumRepository].  */
class NetworkCompendiumRepository(  
    private val compendiumApiService: CompendiumApiService  
) : CompendiumRepository {  
    /** Fetches list of [CompendiumDataList] from [CompendiumRepository]*/  
    override suspend fun getCompendiumItem(): CompendiumDataList = compendiumApiService.getJsonObject()  
}

```
## Demonstation

![hyrule_compedium_features](https://github.com/user-attachments/assets/2cc55c42-549b-4bc2-a42f-20cd6d638d08)

