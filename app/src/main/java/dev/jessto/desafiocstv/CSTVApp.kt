package dev.jessto.desafiocstv

import android.app.Application
import dev.jessto.desafiocstv.data.CSTVApiService
import dev.jessto.desafiocstv.data.MatchesRepository
import dev.jessto.desafiocstv.data.MatchesRepositoryImpl
import dev.jessto.desafiocstv.data.ServiceHelpers.createOkHttpClient
import dev.jessto.desafiocstv.data.ServiceHelpers.createService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CSTVApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val mappersModule = module {
//            factory { WordEntityMapper(get()) }
        }

        val viewModelModule = module {
//            viewModel { DictionaryViewModel(get()) }
        }

        val dataModule = module {
            single<MatchesRepository> { MatchesRepositoryImpl(get()) }
        }

        val networkModule = module {
            single { createOkHttpClient(OK_HTTP) }

            single { createService<CSTVApiService>(get(), BASE_URL) }
        }


        startKoin {
            androidLogger()
            androidContext(this@CSTVApp)
            modules(
                listOf(
                    mappersModule,
                    viewModelModule,
                    dataModule,
                    networkModule
                )
            )
        }
    }

    companion object {
        private const val BASE_URL = "https://api.pandascore.co/"
        private const val OK_HTTP = "Ok Http"
    }
}
