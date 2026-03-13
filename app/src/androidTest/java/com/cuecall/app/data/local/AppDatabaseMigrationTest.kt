package com.cuecall.app.data.local

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

class AppDatabaseMigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate1To2_preservesFirstCounterServiceAssignment() {
        val dbName = "migration-test"
        helper.createDatabase(dbName, 1).apply {
            execSQL(
                """
                INSERT INTO counters (id, clinicId, name, serviceIdsJson, isActive, createdAt, updatedAt)
                VALUES ('counter1', '', 'Window 1', '["svc1","svc2"]', 1, 1, 1)
                """.trimIndent()
            )
            close()
        }

        helper.runMigrationsAndValidate(dbName, 2, true, AppDatabase.MIGRATION_1_2)
    }
}
