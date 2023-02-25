package dev.jessto.desafiocstv.ui.matchesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jessto.desafiocstv.data.MatchesProviderImpl
import dev.jessto.desafiocstv.ui.ApiStatus
import dev.jessto.desafiocstv.ui.model.MatchDTO
import kotlinx.coroutines.launch

class MatchesListViewModel(
    private val matchesProviderImpl: MatchesProviderImpl
) : ViewModel() {

    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    private val _responseErrorMessage = MutableLiveData<String>()
    val responseErrorMessage: LiveData<String>
        get() = _responseErrorMessage

    private val _matchesList = MutableLiveData<List<MatchDTO>>()
    val matchesList: LiveData<List<MatchDTO>>
        get() = _matchesList

    private val _navigateToMatchDetails = MutableLiveData<MatchDTO?>()
    val navigateToMatchDetails: LiveData<MatchDTO?>
        get() = _navigateToMatchDetails

    init {
        Log.d(TAG, "Loaded properly")
        getMatchesList()
    }

    fun navigateToMatchDetails(match: MatchDTO) {
        _navigateToMatchDetails.postValue(match)
    }

    fun returnFromMatchDetails() {
        _navigateToMatchDetails.postValue(null)
    }

    fun getMatchesList() {
        _apiStatus.value = ApiStatus.LOADING

        viewModelScope.launch {
            val listOfMatches = matchesProviderImpl.getMatchesList()

            if (listOfMatches.isEmpty()) {
                _apiStatus.value = ApiStatus.ERROR
            }

            _matchesList.postValue(listOfMatches)

            _apiStatus.value = ApiStatus.SUCCESS
        }
    }

    companion object {
        const val TAG = "MatchesViewModel"
    }
}
