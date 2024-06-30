package com.record.recordy.model.local

import com.record.model.CoreData

data class RecordyData(
    val a: Int,
    val b: String
)

fun RecordyData.toCore() = CoreData(
    a = this.a,
    b = this.b
)
