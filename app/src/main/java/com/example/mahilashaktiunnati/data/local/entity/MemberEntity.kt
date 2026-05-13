package com.example.mahilashaktiunnati.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a Self-Help Group member.
 * One member can have many savings entries and many loans.
 */
@Entity(tableName = "members")
data class MemberEntity(
    @PrimaryKey
    val memberId: String,
    val fullName: String,
    val age: Int,
    val address: String,
    val contactNumber: String,
    val joiningDate: Date,
    val photoUri: String? = null
)
