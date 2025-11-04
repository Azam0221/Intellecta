package com.example.intellecta.dao


import android.content.Context
import com.example.intellecta.model.Note
import com.example.intellecta.model.FileMeta
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities =  [Note::class, FileMeta::class], version = 2, exportSchema = false)
abstract class IntellectaDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun fileDao(): FileDao

    companion object {
        @Volatile
        private var INSTANCE: IntellectaDatabase? = null

        // ========== ADD THIS MIGRATION ==========
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //New columns to notes table
                database.execSQL("ALTER TABLE notes ADD COLUMN isSynced INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE notes ADD COLUMN servedId TEXT")
                database.execSQL("ALTER TABLE notes ADD COLUMN lastModified INTEGER")
                 database.execSQL("ALTER TABLE notes ADD COLUMN  syncError TEXT")
                database.execSQL("ALTER TABLE notes ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE notes ADD COLUMN deletedAt INTEGER")

                //New columns to files table
                database.execSQL("ALTER TABLE files ADD COLUMN isSynced INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE files ADD COLUMN servedId TEXT")
                database.execSQL("ALTER TABLE files ADD COLUMN lastModified INTEGER")
                database.execSQL("ALTER TABLE files ADD COLUMN syncError TEXT")
                database.execSQL("ALTER TABLE files ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE files ADD COLUMN deletedAt INTEGER")
            }
        }

        fun getDatabase(context: Context): IntellectaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IntellectaDatabase::class.java,
                    "intellecta_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
} 