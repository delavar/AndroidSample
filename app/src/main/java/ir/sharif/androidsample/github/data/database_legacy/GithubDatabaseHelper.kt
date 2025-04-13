package ir.sharif.androidsample.github.data.database_legacy

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object GithubRepoContract {
    object RepoEntry : BaseColumns {
        const val TABLE_NAME = "repos"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_STARS = "stars"
        const val COLUMN_NAME_LANGUAGE = "language"
        const val COLUMN_NAME_URL = "url"
        const val COLUMN_OWNER_USERNAME = "owner_username"
    }
}

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${GithubRepoContract.RepoEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${GithubRepoContract.RepoEntry.COLUMN_NAME_NAME} TEXT NOT NULL," +
            "${GithubRepoContract.RepoEntry.COLUMN_NAME_DESCRIPTION} TEXT," +
            "${GithubRepoContract.RepoEntry.COLUMN_NAME_STARS} INTEGER NOT NULL," +
            "${GithubRepoContract.RepoEntry.COLUMN_NAME_LANGUAGE} TEXT," +
            "${GithubRepoContract.RepoEntry.COLUMN_NAME_URL} TEXT NOT NULL," +
            "${GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME} TEXT NOT NULL)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${GithubRepoContract.RepoEntry.TABLE_NAME}"

class GithubDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "GithubDatabase_legacy.db"
    }
}
