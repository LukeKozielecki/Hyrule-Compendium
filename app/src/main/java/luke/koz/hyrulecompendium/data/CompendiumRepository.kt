package luke.koz.hyrulecompendium.data

import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.network.CompendiumApiService

/**
 * Repository that fetch [CompendiumDataList] list from [CompendiumRepository].
 */
interface CompendiumRepository {
    /** Fetches list of [CompendiumDataList] from [CompendiumRepository] */
    suspend fun getCompendiumItem(): CompendiumDataList
}

/**
 * Network Implementation of Repository that fetch [CompendiumDataList] list from [CompendiumRepository].
 */
class NetworkCompendiumRepository(
    private val compendiumApiService: CompendiumApiService
) : CompendiumRepository {
    /** Fetches list of [CompendiumDataList] from [CompendiumRepository]*/
    override suspend fun getCompendiumItem(): CompendiumDataList = compendiumApiService.getJsonObject()
}
