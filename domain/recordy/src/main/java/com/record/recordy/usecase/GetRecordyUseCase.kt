package com.record.recordy.usecase

import com.record.recordy.repository.RecordyRepository
import javax.inject.Inject

class GetRecordyUseCase @Inject constructor(
    private val recordyRepository: RecordyRepository,
) {
    suspend operator fun invoke() = recordyRepository.getRecordy()
}
