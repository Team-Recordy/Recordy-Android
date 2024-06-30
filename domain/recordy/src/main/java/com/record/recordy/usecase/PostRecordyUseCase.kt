package com.record.recordy.usecase

import com.record.recordy.model.Recordy
import com.record.recordy.repository.RecordyRepository
import javax.inject.Inject

class PostRecordyUseCase @Inject constructor(
    private val recordyRepository: RecordyRepository,
) {
    suspend operator fun invoke(recordy: Recordy) = recordyRepository.postRecordy(recordy)
}
