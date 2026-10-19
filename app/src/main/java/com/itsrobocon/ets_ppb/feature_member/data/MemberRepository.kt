package com.itsrobocon.ets_ppb.feature_member.data

import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun getAllMembers(): Flow<List<MemberEntity>>
    fun searchMembers(query: String): Flow<List<MemberEntity>>
    fun getMemberByIdFlow(id: Int): Flow<MemberEntity?>
    suspend fun getMemberById(id: Int): MemberEntity?
    suspend fun insertMember(member: MemberEntity): Long
    suspend fun updateMember(member: MemberEntity)
    suspend fun deleteMember(member: MemberEntity)
}

class MemberRepositoryImpl(
    private val memberDao: MemberDao
) : MemberRepository {

    override fun getAllMembers(): Flow<List<MemberEntity>> {
        return memberDao.getAllMembers()
    }

    override fun searchMembers(query: String): Flow<List<MemberEntity>> {
        return if (query.isBlank()) {
            memberDao.getAllMembers()
        } else {
            memberDao.searchMembers(query.trim())
        }
    }

    override fun getMemberByIdFlow(id: Int): Flow<MemberEntity?> {
        return memberDao.getMemberByIdFlow(id)
    }

    override suspend fun getMemberById(id: Int): MemberEntity? {
        return memberDao.getMemberById(id)
    }

    override suspend fun insertMember(member: MemberEntity): Long {
        return memberDao.insertMember(member)
    }

    override suspend fun updateMember(member: MemberEntity) {
        memberDao.updateMember(member)
    }

    override suspend fun deleteMember(member: MemberEntity) {
        memberDao.deleteMember(member)
    }
}
