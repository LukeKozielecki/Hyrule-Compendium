package luke.koz.hyrulecompendium

import android.app.Application
import luke.koz.hyrulecompendium.data.AppContainer
import luke.koz.hyrulecompendium.data.DefaultAppContainer

class CompendiumApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}