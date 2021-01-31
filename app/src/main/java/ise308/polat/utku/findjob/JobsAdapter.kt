package ise308.polat.utku.findjob

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ise308.polat.utku.findjob.db.DataManager


class JobsAdapter(private var joblist: ArrayList<JobData>, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<JobsAdapter.JobsViewHolder>(){

    inner class JobsViewHolder(jobsItemView: View) : RecyclerView.ViewHolder(jobsItemView) {
        internal var textViewJobHeader = jobsItemView.findViewById<TextView>(R.id.tv_job_header)
        internal var textViewJobInformation = jobsItemView.findViewById<TextView>(R.id.tv_job_information)
        internal var textViewContactInformation = jobsItemView.findViewById<TextView>(R.id.tv_job_contact)

        internal var editImage = jobsItemView.findViewById<ImageView>(R.id.edit_image_view)
        internal var deleteImage = jobsItemView.findViewById<ImageView>(R.id.delete_image_view)

        var cardView = jobsItemView.findViewById(R.id.cardView) as CardView         //For change background color of CardView


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val jobsItemInflater = LayoutInflater.from(parent.context)
        val jobsItemView = jobsItemInflater.inflate(R.layout.jobs_item_view, parent, false)
        return JobsViewHolder(jobsItemView)
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val jobsForDisplay = joblist.get(position)

        if (jobsForDisplay.jobSituation == 1) {
            holder.cardView.setCardBackgroundColor(Color.GREEN)     //Changing background color of cardview. Job Situation = 1
        } else {
            holder.cardView.setCardBackgroundColor(Color.RED)       //Changing background color of cardview. Job Situation = 0
        }

        holder.textViewJobHeader.text = jobsForDisplay.jobHeader.toString()
        holder.textViewJobInformation.text = jobsForDisplay.jobInfo.toString()
        holder.textViewContactInformation.text = jobsForDisplay.contactInfo.toString()

        holder.editImage.setOnClickListener {
            val editDialog = EditJobDialog(jobsForDisplay)                          //For update operation, search not editted header in database and change the new datas which entered with dialog fragment
            editDialog.show(mainActivity.supportFragmentManager,"123")
        }
        holder.deleteImage.setOnClickListener {
            val builder = AlertDialog.Builder(mainActivity)

            builder.setTitle("Delete")
            builder.setMessage("Are you sure to delete this job advertisement?")

            builder.setPositiveButton("Yes"){ _, which ->
                Toast.makeText(mainActivity,"Job is deleted",Toast.LENGTH_LONG).show()
                val dm = DataManager(mainActivity)
                dm.removeJob(jobsForDisplay.jobHeader.toString())

                val intent = Intent(mainActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)                     //This will get you to a previous activity keeping its stack and clearing all activities after it from the stack.
                mainActivity.startActivity(intent)
            }
            builder.setNegativeButton("No"){ _, which ->
                Toast.makeText(mainActivity,"Job is not deleted",Toast.LENGTH_LONG).show()
            }

            builder.show()

        }
    }

    override fun getItemCount() = joblist.size
}