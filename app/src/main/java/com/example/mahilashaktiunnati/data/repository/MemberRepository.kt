package com.example.mahilashaktiunnati.data.repository

import com.example.mahilashaktiunnati.data.local.dao.MemberDao
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for Member operations.
 * Handles member CRUD, validation, and search.
 */
class MemberRepository(private val memberDao: MemberDao) {

    val allMembers: Flow<List<MemberEntity>> = memberDao.getAllMembers()
    val memberCount: Flow<Int> = memberDao.getMemberCount()

    suspend fun insertMember(member: MemberEntity) {
        require(member.fullName.isNotBlank()) { "Member name cannot be empty" }
        require(member.contactNumber.isNotBlank()) { "Contact number cannot be empty" }
        require(member.age in 18..100) { "Age must be between 18 and 100" }
        memberDao.insertMember(member)
    }

    suspend fun updateMember(member: MemberEntity) {
        require(member.fullName.isNotBlank()) { "Member name cannot be empty" }
        memberDao.updateMember(member)
    }

    suspend fun deleteMember(member: MemberEntity) {
        memberDao.deleteMember(member)
    }

    suspend fun getMemberById(memberId: String): MemberEntity? {
        return memberDao.getMemberById(memberId)
    }

    fun getMemberByIdFlow(memberId: String): Flow<MemberEntity?> {
        return memberDao.getMemberByIdFlow(memberId)
    }

    fun searchMembers(query: String): Flow<List<MemberEntity>> {
        return memberDao.searchMembers(query)
    }
}
