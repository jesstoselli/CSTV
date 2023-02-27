package dev.jessto.desafiocstv.ui.matchesList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.jessto.desafiocstv.data.provider.MatchesProviderImpl
import dev.jessto.desafiocstv.getOrAwaitValue
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.TeamDTO
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.stopKoin
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MatchesListViewModelTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var matchesProviderImpl: MatchesProviderImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val matchDTO = MatchDTO(
        scheduledTime = Date(),
        id = 735517,
        leagueName = "ESL Challenger League",
        leagueImg = null,
        series = "North America",
        teams = listOf(
            TeamDTO(
                name = "Villainous",
                badgeImg = null
            ),
            TeamDTO(
                name = "Villainous",
                badgeImg = null
            )
        ),
        status = "running"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Before
    fun baseSetup() {
        MockKAnnotations.init(this)
        stopKoin()
    }

    @Test
    fun testNavigateToMatchDetails(): Unit = runBlocking {

        launch(Dispatchers.Main) {
            // Given
           val matchesListViewModel = MatchesListViewModel(matchesProviderImpl)

            // When
            matchesListViewModel.navigateToMatchDetails(matchDTO)

            // Then
           assertEquals(matchDTO, matchesListViewModel.navigateToMatchDetails.getOrAwaitValue())
        }
    }

    @Test
    fun testReturnFromMatchDetails(): Unit = runBlocking {

        launch(Dispatchers.Main) {
            // Given
            val matchesListViewModel = MatchesListViewModel(matchesProviderImpl)

            // When
            matchesListViewModel.returnFromMatchDetails()

            // Then
            assertEquals(null, matchesListViewModel.navigateToMatchDetails.getOrAwaitValue())
        }
    }

}
