package ise308.polat.utku.findjob

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ise308.polat.utku.findjob.db.DataManager

class MainActivity : AppCompatActivity() {

    private lateinit var jobList : ArrayList<JobData>
    private var recyclerViewJobs : RecyclerView? = null
    private var jobsAdapter : JobsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadJobs()

        recyclerViewJobs = findViewById<RecyclerView>(R.id.recyclerViewJobs)
        jobsAdapter = JobsAdapter(jobList, this)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewJobs!!.layoutManager = layoutManager
        recyclerViewJobs!!.itemAnimator = DefaultItemAnimator()

        recyclerViewJobs!!.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerViewJobs!!.adapter = jobsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_add_new_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.action_add_new_item)  {                  // If the user click the new job icon in the action bar
            val intent = Intent(this, AddNewJobActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadJobs() {
        val dm = DataManager(applicationContext)
        jobList = dm.resultsAllJob()

    }
}