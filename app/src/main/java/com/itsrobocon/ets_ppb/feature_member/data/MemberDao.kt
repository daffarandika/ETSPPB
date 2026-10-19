package com.itsrobocon.ets_ppb.feature_member.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: MemberEntity): Long

    @Update
    suspend fun updateMember(member: MemberEntity)

    @Delete
    suspend fun deleteMember(member: MemberEntity)

    @Query("SELECT * FROM members WHERE id = :id")
    suspend fun getMemberById(id: Int): MemberEntity?

    @Query("SELECT * FROM members WHERE id = :id")
    fun getMemberByIdFlow(id: Int): Flow<MemberEntity?>

    @Query("SELECT * FROM members ORDER BY id DESC")
    fun getAllMembers(): Flow<List<MemberEntity>>

    @Query("SELECT * FROM members WHERE fullName LIKE '%' || :query || '%' OR studentOrEmployeeId LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchMembers(query: String): Flow<List<MemberEntity>>
}
