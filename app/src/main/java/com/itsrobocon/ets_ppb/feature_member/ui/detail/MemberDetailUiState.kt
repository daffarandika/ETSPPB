package com.itsrobocon.ets_ppb.feature_member.ui.detail

import com.itsrobocon.ets_ppb.feature_member.data.MemberEntity
import com.itsrobocon.ets_ppb.feature_member.data.MembershipType

data class MemberDetailUiState(
    val member: MemberEntity? = null,
    val isLoading: Boolean = true,
    val isEditing: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isDeleted: Boolean = false,
    val isUpdated: Boolean = false,
    val isSaving: Boolean = false,

    val fullName: String = "",
    val fullNameError: String? = null,
    val studentOrEmployeeId: String = "",
    val studentOrEmployeeIdError: String? = null,
    val studyProgram: String = "",
    val studyProgramError: String? = null,
    val address: String = "",
    val addressError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val membershipType: MembershipType = MembershipType.MAHASISWA
) {
    val isFormValid: Boolean
        get() = fullName.isNotBlank() && fullNameError == null &&
                studentOrEmployeeId.isNotBlank() && studentOrEmployeeIdError == null &&
                studyProgram.isNotBlank() && studyProgramError == null &&
                address.isNotBlank() && addressError == null &&
                phoneNumber.isNotBlank() && phoneNumberError == null &&
                email.isNotBlank() && emailError == null
}
