package com.alangeronimo.data.repository

import android.content.Context
import com.alangeronimo.data.common.MyLogger
import com.alangeronimo.data.dto.SakeShopDto
import com.alangeronimo.data.mapper.toDomain
import com.alangeronimo.data.network.SakeShopApi
import com.alangeronimo.data.utils.readBackupJson
import com.alangeronimo.domain.model.SakeShop
import com.alangeronimo.domain.repository.SakeShopRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SakeShopRepositoryImpl(
    private val api: SakeShopApi,
    private val context: Context,
    private val gson: Gson,
    private val logger: MyLogger,
) : SakeShopRepository {
    override suspend fun getSakeShops(): List<SakeShop> =
        withContext(Dispatchers.IO) {
            try {
                api.getSakeShops().map { it.toDomain() }
            } catch (e: Exception) {
                logger.logError("Repository", "API failed: ${e.message}. Loading fallback JSON.")
                val backupJson: String = context.readBackupJson("sake_shops_backup.json")
                val type = object : TypeToken<List<SakeShopDto>>() {}.type
                gson.fromJson<List<SakeShopDto>>(backupJson, type).map { it.toDomain() }
            }
        }
}
