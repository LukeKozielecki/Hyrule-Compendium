package luke.koz.hyrulecompendium.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import luke.koz.hyrulecompendium.model.CompendiumDataList
import retrofit2.http.GET

/**
 * A public interface that exposes the [getJsonObject] method
 */
interface CompendiumApiService {
    /**
     * This function gets a JSON object. Inside said object is an array of it's type.
     * Returns a [List] of [CompendiumDataList] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "all" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("all")
    suspend fun getJsonObject(): CompendiumDataList
}

/*

@Serializable
data class MockEvent(
    val id: Int,
    val name: String
)

@Serializable
data class MockEventsResponse(
    val events: List<MockEvent>
)

fun main() {

    // Simulated JSON response as a string
    val jsonString = """{
        "events": [
            {
                "id": 1,
                "name": "Name1"
            },
            {
                "id": 2,
                "name": "Name2"
            }
        ]
    }"""

    // Kotlinx Serialization used to decode the JSON string
    val eventsResponse: MockEventsResponse = Json.decodeFromString(jsonString)

    // Events are stored and accessible when needed
    val eventsList: List<MockEvent> = eventsResponse.events

    // Example use: Print the IDs of the events
    eventsList.forEach { event ->
        println("Event ID: ${event.id}")
        println("Event ID: ${event.name}")
    }

}

*/
