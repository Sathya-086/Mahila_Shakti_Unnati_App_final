package com.example.mahilashaktiunnati.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mahilashaktiunnati.data.local.entity.MemberEntity
import com.example.mahilashaktiunnati.data.repository.MemberRepository
import com.example.mahilashaktiunnati.util.ValidationUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

/**
 * ViewModel for Member management screens.
 * Handles member list, search, add, edit, and delete operations.
 */
class MemberViewModel(private val memberRepository: MemberRepository) : ViewModel() {

    /** All members list */
    val members: StateFlow<List<MemberEntity>> = memberRepository.allMembers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Search query */
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    /** Filtered members based on search */
    val filteredMembers: StateFlow<List<MemberEntity>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) memberRepository.allMembers
            else memberRepository.searchMembers(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Error state */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /** Success state */
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addMember(
        fullName: String,
        age: Int,
        address: String,
        contactNumber: String,
        joiningDate: Date = Date(),
        photoUri: String? = null
    ) {
        if (!ValidationUtils.isValidName(fullName)) {
            _errorMessage.value = "Invalid name. Must be 2-100 characters."
            return
        }
        if (!ValidationUtils.isValidAge(age)) {
            _errorMessage.value = "Invalid age. Must be between 18 and 100."
            return
        }
        if (!ValidationUtils.isValidPhoneNumber(contactNumber)) {
            _errorMessage.value = "Invalid contact number. Must be a valid 10-digit Indian number."
            return
        }
        if (address.isBlank()) {
            _errorMessage.value = "Address cannot be empty."
            return
        }

        viewModelScope.launch {
            try {
                val member = MemberEntity(
                    memberId = UUID.randomUUID().toString().take(8).uppercase(),
                    fullName = fullName,
                    age = age,
                    address = address,
                    contactNumber = contactNumber,
                    joiningDate = joiningDate,
                    photoUri = photoUri
                )
                memberRepository.insertMember(member)
                _successMessage.value = "Member added successfully"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to add member"
            }
        }
    }

    fun updateMember(member: MemberEntity) {
        if (!ValidationUtils.isValidName(member.fullName)) {
            _errorMessage.value = "Invalid name. Must be 2-100 characters."
            return
        }
        if (!ValidationUtils.isValidAge(member.age)) {
            _errorMessage.value = "Invalid age. Must be between 18 and 100."
            return
        }
        if (!ValidationUtils.isValidPhoneNumber(member.contactNumber)) {
            _errorMessage.value = "Invalid contact number. Must be a valid 10-digit Indian number."
            return
        }
        if (member.address.isBlank()) {
            _errorMessage.value = "Address cannot be empty."
            return
        }

        viewModelScope.launch {
            try {
                memberRepository.updateMember(member)
                _successMessage.value = "Member updated successfully"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to update member"
            }
        }
    }

    fun deleteMember(member: MemberEntity) {
        viewModelScope.launch {
            try {
                memberRepository.deleteMember(member)
                _successMessage.value = "Member deleted successfully"
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to delete member"
            }
        }
    }

    fun getMemberById(memberId: String): Flow<MemberEntity?> {
        return memberRepository.getMemberByIdFlow(memberId)
    }

    fun clearError() { _errorMessage.value = null }
    fun clearSuccess() { _successMessage.value = null }
}

class MemberViewModelFactory(private val repository: MemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
