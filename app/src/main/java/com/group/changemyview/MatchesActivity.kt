package com.group.changemyview

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MatchesActivity : AppCompatActivity() {


    val NO_MATCHES = "No other users answered this question with an opposing view point."
    val UNANSWERED = "You did not answer this question."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        val currentActivity = this
        val currentUserUID = FirebaseAuth.getInstance().currentUser!!.uid
        val listOfMatches = ArrayList<Match>()
        val listView:ListView = findViewById(android.R.id.list)

        var from = ""
        val db_users = FirebaseDatabase.getInstance().getReference("users")
        db_users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
              from = dataSnapshot.child(currentUserUID).child("username").value as String
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


        val db_questions = FirebaseDatabase.getInstance().getReference("Questions")
        db_questions.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val potentialMatches = ArrayMap<String, String>()
                    val question = snapshot.key
                    var currentUsersAnswer = ""

                    val currentUserAnsweredQuestion = snapshot.child(currentUserUID)
                    if (currentUserAnsweredQuestion.exists()) {
                        currentUsersAnswer = currentUserAnsweredQuestion.child("answer").value as String
                    }
                    if (currentUsersAnswer == "") {
                        listOfMatches.add(Match(question!!, UNANSWERED, "None", "None"))
                    }
                    else {
                        for (user in snapshot.children) {
                            if (user.child("answer").value as String != currentUsersAnswer)
                            {
                                val username = user.child("username").value as String
                                potentialMatches[username] = user.child("email").value as String
                            }
                        }
                        if (potentialMatches.size == 0) {
                            listOfMatches.add(Match(question!!, currentUsersAnswer, NO_MATCHES, "None"))
                        }
                        else {
                            val to = potentialMatches.keys.random()
                            listOfMatches.add(Match(question!!, currentUsersAnswer, potentialMatches[to]!!, to))
                        }
                    }
                }
                val adapter = MatchesAdapter(currentActivity, listOfMatches)
                listView.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            val item = listView.getItemAtPosition(position) as Match
            if (item.answer != UNANSWERED && item.answer != NO_MATCHES) {
                var intent = createEmail(item.email, item.question)
                startActivity(Intent.createChooser(intent, "Send Email"))
            }
            else {
                Toast.makeText(this,
                    "You were not matched for this question.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createEmail(email : String, question: String) : Intent{
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Change my View App")
        intent.putExtra(Intent.EXTRA_TEXT, "Hi, \n\n we were matched on Change my View app for a " +
                "debate on the topic: \n\n" + question + "\n\n Please respond back if you would like to " +
                "chat.")
        return intent
    }



}