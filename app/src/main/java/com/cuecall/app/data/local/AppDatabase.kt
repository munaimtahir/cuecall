package com.cuecall.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 2,
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

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `counters_new` (
                        `id` TEXT NOT NULL,
                        `clinicId` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `serviceId` TEXT,
                        `isActive` INTEGER NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_counters_new_clinicId` ON `counters_new` (`clinicId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_counters_new_serviceId` ON `counters_new` (`serviceId`)")
                db.execSQL(
                    """
                    INSERT INTO `counters_new` (`id`, `clinicId`, `name`, `serviceId`, `isActive`, `createdAt`, `updatedAt`)
                    SELECT
                        `id`,
                        `clinicId`,
                        `name`,
                        CASE
                            WHEN `serviceIdsJson` IS NULL OR TRIM(`serviceIdsJson`) IN ('', '[]') THEN NULL
                            WHEN INSTR(`serviceIdsJson`, '"') = 0 THEN NULL
                            ELSE SUBSTR(
                                `serviceIdsJson`,
                                INSTR(`serviceIdsJson`, '"') + 1,
                                INSTR(SUBSTR(`serviceIdsJson`, INSTR(`serviceIdsJson`, '"') + 1), '"') - 1
                            )
                        END,
                        `isActive`,
                        `createdAt`,
                        `updatedAt`
                    FROM `counters`
                    """.trimIndent()
                )
                db.execSQL("DROP TABLE `counters`")
                db.execSQL("ALTER TABLE `counters_new` RENAME TO `counters`")
            }
        }
    }
}
