package ir.sharif.androidsample.github.data.database_legacy.dao

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import androidx.core.database.sqlite.transaction
import ir.sharif.androidsample.github.data.database_legacy.GithubDatabaseHelper
import ir.sharif.androidsample.github.data.database_legacy.GithubRepoContract
import ir.sharif.androidsample.github.data.database_legacy.entity.GithubRepoEntity

class GithubRepoDao(context: Context) {

    private val dbHelper = GithubDatabaseHelper(context)

    fun insert(entity: GithubRepoEntity): Long {
        // Gets the data repository in write mode
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(GithubRepoContract.RepoEntry.COLUMN_NAME_NAME, entity.name)
            put(GithubRepoContract.RepoEntry.COLUMN_NAME_DESCRIPTION, entity.description)
            put(GithubRepoContract.RepoEntry.COLUMN_NAME_STARS, entity.stars)
            put(GithubRepoContract.RepoEntry.COLUMN_NAME_LANGUAGE, entity.language)
            put(GithubRepoContract.RepoEntry.COLUMN_NAME_URL, entity.url)
            put(GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME, entity.ownerUsername)
        }

        // Insert the new row, returning the primary key value of the new row
        // or -1 if an error occurred
        return db.insert(GithubRepoContract.RepoEntry.TABLE_NAME, null, values)
    }

    fun getByOwnerName(ownerUsername: String): List<GithubRepoEntity> {
        val db = dbHelper.readableDatabase
        val entities = mutableListOf<GithubRepoEntity>()

        // Define a projection that specifies which columns from the database
        // you will actually use after this query
        val projection = arrayOf(
            BaseColumns._ID,
            GithubRepoContract.RepoEntry.COLUMN_NAME_NAME,
            GithubRepoContract.RepoEntry.COLUMN_NAME_DESCRIPTION,
            GithubRepoContract.RepoEntry.COLUMN_NAME_STARS,
            GithubRepoContract.RepoEntry.COLUMN_NAME_LANGUAGE,
            GithubRepoContract.RepoEntry.COLUMN_NAME_URL
        )

        // Filter results WHERE "name" = 'ownerUsername'
        // Note: In a real implementation, you would need a column to store the owner username
        // Since the current schema doesn't have this column, this is a placeholder implementation
        // that would need to be adapted to your actual data model
        val selection = "${GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME} LIKE ?"
        val selectionArgs = arrayOf("%$ownerUsername%")

        val cursor = db.query(
            GithubRepoContract.RepoEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val entity = GithubRepoEntity(
                    name = getString(getColumnIndexOrThrow(GithubRepoContract.RepoEntry.COLUMN_NAME_NAME)),
                    description = getString(getColumnIndexOrThrow(GithubRepoContract.RepoEntry.COLUMN_NAME_DESCRIPTION)),
                    stars = getInt(getColumnIndexOrThrow(GithubRepoContract.RepoEntry.COLUMN_NAME_STARS)),
                    language = getString(getColumnIndexOrThrow(GithubRepoContract.RepoEntry.COLUMN_NAME_LANGUAGE)),
                    url = getString(getColumnIndexOrThrow(GithubRepoContract.RepoEntry.COLUMN_NAME_URL)),
                    ownerUsername = getString(getColumnIndexOrThrow(GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME))
                )
                entities.add(entity)
            }
        }
        cursor.close()

        return entities
    }

    fun updateAll(entities: List<GithubRepoEntity>): Int {
        val db = dbHelper.writableDatabase
        var updatedCount = 0

        db.beginTransaction()
        try {
            for (entity in entities) {
                val values = ContentValues().apply {
                    put(GithubRepoContract.RepoEntry.COLUMN_NAME_NAME, entity.name)
                    put(GithubRepoContract.RepoEntry.COLUMN_NAME_DESCRIPTION, entity.description)
                    put(GithubRepoContract.RepoEntry.COLUMN_NAME_STARS, entity.stars)
                    put(GithubRepoContract.RepoEntry.COLUMN_NAME_LANGUAGE, entity.language)
                    put(GithubRepoContract.RepoEntry.COLUMN_NAME_URL, entity.url)
                    put(GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME, entity.ownerUsername)
                }

                // Define the selection criteria
                val selection =
                    "${GithubRepoContract.RepoEntry.COLUMN_NAME_NAME} = ? AND ${GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME} = ?"
                val selectionArgs = arrayOf(entity.name, entity.ownerUsername)

                // Update the record
                val count = db.update(
                    GithubRepoContract.RepoEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )

                updatedCount += count
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        return updatedCount
    }

    fun insertAll(entities: List<GithubRepoEntity>): List<Long> {
        val db = dbHelper.writableDatabase
        val insertedIds = mutableListOf<Long>()

        // Use a transaction for better performance when inserting multiple items
        db.transaction {
            try {
                for (entity in entities) {
                    val values = ContentValues().apply {
                        put(GithubRepoContract.RepoEntry.COLUMN_NAME_NAME, entity.name)
                        put(GithubRepoContract.RepoEntry.COLUMN_NAME_DESCRIPTION, entity.description)
                        put(GithubRepoContract.RepoEntry.COLUMN_NAME_STARS, entity.stars)
                        put(GithubRepoContract.RepoEntry.COLUMN_NAME_LANGUAGE, entity.language)
                        put(GithubRepoContract.RepoEntry.COLUMN_NAME_URL, entity.url)
                        put(GithubRepoContract.RepoEntry.COLUMN_OWNER_USERNAME, entity.ownerUsername)
                    }

                    val id = insert(GithubRepoContract.RepoEntry.TABLE_NAME, null, values)
                    insertedIds.add(id)
                }
            } finally {
            }
        }

        return insertedIds
    }

    // use this method to close the database when the DAO is no longer needed
    fun close() {
        dbHelper.close()
    }
}
