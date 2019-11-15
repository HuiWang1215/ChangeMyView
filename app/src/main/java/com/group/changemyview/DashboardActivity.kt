package com.group.changemyview


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this@DashboardActivity, MainActivity::class.java)
            intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate()

        return super.onCreateOptionsMenu(menu)
    }
}
