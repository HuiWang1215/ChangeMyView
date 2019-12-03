package com.group.changemyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.database.*
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class UpdateQuestionActivity : AppCompatActivity() {

    var yesBtn: Button? = null
    var noBtn: Button? = null
    var mQuestionView: TextView? = null
    var mQuestionText: String? = null
    val user = FirebaseAuth.getInstance().currentUser
    var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // set username
        val db = FirebaseDatabase.getInstance().getReference("users")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.key == user!!.uid)
                        username = snapshot.child("username").value.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        mQuestionView = findViewById(R.id.question)
        mQuestionText = intent.getStringExtra("Question text") as String
        mQuestionView!!.setText(mQuestionText)

        chooseAnswer()
    }

    private fun chooseAnswer() {
        yesBtn = findViewById(R.id.yesButton)
        noBtn = findViewById(R.id.noButton)
        yesBtn!!.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/Questions/$mQuestionText/${user!!.uid}")
            val answer = Answer(user.email!!,"Yes", username)
            ref.setValue(answer)

            startActivity(Intent(this@UpdateQuestionActivity, SavedQuestionAnswersActivity::class.java))

        }
        noBtn!!.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/Questions/$mQuestionText/${user!!.uid}")
            val answer = Answer(user.email!!,"No", username)
            ref.setValue(answer)

            startActivity(Intent(this@UpdateQuestionActivity, SavedQuestionAnswersActivity::class.java))
        }
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }
}