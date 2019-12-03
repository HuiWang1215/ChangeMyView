package com.group.changemyview

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SavedQuestionAnswersActivity : AppCompatActivity() {


    lateinit var mAdapter: QuestionListAdapter
    private var listView: ListView? = null
    internal var topLayout: LinearLayout? = null

    private var questionList : MutableList<QuestionItem>? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.saved_questions_activity)

        topLayout = findViewById(R.id.linearLayoutContainer)
        listView = findViewById(android.R.id.list)

        // resize top image
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val savedQuestionsBitmap = BitmapFactory.decodeResource(resources,
            R.drawable.saved_questions_background)
        val savedQuestionsScaledBitmap = Bitmap.createScaledBitmap(savedQuestionsBitmap, width,
            height/3, false)
        val savedQuestionsDrawable = BitmapDrawable(resources, savedQuestionsScaledBitmap)
        topLayout!!.background = savedQuestionsDrawable

        // get current user
        val user = FirebaseAuth.getInstance().currentUser!!.uid

        questionList = mutableListOf()
        var index = 0

        val db = FirebaseDatabase.getInstance().getReference("Questions")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {

                    for (child in snapshot.children) {

                        if (child.key == user) {

                            val answer = when (child.child("answer").value) {
                                "Yes" -> QuestionItem.Answer.YES
                                "No" -> QuestionItem.Answer.NO
                                else -> QuestionItem.Answer.NO_ANSWER
                            }

                            questionList!!.add(QuestionItem(index + 1, snapshot.key!!, answer))
                            index += 1
                        }
                    }
                }

                mAdapter = QuestionListAdapter(applicationContext, questionList!!)
                listView!!.adapter = mAdapter

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        // update question from click
        listView!!.setOnItemClickListener { parent, view, position, id ->
            val myItem = parent.getItemAtPosition(position) as QuestionItem
            val mIntent = Intent(this@SavedQuestionAnswersActivity,
                UpdateQuestionActivity::class.java)
            mIntent.putExtra("Question text", myItem.mQuestion)
            startActivity(mIntent)
        }
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }

}
