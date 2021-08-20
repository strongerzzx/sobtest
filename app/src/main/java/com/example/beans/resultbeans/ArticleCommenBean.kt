package com.example.beans.resultbeans

data class ArticleCommenBean(
        val code: Int,
        val `data`: Data,
        val message: String,
        val success: Boolean
)

data class Data(
        val content: List<Content>, //父评论
        val first: Boolean,
        val last: Boolean,
        val number: Int,
        val numberOfElements: Int,
        val pageable: Pageable,
        val size: Int,
        val sort: SortX,
        val totalElements: Int,
        val totalPages: Int
)

data class Content(
        val _id: String,
        val articleId: String,
        val avatar: String,
        val commentContent: String,
        val isTop: String,
        val nickname: String,
        val parentId: String,
        val publishTime: String,
        val role: Any,
        val subComments: List<SubComment>, //子评论
        val userId: String,
        val vip: Boolean
)

data class Pageable(
        val offset: Int,
        val pageNumber: Int,
        val pageSize: Int,
        val paged: Boolean,
        val sort: Sort,
        val unpaged: Boolean
)

data class SortX(
        val sorted: Boolean,
        val unsorted: Boolean
)

data class SubComment(
        val _id: String,
        val articleId: String,
        val beNickname: String,
        val beUid: String,
        val content: String,
        val parentId: String,
        val publishTime: String,
        val vip: Boolean,
        val yourAvatar: String,
        val yourNickname: String,
        val yourRole: String,
        val yourUid: String
)

data class Sort(
        val sorted: Boolean,
        val unsorted: Boolean
)