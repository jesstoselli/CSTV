package dev.jessto.desafiocstv.ui.matchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jessto.desafiocstv.data.MatchesRepositoryImpl
import dev.jessto.desafiocstv.ui.model.OpponentDTO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MatchDetailsViewModel(
    private val matchesRepositoryImpl: MatchesRepositoryImpl
) : ViewModel() {

    private val _opponentsList = MutableLiveData<List<OpponentDTO>>()
    val opponentsList: LiveData<List<OpponentDTO>>
        get() = _opponentsList


    fun getOpponentsList(matchId: String) {
        viewModelScope.launch {
            matchesRepositoryImpl.getOpponentsList(matchId)

            delay(1000L)
            _opponentsList.postValue(matchesRepositoryImpl.opponentsList.value)
        }
    }
}
