package com.group.changemyview

import android.app.ListActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardActivity : ListActivity() {

    private var listView: ListView? = null
    var questionList = arrayListOf<QuestionItem>()
    private var submitButton: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)

        var db = FirebaseDatabase.getInstance().getReference("Questions")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val questionText = snapshot.key
                    val questionItem = QuestionItem(1, questionText!!, QuestionItem.Answer.NO_ANSWER)
                    questionList.add(questionItem)
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        listView = findViewById<ListView>(android.R.id.list)
        val adapter = QuestionListAdapter(this, questionList)
        listView!!.adapter = adapter

//        submitButton = findViewById(R.id.submitButton)
//
//        submitButton!!.setOnClickListener() {
//            fun onClick(View v) {
//                processAnswers()
//            }
//        }
    }

    override fun getListView(): ListView? {
        return listView!!
    }

    fun processAnswers() {

        for (q in questionList) {
            Log.i(TAG, "Question is ${q.mQuestion}")
//            val answerGroup = findViewById<RadioGroup>(R.id.answerGroup)
//            val selectedButton = answerGroup.checkedRadioButtonId as RadioButton

        }
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }

}


//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Menu
//import com.google.firebase.auth.FirebaseAuth
//
//class DashboardActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dashboard)
//
//        val uid = FirebaseAuth.getInstance().uid
//        if (uid == null) {
//            val intent = Intent(this@DashboardActivity, MainActivity::class.java)
//            intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate()
//
//        return super.onCreateOptionsMenu(menu)
//    }
//}
