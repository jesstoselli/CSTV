package dev.jessto.desafiocstv

import android.app.Application
import dev.jessto.desafiocstv.data.provider.MatchesProviderImpl
import dev.jessto.desafiocstv.data.repository.MatchesRepositoryImpl
import dev.jessto.desafiocstv.ui.matchDetails.MatchDetailsViewModel
import dev.jessto.desafiocstv.ui.matchesList.MatchesListViewModel
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
            viewModel { MatchesListViewModel(get()) }
            viewModel { MatchDetailsViewModel(get()) }
        }

        val dataModule = module {
            single { MatchesProviderImpl(get(), get(), get()) }
            single { MatchesRepositoryImpl() }
        }

        startKoin {
            androidLogger()
            androidContext(this@CSTVApp)
            modules(
                listOf(
                    mappersModule,
                    dataModule,
                    viewModelModule
                )
            )
        }
    }
}
