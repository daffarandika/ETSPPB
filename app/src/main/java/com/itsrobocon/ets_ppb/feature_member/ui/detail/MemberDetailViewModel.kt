package com.itsrobocon.ets_ppb.feature_member.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsrobocon.ets_ppb.feature_member.data.MemberEntity
import com.itsrobocon.ets_ppb.feature_member.data.MemberRepository
import com.itsrobocon.ets_ppb.feature_member.data.MembershipType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemberDetailViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemberDetailUiState())
    val uiState: StateFlow<MemberDetailUiState> = _uiState.asStateFlow()

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    private var isInitialized = false

    fun loadMember(memberId: Int, initialIsEdit: Boolean = false) {
        viewModelScope.launch {
            if (!isInitialized) {
                _uiState.update { it.copy(isLoading = true, isEditing = initialIsEdit) }
                isInitialized = true
            }
            repository.getMemberByIdFlow(memberId).collect { member ->
                if (member != null) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            member = member,
                            isLoading = false,
                            fullName = if (currentState.isEditing && currentState.fullName.isNotBlank()) currentState.fullName else member.fullName,
                            studentOrEmployeeId = if (currentState.isEditing && currentState.studentOrEmployeeId.isNotBlank()) currentState.studentOrEmployeeId else member.studentOrEmployeeId,
                            studyProgram = if (currentState.isEditing && currentState.studyProgram.isNotBlank()) currentState.studyProgram else member.studyProgram,
                            address = if (currentState.isEditing && currentState.address.isNotBlank()) currentState.address else member.address,
                            phoneNumber = if (currentState.isEditing && currentState.phoneNumber.isNotBlank()) currentState.phoneNumber else member.phoneNumber,
                            email = if (currentState.isEditing && currentState.email.isNotBlank()) currentState.email else member.email,
                            membershipType = if (currentState.isEditing) currentState.membershipType else MembershipType.fromString(member.membershipType)
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, member = null) }
                }
            }
        }
    }

    fun toggleEditMode() {
        val currentState = _uiState.value
        val newIsEditing = !currentState.isEditing
        if (newIsEditing && currentState.member != null) {
            val member = currentState.member
            _uiState.update {
                it.copy(
                    isEditing = true,
                    fullName = member.fullName,
                    fullNameError = null,
                    studentOrEmployeeId = member.studentOrEmployeeId,
                    studentOrEmployeeIdError = null,
                    studyProgram = member.studyProgram,
                    studyProgramError = null,
                    address = member.address,
                    addressError = null,
                    phoneNumber = member.phoneNumber,
                    phoneNumberError = null,
                    email = member.email,
                    emailError = null,
                    membershipType = MembershipType.fromString(member.membershipType)
                )
            }
        } else {
            _uiState.update { it.copy(isEditing = false) }
        }
    }

    fun onFullNameChanged(value: String) {
        val error = when {
            value.isBlank() -> "Nama lengkap wajib diisi"
            value.trim().length < 3 -> "Nama minimal 3 karakter"
            else -> null
        }
        _uiState.update { it.copy(fullName = value, fullNameError = error) }
    }

    fun onStudentOrEmployeeIdChanged(value: String) {
        val error = when {
            value.isBlank() -> "NIM/NIP wajib diisi"
            !value.all { it.isDigit() } -> "NIM/NIP harus berupa angka"
            value.length < 5 -> "NIM/NIP minimal 5 digit"
            else -> null
        }
        _uiState.update { it.copy(studentOrEmployeeId = value, studentOrEmployeeIdError = error) }
    }

    fun onStudyProgramChanged(value: String) {
        val error = when {
            value.isBlank() -> "Program Studi wajib diisi"
            else -> null
        }
        _uiState.update { it.copy(studyProgram = value, studyProgramError = error) }
    }

    fun onAddressChanged(value: String) {
        val error = when {
            value.isBlank() -> "Alamat wajib diisi"
            else -> null
        }
        _uiState.update { it.copy(address = value, addressError = error) }
    }

    fun onPhoneNumberChanged(value: String) {
        val error = when {
            value.isBlank() -> "Nomor HP wajib diisi"
            !value.all { it.isDigit() || it == '+' } -> "Nomor HP hanya boleh berisi angka/plus"
            value.length < 8 -> "Nomor HP minimal 8 digit"
            else -> null
        }
        _uiState.update { it.copy(phoneNumber = value, phoneNumberError = error) }
    }

    fun onEmailChanged(value: String) {
        val error = when {
            value.isBlank() -> "Email wajib diisi"
            !emailRegex.matches(value.trim()) -> "Format email tidak valid (contoh: user@mail.com)"
            else -> null
        }
        _uiState.update { it.copy(email = value, emailError = error) }
    }

    fun onMembershipTypeChanged(value: MembershipType) {
        _uiState.update { it.copy(membershipType = value) }
    }

    fun updateMember() {
        val currentState = _uiState.value
        val member = currentState.member ?: return
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val updatedMember = member.copy(
                fullName = currentState.fullName.trim(),
                studentOrEmployeeId = currentState.studentOrEmployeeId.trim(),
                studyProgram = currentState.studyProgram.trim(),
                address = currentState.address.trim(),
                phoneNumber = currentState.phoneNumber.trim(),
                email = currentState.email.trim(),
                membershipType = currentState.membershipType.displayName
            )
            repository.updateMember(updatedMember)
            _uiState.update {
                it.copy(
                    isSaving = false,
                    isEditing = false,
                    isUpdated = true
                )
            }
        }
    }

    fun showDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun deleteMember() {
        val member = _uiState.value.member ?: return
        viewModelScope.launch {
            repository.deleteMember(member)
            _uiState.update { it.copy(showDeleteDialog = false, isDeleted = true) }
        }
    }
}
