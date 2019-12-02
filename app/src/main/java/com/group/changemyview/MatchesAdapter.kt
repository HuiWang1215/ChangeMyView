package com.group.changemyview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MatchesAdapter(private val activity: Activity, matchList: ArrayList<Match>) : BaseAdapter() {

    private var matchList = ArrayList<Match>()

    init {
        this.matchList = matchList as ArrayList
    }

    override fun getCount(): Int {
        return matchList.size
    }

    override fun getItem(i: Int): Any {
        return matchList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val vi = inflater.inflate(R.layout.match_item, null)
        val question = vi.findViewById<TextView>(R.id.question)
        val email = vi.findViewById<TextView>(R.id.email)
        val answer = vi.findViewById<TextView>(R.id.answer)
        question.text = "Question: " + matchList[i].question
        email.text = "Email: " + matchList[i].email
        answer.text = "Your Answer: " + matchList[i].answer
        return vi
    }
}