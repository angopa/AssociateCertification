package com.angopa.aad.androidui.codelabs.useravatar

class UserDummyList {
    companion object {
        fun createDummyUserList(): ArrayList<User> {
            val users = ArrayList<User>()
            users.add(
                User(
                    "Tomas",
                    "Seattle",
                    "https://www.w3schools.com/howto/img_avatar.png",
                    UserColor.RED,
                    Badge.GOLD,
                    Badge.SILVER,
                    Badge.BRONZE,
                    Badge.DIAMOND
                )
            )
            users.add(
                User(
                    "Edward",
                    "Toronto",
                    "https://www.w3schools.com/howto/img_avatar2.png",
                    UserColor.GREEN,
                    Badge.BRONZE,
                    Badge.GOLD,
                    Badge.SILVER,
                    Badge.SILVER
                )
            )
            users.add(
                User(
                    "Evelin",
                    "Dallas",
                    "https://www.w3schools.com/w3images/avatar2.png",
                    UserColor.BLUE,
                    Badge.PEARL,
                    Badge.SILVER,
                    Badge.GOLD,
                    Badge.SILVER
                )
            )
            users.add(
                User(
                    "Gilbert",
                    "Houston",
                    "https://www.w3schools.com/w3images/avatar6.png",
                    UserColor.YELLOW,
                    Badge.DIAMOND,
                    Badge.SILVER,
                    Badge.BRONZE,
                    Badge.GOLD
                )
            )
            users.add(
                User(
                    "Jen",
                    "Los Angeles",
                    "https://www.w3schools.com/w3images/avatar5.png",
                    UserColor.BLACK,
                    Badge.GOLD,
                    Badge.SILVER,
                    Badge.DIAMOND,
                    Badge.PEARL
                )
            )
            return users
        }
    }
}