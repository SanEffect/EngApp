package com.san.englishbender.data.repositories

import com.san.englishbender.data.local.dataStore.DataStoreRealm
import com.san.englishbender.domain.repositories.IUserDataRepository

class UserDataRepository(
    private val dataStore: DataStoreRealm
) : IUserDataRepository {

//    override val userData: Flow<UserSettings> =
//        dataStore.getUserSettingsAsFlow()
}