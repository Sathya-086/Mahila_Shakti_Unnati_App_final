package com.example.mahilashaktiunnati.data.local.dao

import androidx.room.*
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Member operations.
 * Provides CRUD operations and queries for member management.
 */
@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: MemberEntity)

    @Update
    suspend fun updateMember(member: MemberEntity)

    @Delete
    suspend fun deleteMember(member: MemberEntity)

    @Query("SELECT * FROM members ORDER BY fullName ASC")
    fun getAllMembers(): Flow<List<MemberEntity>>

    @Query("SELECT * FROM members WHERE memberId = :memberId")
    suspend fun getMemberById(memberId: String): MemberEntity?

    @Query("SELECT * FROM members WHERE memberId = :memberId")
    fun getMemberByIdFlow(memberId: String): Flow<MemberEntity?>

    @Query("SELECT * FROM members WHERE fullName LIKE '%' || :query || '%' OR memberId LIKE '%' || :query || '%' OR contactNumber LIKE '%' || :query || '%'")
    fun searchMembers(query: String): Flow<List<MemberEntity>>

    @Query("SELECT COUNT(*) FROM members")
    fun getMemberCount(): Flow<Int>
}
