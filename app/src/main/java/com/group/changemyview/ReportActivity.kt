package com.group.changemyview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val button = findViewById<Button>(R.id.report)
        button.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val db_users = FirebaseDatabase.getInstance().getReference("users")

            db_users.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var userFound = false
                    for (user in dataSnapshot.children) {
                        if (user.child("username").value == username) {
                            (user.child("reports").ref).setValue((user.child("reports").value as Int) + 1)
                            userFound = true
                            break
                        }
                    }
                    if (!userFound) {
                        Toast.makeText(applicationContext,
                            "The user you entered does not exist.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }


    }


}