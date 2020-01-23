package com.angopa.aad.androidui.codelabs.useravatar

data class User(
    var name: String,
    var branch: String,
    var avatarUrl: String,
    var avatarColor: UserColor,
    var topLeftBadge: Badge,
    var topRightBadge: Badge,
    var bottomLeftBadge: Badge,
    var bottomRightBadge: Badge
)