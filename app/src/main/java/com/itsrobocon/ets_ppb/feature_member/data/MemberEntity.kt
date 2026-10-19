package com.itsrobocon.ets_ppb.feature_member.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MembershipType(val displayName: String) {
    MAHASISWA("Mahasiswa"),
    DOSEN("Dosen"),
    TENDIK("Tendik");

    companion object {
        fun fromString(value: String): MembershipType {
            return entries.find { it.displayName.equals(value, ignoreCase = true) || it.name.equals(value, ignoreCase = true) }
                ?: MAHASISWA
        }
    }
}

@Entity(tableName = "members")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val studentOrEmployeeId: String,
    val studyProgram: String,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val membershipType: String
)
