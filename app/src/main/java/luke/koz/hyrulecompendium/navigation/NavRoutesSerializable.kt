package luke.koz.hyrulecompendium.navigation

import kotlinx.serialization.Serializable
import luke.koz.hyrulecompendium.model.CompendiumItem

/**
 * NavRoutesSerializable class stores Serializable routes utilized inside AppNavigationHost
 */
sealed class NavRoutesSerializable{

    @Serializable
    object CompendiumHubLocation
    @Serializable
    data class CompendiumSelectedItemScreen (
        val compendiumSelectedItem : CompendiumItem
    )

}