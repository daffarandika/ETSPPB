package com.itsrobocon.ets_ppb.feature_member.ui.list

import com.itsrobocon.ets_ppb.feature_member.data.MemberEntity

data class MemberListUiState(
    val members: List<MemberEntity> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val memberToDelete: MemberEntity? = null
)
