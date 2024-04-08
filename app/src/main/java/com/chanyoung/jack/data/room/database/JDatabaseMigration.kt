package com.chanyoung.jack.data.room.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object JDatabaseMigration {
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE Group")
        }
    }
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {

            db.execSQL("ALTER TABLE JLink RENAME TO Link")

            db.execSQL("ALTER TABLE JGroup RENAME TO LinkGroup")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {

            db.execSQL("ALTER TABLE JLink RENAME TO Link")

            db.execSQL("ALTER TABLE Group RENAME TO LinkGroup")
        }

    }

    val MIGRATION_4_5 = object :Migration(4,5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE Link ADD COLUMN imagePath TEXT")
        }
    }

    val MIGRATION_5_6 = object :Migration(5,6) {
        override fun migrate(db: SupportSQLiteDatabase) {

            db.execSQL("DROP TABLE IF EXISTS Group")

            db.execSQL("ALTER TABLE Link DROP COLUMN imagePath")

            db.execSQL("ALTER TABLE Link ADD COLUMN imagePath TEXT DEFAULT NULL")
        }
    }

}