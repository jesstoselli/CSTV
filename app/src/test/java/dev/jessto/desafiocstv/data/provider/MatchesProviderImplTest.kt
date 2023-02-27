package dev.jessto.desafiocstv.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.jessto.desafiocstv.data.networkmodel.League
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.networkmodel.Opponent
import dev.jessto.desafiocstv.data.networkmodel.OpponentTeams
import dev.jessto.desafiocstv.data.networkmodel.OpponentsResponse
import dev.jessto.desafiocstv.data.networkmodel.PlayerResponse
import dev.jessto.desafiocstv.data.networkmodel.Series
import dev.jessto.desafiocstv.data.repository.MatchesRepositoryImpl
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.OpponentDTO
import dev.jessto.desafiocstv.ui.model.PlayerDTO
import dev.jessto.desafiocstv.ui.model.TeamDTO
import dev.jessto.desafiocstv.utils.mappers.MatchDTOMapper
import dev.jessto.desafiocstv.utils.mappers.OpponentsDTOMapper
import dev.jessto.desafiocstv.utils.mappers.PlayersDTOMapper
import dev.jessto.desafiocstv.utils.mappers.TeamDTOMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class MatchesProviderImplTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var matchesRepositoryImpl: MatchesRepositoryImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val matchesList = listOf(
        MatchResponse(
            beginAt = Date(),
            id = 735517,
            league = League(
                id = 4734,
                imageUrl = null,
                name = "ESL Challenger League"
            ),
            opponentTeams = listOf(
                OpponentTeams(
                    Opponent(
                        opponentImg = null,
                        name = "Villainous"
                    )
                ),
                OpponentTeams(
                    Opponent(
                        opponentImg = null,
                        name = "Villainous"
                    )
                )
            ),
            series = Series(
                name = "North America"
            ),
            status = "running"
        ),
        MatchResponse(
            beginAt = Date(),
            id = 734737,
            league = League(
                id = 4491,
                imageUrl = null,
                name = "5E"
            ),
            opponentTeams = listOf(
                OpponentTeams(
                    Opponent(
                        opponentImg = null,
                        name = "Lynn Vision"
                    )
                ),
                OpponentTeams(
                    Opponent(
                        opponentImg = null,
                        name = "TYLOO"
                    )
                )
            ),
            series = Series(
                name = "Arena Asia Cup"
            ),
            status = "not_started"
        ),
        MatchResponse(
            beginAt = Date(),
            id = 734738,
            league = League(
                id = 4491,
                imageUrl = null,
                name = "5E"
            ),
            opponentTeams = listOf(
                OpponentTeams(
                    Opponent(
                        opponentImg = null,
                        name = "Renewal"
                    )
                ),
                OpponentTeams(
                    Opponent(
                        opponentImg = null,
                        name = "Rare Atom"
                    )
                )
            ),
            series = Series(
                name = "Arena Asia Cup"
            ),
            status = "running"
        )
    )

    private val opponentsList = listOf(
        OpponentsResponse(
            id = 129240,
            teamName = "Villainous",
            teamBadgeImg = "https://cdn.pandascore.co/images/team/image/129240/227px_villainous__american_organization__allmode.png",
            players = listOf(
                PlayerResponse(
                    firstName = "Gabriel",
                    name = "tENSKI",
                    lastName = "Rodrigue",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Alvin",
                    name = "Alvin",
                    lastName = "Bui",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Sam",
                    name = "Noxio",
                    lastName = "Goodwin",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Brody",
                    name = "BeaKie",
                    lastName = "Kelly",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Cory",
                    name = "shutout",
                    lastName = "Frymark",
                    playerImg = null
                ),
            )
        ),
        OpponentsResponse(
            id = 125859,
            teamName = "ATK",
            teamBadgeImg = "https://cdn.pandascore.co/images/team/image/125859/800px_atk_2020_infobox.png",
            players = listOf(
                PlayerResponse(
                    firstName = "Rhys",
                    name = "Fadey",
                    lastName = "Armstrong",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Gareth",
                    name = "MisterM",
                    lastName = "Ries",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Ian",
                    name = "motm",
                    lastName = "Hardy",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Michael",
                    name = "Swisher",
                    lastName = "Schmidt",
                    playerImg = null
                ),
                PlayerResponse(
                    firstName = "Jonathan",
                    name = "djay",
                    lastName = "Dallal",
                    playerImg = null
                ),
            )
        )
    )

    private val matchesDTOList = arrayListOf(
        MatchDTO(
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
        ),
        MatchDTO(
            scheduledTime = Date(),
            id = 734737,
            leagueName = "5E",
            leagueImg = null,
            teams = listOf(
                TeamDTO(
                    badgeImg = null,
                    name = "Lynn Vision"
                ),
                TeamDTO(
                    badgeImg = null,
                    name = "TYLOO"
                )
            ),
            series = "Arena Asia Cup",
            status = "not_started"
        ),
        MatchDTO(
            scheduledTime = Date(),
            id = 734738,
            leagueName = "5E",
            leagueImg = null,
            teams = listOf(
                TeamDTO(
                    badgeImg = null,
                    name = "Renewal"
                ),
                TeamDTO(
                    badgeImg = null,
                    name = "Rare Atom"
                )
            ),
            series = "Arena Asia Cup",
            status = "running"
        )
    )

    private val opponentsDTOList = arrayListOf(
        OpponentDTO(
            id = 129240,
            teamName = "Villainous",
            teamBadgeImg = null,
            players = listOf(
                PlayerDTO(
                    name = "Gabriel Rodrigue",
                    nickname = "tENSKI",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Alvin Bui",
                    nickname = "Alvin",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Sam Goodwin",
                    nickname = "Noxio",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Brody Kelly",
                    nickname = "BeaKie",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Cory Frymark",
                    nickname = "shutout",
                    playerImg = null
                ),
            )
        ),
        OpponentDTO(
            id = 125859,
            teamName = "ATK",
            teamBadgeImg = null,
            players = listOf(
                PlayerDTO(
                    name = "Rhys Armstrong",
                    nickname = "Fadey",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Gareth Ries",
                    nickname = "MisterM",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Ian Hardy",
                    nickname = "motm",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Michael Schmidt",
                    nickname = "Swisher",
                    playerImg = null
                ),
                PlayerDTO(
                    name = "Jonathan Dallal",
                    nickname = "djay",
                    playerImg = null
                ),
            )
        )
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
    fun getMatchesList_successful(): Unit = runBlocking {

        launch(Dispatchers.Main) {
            // Given
            val teamDTOMapper = TeamDTOMapper()
            val matchDTOMapper = MatchDTOMapper(teamDTOMapper)
            val playersDTOMapper = PlayersDTOMapper()
            val opponentsDTOMapper = OpponentsDTOMapper(playersDTOMapper)
            val matchesProviderImpl = MatchesProviderImpl(matchesRepositoryImpl, matchDTOMapper, opponentsDTOMapper)

            coEvery { matchesRepositoryImpl.getMatchesList() } returns matchesList

            // When
            val result = matchesProviderImpl.getMatchesList()

            // Then
            coVerify(exactly = 1) { matchesRepositoryImpl.getMatchesList() }
//            assertEquals(matchesDTOList, result)
            assertEquals(matchesDTOList.first().id, result.first().id)
        }
    }

    @Test
    fun getOpponentsList_successful(): Unit = runBlocking {

        launch(Dispatchers.Main) {
            // Given
            val matchId = "342"
            val teamDTOMapper = TeamDTOMapper()
            val matchDTOMapper = MatchDTOMapper(teamDTOMapper)
            val playersDTOMapper = PlayersDTOMapper()
            val opponentsDTOMapper = OpponentsDTOMapper(playersDTOMapper)
            val matchesProviderImpl = MatchesProviderImpl(matchesRepositoryImpl, matchDTOMapper, opponentsDTOMapper)

            coEvery { matchesRepositoryImpl.getOpponentsList(matchId) } returns opponentsList

            // When
            val result = matchesProviderImpl.getOpponentsList(matchId)

            // Then
            coVerify(exactly = 1) { matchesRepositoryImpl.getOpponentsList(matchId) }
//            assertEquals(opponentsList, result)
            assertEquals(opponentsDTOList.first().id, result.first().id)
        }
    }

}

