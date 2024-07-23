package com.record.common.intentprovider

interface UploadBroadCaster {
    fun sendUploadProgress(percentage: Int)
    fun sendUploadStart()
    fun sendUploadEnd()
    fun sendUploadSuccess()
    fun sendUploadFailure()
}
