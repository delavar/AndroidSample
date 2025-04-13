package ir.sharif.androidsample.github.data.database_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.sharif.androidsample.github.data.database_room.dao.GithubRepoDao
import ir.sharif.androidsample.github.data.database_room.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class GithubRoomDatabase : RoomDatabase() {
    abstract fun githubRepoDao(): GithubRepoDao

    companion object {
        @Volatile
        private var INSTANCE: GithubRoomDatabase? = null

        fun getDatabase(context: Context): GithubRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubRoomDatabase::class.java,
                    "GithubDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
