package dev.jessto.desafiocstv.ui.matchesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jessto.desafiocstv.data.MatchesRepositoryImpl
import dev.jessto.desafiocstv.ui.model.MatchDTO
import kotlinx.coroutines.launch

class MatchesViewModel(
    private val matchesRepositoryImpl: MatchesRepositoryImpl
) : ViewModel() {

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
        viewModelScope.launch {
            _matchesList.postValue(matchesRepositoryImpl.matchesList.value)
        }
    }

    companion object {
        const val TAG = "MatchesViewModel"
    }
}
