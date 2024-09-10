package luke.koz.hyrulecompendium.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import luke.koz.hyrulecompendium.network.CompendiumApiService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val compendiumRepository: CompendiumRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://botw-compendium.herokuapp.com/api/v3/compendium/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: CompendiumApiService by lazy {
        retrofit.create(CompendiumApiService::class.java)
    }

    /**
     * DI implementation for [CompendiumDataList] repository
     */
    override val compendiumRepository: CompendiumRepository by lazy {
        NetworkCompendiumRepository(retrofitService)
    }
}
