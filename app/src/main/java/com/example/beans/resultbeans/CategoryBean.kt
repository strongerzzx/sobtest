package com.example.beans.resultbeans

data class CategoryBean(
    val code: Int,
    val `data`: List<CategoryData>,
    val message: String,
    val success: Boolean
)

data class CategoryData(
    val categoryName: String,
    val createTime: String,
    val description: String,
    val id: String,
    val order: Int,
    val pyName: String,
    val updateTime: Any
)