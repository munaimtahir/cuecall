package com.cuecall.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cuecall.app.data.local.dao.*
import com.cuecall.app.data.local.entity.*

@Database(
    entities = [
        ClinicEntity::class,
        ServiceEntity::class,
        CounterEntity::class,
        QueueDayEntity::class,
        TokenEntity::class,
        CallEventEntity::class,
        DeviceEntity::class,
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clinicDao(): ClinicDao
    abstract fun serviceDao(): ServiceDao
    abstract fun counterDao(): CounterDao
    abstract fun queueDayDao(): QueueDayDao
    abstract fun tokenDao(): TokenDao
    abstract fun callEventDao(): CallEventDao
    abstract fun deviceDao(): DeviceDao

    companion object {
        const val DATABASE_NAME = "cuecall.db"
    }
}
