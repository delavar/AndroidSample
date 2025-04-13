package ir.sharif.androidsample.github.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.sharif.androidsample.github.data.database.dao.GithubRepoDao
import ir.sharif.androidsample.github.data.database.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubRepoDao(): GithubRepoDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "GithubDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
