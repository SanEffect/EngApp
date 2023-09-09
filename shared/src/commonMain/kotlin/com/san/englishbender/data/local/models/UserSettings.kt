package com.san.englishbender.data.local.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class UserSettings : RealmObject {
    var tagColors: RealmList<String> = realmListOf()

    //    val bookmarkedNewsResources: Set<String>,
//    val viewedNewsResources: Set<String>,
//    val followedTopics: Set<String>,
////    val themeBrand: ThemeBrand,
////    val darkThemeConfig: DarkThemeConfig,
//    val useDynamicColor: Boolean,
//    val shouldHideOnboarding: Boolean,
}