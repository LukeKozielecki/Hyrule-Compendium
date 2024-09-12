package luke.koz.hyrulecompendium.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompendiumMonster(
    @SerialName(value = "name") val name: String, // string; entry name
    @SerialName(value = "id") val id : Int,  // integer; ID as shown in compendium
    @SerialName(value = "category") val category : String,// string; "monsters"
    @SerialName(value = "description") val description: String, // string; short paragraph
    @SerialName(value = "image") val image: String, // string; URL of image
    @SerialName(value = "common_locations") val common_locations: List<String>?, // array of strings or null for unknown; where the entry is commonly seen
    @SerialName(value = "drops") val drops : List<String>?, // array of strings or null for unknown; recoverable materials from killing
    @SerialName(value = "dlc") val dlc : Boolean // boolean; whether the entry is from a DLC pack
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

@Serializable
data class Properties(
    @SerialName(value = "attack")
    val attack : Int? = null,
    @SerialName(value = "defense")
    val defense : Int? = null,
    @SerialName(value = "effect")
    val effect : String? = null,
    @SerialName(value = "type")
    val type : String? = null,
)

@Serializable
data class CompendiumDataList(
    val data: List<CompendiumItem>
)
