package ise308.polat.utku.findjob

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ise308.polat.utku.findjob.db.DataManager



class AddNewJobActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_job)


        val etJobHeader = findViewById<EditText>(R.id.et_job_header)
        val etJobInformation = findViewById<EditText>(R.id.et_job_information)
        val etJobContact = findViewById<EditText>(R.id.et_job_contact)

        val btnJobInsert = findViewById<Button>(R.id.btn_add_database_job)
        val btnBack = findViewById<Button>(R.id.btn_back)

        val dm = DataManager(application)

        btnJobInsert.setOnClickListener {

            if(etJobHeader.text.toString() == "" || etJobInformation.text.toString() == "" || etJobContact.text.toString() == ""){
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Error")
                builder.setMessage("Please fill the all blanks")

                builder.setPositiveButton("Ok"){ _, which ->
                    Toast.makeText(this,"Fill the blanks", Toast.LENGTH_LONG).show()
                }
                builder.show()
            } else {
                var jobInsert = JobData(etJobHeader.text.toString(), etJobInformation.text.toString(), etJobContact.text.toString(),1)

                dm.insertJob(jobInsert)                                                                                                        // I don't want to selecting the job situation because
                val intent = Intent(applicationContext, MainActivity::class.java)                                                              //I think that the new added job have at least one open position.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)                                                                                //Briefly, new added job has never closed position.
                startActivity(intent)
                onBackPressed()
            }

        }

        btnBack.setOnClickListener {
            finish()
        }

    }
}