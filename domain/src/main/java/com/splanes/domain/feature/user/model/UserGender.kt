package com.splanes.domain.feature.user.model

enum class UserGender(val apiName: String) {
    MALE(apiName = "male"),
    FEMALE(apiName = "female"),
    UNKNOWN(apiName = "");

    companion object {
        fun parse(name: String?): UserGender =
            values().associateBy { it.apiName }[name] ?: UNKNOWN
    }
}