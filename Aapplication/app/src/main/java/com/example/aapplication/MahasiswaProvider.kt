package com.example.aapplication

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class MahasiswaProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.aapplication.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/mahasiswa")

        private const val MAHASISWA_DIR = 1
        private const val MAHASISWA_ITEM = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "mahasiswa", MAHASISWA_DIR)
            addURI(AUTHORITY, "mahasiswa/#", MAHASISWA_ITEM)
        }
    }

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(): Boolean {
        dbHelper = DatabaseHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        val cursor = when (uriMatcher.match(uri)) {
            MAHASISWA_DIR -> db.query(DatabaseHelper.TABLE_MAHASISWA, projection, selection, selectionArgs, null, null, sortOrder)
            MAHASISWA_ITEM -> {
                val id = uri.lastPathSegment
                db.query(DatabaseHelper.TABLE_MAHASISWA, projection, "${DatabaseHelper.COL_ID} = ?", arrayOf(id), null, null, null)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(DatabaseHelper.TABLE_MAHASISWA, null, values)
        if (id > 0) {
            context?.contentResolver?.notifyChange(uri, null)
            return Uri.withAppendedPath(CONTENT_URI, id.toString())
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val count = db.delete(DatabaseHelper.TABLE_MAHASISWA, selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper.writableDatabase
        val count = db.update(DatabaseHelper.TABLE_MAHASISWA, values, selection, selectionArgs)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? = when (uriMatcher.match(uri)) {
        MAHASISWA_DIR -> "vnd.android.cursor.dir/vnd.com.example.mahasiswa"
        MAHASISWA_ITEM -> "vnd.android.cursor.item/vnd.com.example.mahasiswa"
        else -> null
    }
}