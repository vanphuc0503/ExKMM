package com.vanphuc.exsqldelight

import com.vanphuc.exsqldelight.cache.Database
import com.vanphuc.exsqldelight.cache.DatabaseDriverFactory
import com.vanphuc.exsqldelight.entity.RocketLaunch
import com.vanphuc.exsqldelight.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}