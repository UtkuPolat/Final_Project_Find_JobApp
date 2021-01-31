package ise308.polat.utku.findjob.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ise308.polat.utku.findjob.JobData

private const val TAG = "DataManager"

class DataManager(context: Context) {

    private val db : SQLiteDatabase

    init {
        db = CustomSQLiteOpenHelper(context).writableDatabase
    }

    fun insertJob(newJob : JobData) {

        val insertQuery = "INSERT INTO " + TABLE_JOBS + " (" +
                HEADER + ", " + INFORMATION + ", " + CONTACT + ", " + SITUATION +
                ") VALUES ('" + newJob.jobHeader+ "' , '"+ newJob.jobInfo+ "' , '" + newJob.contactInfo+ "' , '"+ newJob.jobSituation+ "');"

        Log.i(TAG, "onCreate: $insertQuery")

        db.execSQL(insertQuery)
    }

    fun removeJob(jobHeader: String ) {

        val removeQuery = "DELETE FROM " + TABLE_JOBS +
                " WHERE " + HEADER+ " = '" + jobHeader +"';"

        Log.i(TAG, "onCreate: $removeQuery")

        db.execSQL(removeQuery)
    }

    fun updateJob(job : JobData, jobHeader: String) {   //job => New edited jobHeader of JobData item     |-|     jobHeader => Before editing jobHeader of item for search in database with query

        val updateQuery = "SELECT * FROM " +
                TABLE_JOBS + " WHERE " + HEADER+ " = '" + jobHeader + "';"

        val updateCursors = db.rawQuery(updateQuery, null)

        if(updateCursors.moveToFirst()) {

            do {

                val contentValue = ContentValues()

                contentValue.put(HEADER, job.jobHeader.toString())
                contentValue.put(INFORMATION,  job.jobInfo.toString())
                contentValue.put(CONTACT, job.contactInfo.toString())          // All data edited or not edited(For Example only one property changed or only job situation changed) are updating in Database (Line 48 to Line 67).
                contentValue.put(SITUATION, job.jobSituation)

                db.update(TABLE_JOBS, contentValue, "$HEADER=? AND $INFORMATION=?",
                        arrayOf(updateCursors.getString(updateCursors.getColumnIndex(HEADER)),      // After user enter the values of fields db will update line => Between 59-65
                                updateCursors.getString(updateCursors.getColumnIndex(INFORMATION))))

                db.update(TABLE_JOBS, contentValue, "$CONTACT=? AND $SITUATION=?",
                        arrayOf(updateCursors.getString(updateCursors.getColumnIndex(CONTACT)),
                                updateCursors.getString(updateCursors.getColumnIndex(SITUATION))))

            }while (updateCursors.moveToNext())

        }
    }

    fun resultsAllJob() : ArrayList<JobData> {

        val jobList = ArrayList<JobData>()

        val resultsQuery = "SELECT * FROM " +
                TABLE_JOBS

        val resultsCursor = db.rawQuery(resultsQuery, null)

        if(resultsCursor.moveToFirst()) {
            do {
                val job = JobData()
                job.jobHeader = resultsCursor.getString(resultsCursor.getColumnIndex(HEADER)).toString()
                job.jobInfo = resultsCursor.getString(resultsCursor.getColumnIndex(INFORMATION)).toString()
                job.contactInfo = resultsCursor.getString(resultsCursor.getColumnIndex(CONTACT)).toString()     //In database, every rows and columns reads, and create a JobData object list for RecyclerView
                job.jobSituation = resultsCursor.getString(resultsCursor.getColumnIndex(SITUATION)).toInt()
                jobList.add(job)
            } while (resultsCursor.moveToNext())
        }
        return jobList
    }

    private inner class CustomSQLiteOpenHelper(context: Context) :
            SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

        override fun onCreate(db: SQLiteDatabase?) {

            val newTableQuery = "CREATE TABLE " + TABLE_JOBS + " ( " +
                    _ID + " integer primary key autoincrement not null , " +
                    HEADER + " text not null, "+
                    INFORMATION + " text not null, "+
                    CONTACT + " text not null, "+
                    SITUATION + " integer not null default 1);"


            Log.i(TAG, "onCreate: $newTableQuery")

            db?.execSQL(newTableQuery)

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}


    }

    companion object {
        private const val DB_NAME = "job_database"
        private const val DB_VERSION = 1
        private const val TABLE_JOBS = "job_table"
        private const val _ID = "job_id"
        private const val HEADER = "job_header"
        private const val INFORMATION = "job_information"
        private const val CONTACT = "job_contact"
        private const val SITUATION = "job_situation"
    }


}