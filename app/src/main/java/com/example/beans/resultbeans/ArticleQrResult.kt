package com.example.beans.resultbeans

data class ArticleQrResult(
    val code: Int,
    val `data`: QrData,
    val message: String,
    val success: Boolean
)

data class QrData(
    val qrcUrl: String,
    val tips: String
)