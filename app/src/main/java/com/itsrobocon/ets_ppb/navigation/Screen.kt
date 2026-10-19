package com.itsrobocon.ets_ppb.navigation

sealed class Screen(val route: String) {
    data object MemberList : Screen("member_list")
    data object Registration : Screen("registration")
    data object MemberDetail : Screen("member_detail/{memberId}?isEdit={isEdit}") {
        fun createRoute(memberId: Int, isEdit: Boolean = false) = "member_detail/$memberId?isEdit=$isEdit"
    }
}
