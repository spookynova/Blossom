package id.blossom

import android.app.Application
import id.blossom.di.component.ApplicationComponent
import id.blossom.di.component.DaggerApplicationComponent
import id.blossom.di.module.ApplicationModule

class BlossomApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}