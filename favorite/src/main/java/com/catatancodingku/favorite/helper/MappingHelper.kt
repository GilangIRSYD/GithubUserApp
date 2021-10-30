package com.catatancodingku.favorite.helper

import android.database.Cursor
import com.catatancodingku.favorite.room.Favorite

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Favorite> {
        val notesList = ArrayList<Favorite>()

        cursor?.apply {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val imageUrl = getString(getColumnIndexOrThrow("imageUrl"))
                notesList.add(Favorite(id,name, imageUrl))
            }
        }
        return notesList
    }

//    fun mapCursorToObject(notesCursor: Cursor?): ContactsContract.CommonDataKinds.Note {
//        var note = ContactsContract.CommonDataKinds.Note()
//        notesCursor?.apply {
//            moveToFirst()
//            val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
//            val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
//            val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
//            val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
//            note = ContactsContract.CommonDataKinds.Note(id, title, description, date)
//        }
//        return note
//    }
}