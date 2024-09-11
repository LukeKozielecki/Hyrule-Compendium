package luke.koz.hyrulecompendium.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import luke.koz.hyrulecompendium.CompendiumApplication
import luke.koz.hyrulecompendium.data.CompendiumRepository
import luke.koz.hyrulecompendium.model.CompendiumDataList
import luke.koz.hyrulecompendium.model.CompendiumItem
import retrofit2.HttpException
import java.io.IOException

/**
 * Defined UI states for [HomeScreen]
 */
sealed interface CompendiumUiState {
    data class Success(val compendiumDataList: CompendiumDataList) : CompendiumUiState
    object Error : CompendiumUiState
    object Loading : CompendiumUiState
}

class CompendiumViewModel(private val compendiumItemRepository: CompendiumRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var compendiumUiState: CompendiumUiState by mutableStateOf(CompendiumUiState.Loading)
        private set

    init {
        getCompendiumItem()
    }

    /**
     * Gets [CompendiumDataList] information from the Retrofit [CompendiumApiService] and updates the
     * [CompendiumDataList].
     */
    fun getCompendiumItem() {
        viewModelScope.launch {
            compendiumUiState = CompendiumUiState.Loading
            compendiumUiState = try {
                CompendiumUiState.Success(compendiumItemRepository.getCompendiumItem())
            } catch (e: IOException) {
                CompendiumUiState.Error
            } catch (e: HttpException) {
                CompendiumUiState.Error
            }
        }
    }

    fun filterData(dataList: List<CompendiumItem>, filterTerm : String) : List<CompendiumItem> {
        return dataList.filter { item -> item.category == filterTerm }
    }

    /**
     * Factory for [CompendiumViewModel] that takes [CompendiumRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CompendiumApplication)
                val compendiumRepository = application.container.compendiumRepository
                CompendiumViewModel(compendiumItemRepository = compendiumRepository)
            }
        }
    }
}

