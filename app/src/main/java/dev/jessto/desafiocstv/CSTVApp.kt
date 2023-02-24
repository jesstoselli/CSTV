package dev.jessto.desafiocstv

import android.app.Application
import dev.jessto.desafiocstv.data.CSTVApiService
import dev.jessto.desafiocstv.data.MatchesRepository
import dev.jessto.desafiocstv.data.MatchesRepositoryImpl
import dev.jessto.desafiocstv.data.ServiceHelpers.createOkHttpClient
import dev.jessto.desafiocstv.data.ServiceHelpers.createService
import dev.jessto.desafiocstv.ui.matchesList.MatchesViewModel
import dev.jessto.desafiocstv.utils.mappers.MatchDTOMapper
import dev.jessto.desafiocstv.utils.mappers.OpponentsDTOMapper
import dev.jessto.desafiocstv.utils.mappers.PlayersDTOMapper
import dev.jessto.desafiocstv.utils.mappers.TeamDTOMapper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CSTVApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val mappersModule = module {
            factory { MatchDTOMapper(get()) }
            factory { TeamDTOMapper() }
            factory { OpponentsDTOMapper(get()) }
            factory { PlayersDTOMapper() }
        }

        val viewModelModule = module {
            viewModel { MatchesViewModel(get()) }
        }

        val dataModule = module {
            single<MatchesRepository> { MatchesRepositoryImpl(get(), get(), get()) }
        }

        val networkModule = module {
            single { createOkHttpClient() }

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
    }
}
