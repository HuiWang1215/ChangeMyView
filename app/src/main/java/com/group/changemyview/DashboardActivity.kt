package com.group.changemyview

import android.app.ListActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.widget.*


class DashboardActivity : ListActivity() {


    internal lateinit var mAdapter: QuestionListAdapter
    private var listView: ListView? = null

    private var mAnswerRadioGroup: RadioGroup? = null

    var questionList = arrayListOf<QuestionItem>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)

        var index = 1

        val db = FirebaseDatabase.getInstance().getReference("Questions")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val questionText = snapshot.key
                    val questionItem = QuestionItem(index, questionText!!, QuestionItem.Answer.NO_ANSWER)
                    questionList.add(questionItem)
                    index++
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        listView = findViewById<ListView>(android.R.id.list)
        mAdapter = QuestionListAdapter(this, questionList)
        listView!!.adapter = mAdapter
    }

    override fun getListView(): ListView? {
        return listView!!
    }

    fun processAnswers(v: View) {

        for (i in 0 until mAdapter.count) {
            val data = mAdapter.getItem(i) as QuestionItem
            
            mAnswerRadioGroup = findViewById<View>(R.id.answerGroup) as RadioGroup

            when {
                mAnswerRadioGroup!!.checkedRadioButtonId == R.id.answerYes -> data.mAnswer = QuestionItem.Answer.YES
                mAnswerRadioGroup!!.checkedRadioButtonId == R.id.answerNo -> data.mAnswer = QuestionItem.Answer.NO
                else -> data.mAnswer = QuestionItem.Answer.NO_ANSWER
            }

            Log.i(TAG, "${data.mQuestion} " + data.mAnswer.toString())
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
