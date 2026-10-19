package com.itsrobocon.ets_ppb.feature_member.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MemberEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
}
