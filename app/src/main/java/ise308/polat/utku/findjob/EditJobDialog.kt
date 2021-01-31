package ise308.polat.utku.findjob

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import ise308.polat.utku.findjob.db.DataManager


class EditJobDialog(var jobData: JobData) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)

        val inflater = activity!!.layoutInflater
        val newJobDialog = inflater.inflate(R.layout.edit_job_dialog, null)

        val editTextHeader = newJobDialog.findViewById<EditText>(R.id.et_new_job_header)
        val editTextInformation = newJobDialog.findViewById<EditText>(R.id.et_new_job_information)
        val editTextContact = newJobDialog.findViewById<EditText>(R.id.et_new_job_contact)
        val switchJobSituation = newJobDialog.findViewById<SwitchCompat>(R.id.job_situation)

        val buttonUpdate = newJobDialog.findViewById<Button>(R.id.btn_update_job)

        editTextHeader.setText(jobData.jobHeader.toString())                  //  jobData is the coming data form Jobs Adapter. Because of data passing.
        editTextInformation.setText(jobData.jobInfo.toString())               //  If user tap to edit image on the any job item, job item informations are shown in the Edit Text field.
        editTextContact.setText(jobData.contactInfo.toString())               //  For the lines between 30 and 33. This operation will be done.
        switchJobSituation.isChecked = jobData.jobSituation == 1              //  Edit dialogFragment switch => if 1 isChecked = true, else isChecked = false

        builder.setView(newJobDialog)



        buttonUpdate.setOnClickListener {

            val editedJob = JobData()

            editedJob.jobHeader = editTextHeader.text.toString()            //  I create a JobData object with the datas which are entered by user, and update the database in this setOnClickLister.
            editedJob.jobInfo = editTextInformation.text.toString()
            editedJob.contactInfo = editTextContact.text.toString()
            if (switchJobSituation.isChecked) {
                editedJob.jobSituation = 1
            }else {
                editedJob.jobSituation = 0
            }

            val dm = DataManager(activity!!)
            dm.updateJob(editedJob, jobData.jobHeader.toString())

            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)                 //  This will get you to a previous activity keeping its top of the stack and clearing all activities down side of it from the stack.
            startActivity(intent)

        }

        return builder.create()

    }
}