package com.san.englishbender.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.san.englishbender.data.local.models.UserData
import kotlinx.coroutines.flow.map

class DataStore(
    private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
    }

    val userData = context.dataStore.data
        .map {
            UserData(
//                bookmarkedNewsResources = it.bookmarkedNewsResourceIdsMap.keys,
//                viewedNewsResources = it.viewedNewsResourceIdsMap.keys,
//                followedTopics = it.followedTopicIdsMap.keys,
//                themeBrand = when (it.themeBrand) {
//                    null,
//                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
//                    ThemeBrandProto.UNRECOGNIZED,
//                    ThemeBrandProto.THEME_BRAND_DEFAULT,
//                    -> ThemeBrand.DEFAULT
//                    ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
//                },
//                darkThemeConfig = when (it.darkThemeConfig) {
//                    null,
//                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
//                    DarkThemeConfigProto.UNRECOGNIZED,
//                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
//                    ->
//                        DarkThemeConfig.FOLLOW_SYSTEM
//                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
//                        DarkThemeConfig.LIGHT
//                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
//                },
                useDynamicColor = false,
//                shouldHideOnboarding = it.shouldHideOnboarding,
            )
        }
}