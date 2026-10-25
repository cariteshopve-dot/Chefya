package agency.lacosta.chefya

import android.app.Application
import agency.lacosta.chefya.data.KitchenRepository

class ChefYaApp : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

/** Dependencias de la app, creadas una sola vez. */
class AppContainer(app: Application) {
    val kitchen = KitchenRepository(app)
}
