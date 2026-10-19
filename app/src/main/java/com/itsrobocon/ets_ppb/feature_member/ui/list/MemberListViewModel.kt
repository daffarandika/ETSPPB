package com.itsrobocon.ets_ppb.feature_member.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsrobocon.ets_ppb.feature_member.data.MemberEntity
import com.itsrobocon.ets_ppb.feature_member.data.MemberRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MemberListViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _memberToDelete = MutableStateFlow<MemberEntity?>(null)

    private val _members = _searchQuery.flatMapLatest { query ->
        repository.searchMembers(query)
    }

    val uiState: StateFlow<MemberListUiState> = combine(
        _members,
        _searchQuery,
        _memberToDelete
    ) { members, searchQuery, memberToDelete ->
        MemberListUiState(
            members = members,
            searchQuery = searchQuery,
            isLoading = false,
            memberToDelete = memberToDelete
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MemberListUiState(isLoading = true)
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun confirmDeleteMember(member: MemberEntity) {
        _memberToDelete.value = member
    }

    fun dismissDeleteDialog() {
        _memberToDelete.value = null
    }

    fun deleteConfirmed() {
        val member = _memberToDelete.value ?: return
        viewModelScope.launch {
            repository.deleteMember(member)
            _memberToDelete.value = null
        }
    }
}
