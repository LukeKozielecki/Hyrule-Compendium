package luke.koz.hyrulecompendium.navigation

import kotlinx.serialization.Serializable

/**
 * NavRoutesSerializable class stores Serializable routes utilized inside AppNavigationHost
 */
sealed class NavRoutesSerializable{

    @Serializable
    object CompendiumHubLocation
    @Serializable
    object CompendiumItemScreen

}