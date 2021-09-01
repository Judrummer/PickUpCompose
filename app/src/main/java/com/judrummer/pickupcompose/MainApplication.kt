package com.judrummer.pickupcompose

import android.app.Application
import com.judrummer.pickupcompose.data.PickUpApi
import com.judrummer.pickupcompose.data.PickUpApiImpl
import com.judrummer.pickupcompose.ui.screen.pickuplist.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    val dataModule = module {
        single<HttpClient> {
            HttpClient(OkHttp) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    })
                }
                if (BuildConfig.DEBUG) install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.ALL
                }
            }
        }
        single<PickUpApi> { PickUpApiImpl(get()) }
    }

    val domainModule = module {
        single<PickUpLocationMapper> { PickUpLocationMapperImpl() }

        single<GetActivePickUpLocationsUsecase> { GetActivePickUpLocationsUsecaseImpl(get(), get()) }
    }

    val appModule = module {
        viewModel { PickUpListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
            modules(dataModule)
            modules(domainModule)
        }
    }
}