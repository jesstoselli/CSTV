package dev.jessto.desafiocstv.ui.matchDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jessto.desafiocstv.data.provider.MatchesProviderImpl
import dev.jessto.desafiocstv.ui.ApiStatus
import dev.jessto.desafiocstv.ui.model.OpponentDTO
import kotlinx.coroutines.launch

class MatchDetailsViewModel(
    private val matchesProviderImpl: MatchesProviderImpl
) : ViewModel() {

    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    private val _opponentsList = MutableLiveData<List<OpponentDTO>>()
    val opponentsList: LiveData<List<OpponentDTO>>
        get() = _opponentsList


    fun getOpponentsList(matchId: String) {
        _apiStatus.value = ApiStatus.LOADING

        viewModelScope.launch {
            val listOfOpponents = matchesProviderImpl.getOpponentsList(matchId)

            if (listOfOpponents.isEmpty()) {
                _apiStatus.value = ApiStatus.ERROR
            }

            _opponentsList.postValue(listOfOpponents)

            _apiStatus.value = ApiStatus.SUCCESS
        }
    }
}
