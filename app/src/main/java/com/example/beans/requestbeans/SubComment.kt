package com.example.beans.requestbeans

data class SubComment(val articleId: String, val parentId: String, val beUid: String, val beNickname: String, var content: String) {
    constructor(articleId: String, parentId: String, beUid: String, beNickname: String) : this(articleId,
            parentId, beUid, beNickname, "") {
    }
}