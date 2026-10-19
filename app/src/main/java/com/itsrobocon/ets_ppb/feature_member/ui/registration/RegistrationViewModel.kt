package com.itsrobocon.ets_ppb.feature_member.ui.registration

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

class RegistrationViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

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

    fun registerMember() {
        val currentState = _uiState.value
        if (!currentState.isFormValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val newMember = MemberEntity(
                fullName = currentState.fullName.trim(),
                studentOrEmployeeId = currentState.studentOrEmployeeId.trim(),
                studyProgram = currentState.studyProgram.trim(),
                address = currentState.address.trim(),
                phoneNumber = currentState.phoneNumber.trim(),
                email = currentState.email.trim(),
                membershipType = currentState.membershipType.displayName
            )
            repository.insertMember(newMember)
            _uiState.update { it.copy(isSaving = false, isSuccess = true) }
        }
    }
}
