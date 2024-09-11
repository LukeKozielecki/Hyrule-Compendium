package luke.koz.hyrulecompendium.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    /**
     * [currentFilterCategory] is a private variable meant to be used with [updateFilterTerm] function,
     * to select category of displayed compendium items. It filters **only** preset
     * [CompendiumItem.category]
     */
    private val _currentFilterCategory = MutableStateFlow("any")
    //todo [currentFilterCategory]
    //  is accessed both by textField and buttons. separate && create _currentSearchKeyPhrase
    /**
     * [selectedCategoryOfInputSearch] is a private variable meant to be used with [filterData] function,
     * to select category of inquiry for user input. If they wish to search by:
     * [CompendiumItem.category], [CompendiumItem.common_locations], etc.
     */
    private val _selectedCategoryOfInputSearch = MutableStateFlow("any")
    private val _sortDirectionAscending = MutableStateFlow(true)
    private val _filteredData = MutableStateFlow<List<CompendiumItem>>(listOf<CompendiumItem>())

    /**
     * [currentFilterCategory] is an exposed variable meant to be used with [updateFilterTerm] function,
     * to select category of displayed compendium items. It filters **only** preset
     * [CompendiumItem.category]
     */
    val currentFilterCategory: StateFlow<String> = _currentFilterCategory
    /**
     * [selectedCategoryOfInputSearch] is an exposed variable meant to be used with [filterData] function,
     * to select category of inquiry for user input. If they wish to search by:
     * [CompendiumItem.category], [CompendiumItem.common_locations], etc.
     */
    val selectedCategoryOfInputSearch : StateFlow<String> = _selectedCategoryOfInputSearch
    val sortDirectionAscending: StateFlow<Boolean> = _sortDirectionAscending
    val filteredData: StateFlow<List<CompendiumItem>> = _filteredData

    init {
        getCompendiumItem()
    }

    fun filterData (compendiumItem: CompendiumDataList) : List<CompendiumItem> {
        return if (currentFilterCategory.value == "any") {
            _filteredData.value = compendiumItem.data
            _filteredData.value
        } else {
            filterData(compendiumItem.data,currentFilterCategory.value)
        }
    }

    fun updateFilterTerm(newFilterTerm: String) {
        _currentFilterCategory.value = newFilterTerm
    }

    fun toggleSortOrder() {
        _sortDirectionAscending.value = !_sortDirectionAscending.value
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

    fun assignSelectedCategoryOfInputSearch(category : String/* = "category"*/) {
        _selectedCategoryOfInputSearch.value = category
    }
    fun filterData(dataList: List<CompendiumItem>, filterTerm : String) : List<CompendiumItem> {
        dataList.filter { item -> item.category == _currentFilterCategory.value }
        return when (_selectedCategoryOfInputSearch.value) {
            "any" -> dataList.filter { item ->
                item.category.contains(filterTerm, ignoreCase = true) ||
                item.description.contains(filterTerm, ignoreCase = true) ||
                item.common_locations?.any { location -> location.contains(filterTerm, ignoreCase = true) } == true ||
                item.name.contains(filterTerm, ignoreCase = true) ||
                item.drops?.any { drop -> drop.contains(filterTerm, ignoreCase = true) } == true ||
                item.cooking_effect?.contains(filterTerm, ignoreCase = true) == true ||
                (item.edible?.toString() == filterTerm)
            }
            "category" -> dataList.filter { item -> item.category == filterTerm }
            "id" -> dataList.filter { item -> item.id == filterTerm.toIntOrNull() }
            "name" -> dataList.filter { item -> item.name.contains(filterTerm, ignoreCase = true) }
            else -> dataList.filter { item -> item.category == filterTerm }
        }
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
