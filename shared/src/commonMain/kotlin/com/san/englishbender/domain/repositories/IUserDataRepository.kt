package com.san.englishbender.domain.repositories

import com.san.englishbender.data.local.models.UserSettings
import kotlinx.coroutines.flow.Flow

interface IUserDataRepository {

    /**
     * Stream of [UserSettings]
     */
    val userData: Flow<UserSettings>

    /**
     * Sets the user's currently followed topics
     */
//    suspend fun setFollowedTopicIds(followedTopicIds: Set<String>)
//
//    /**
//     * Toggles the user's newly followed/unfollowed topic
//     */
//    suspend fun toggleFollowedTopicId(followedTopicId: String, followed: Boolean)
//
//    /**
//     * Updates the bookmarked status for a news resource
//     */
//    suspend fun updateNewsResourceBookmark(newsResourceId: String, bookmarked: Boolean)
//
//    /**
//     * Updates the viewed status for a news resource
//     */
//    suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean)

    /**
     * Sets the desired theme brand.
     */
//    suspend fun setThemeBrand(themeBrand: ThemeBrand)
//
//    /**
//     * Sets the desired dark theme config.
//     */
//    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
//    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
//
//    /**
//     * Sets whether the user has completed the onboarding process.
//     */
//    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)
}