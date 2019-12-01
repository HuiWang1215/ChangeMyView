package com.group.changemyview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import android.widget.TextView

class QuestionListAdapter(private val mContext: Context,
                          private val dataSource: MutableList<QuestionItem>) : BaseAdapter() {

    private val mItems = dataSource
    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    var mQuestionText: TextView? = null
    var mAnswerText: TextView? = null

    // Returns the number of QuestionItems
    override fun getCount(): Int {
        return mItems.size
    }

    // Retrieve the specific QuestionItem at pos
    override fun getItem(pos: Int): Any {
        return mItems[pos]
    }

    // Get the ID for the QuestionItem
    // In this case it's just the position
    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }


    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val v = mLayoutInflater.inflate(R.layout.question_item, parent, false)
        val currQuestion = getItem(position) as QuestionItem

        val answerString = when {
            currQuestion.mAnswer == QuestionItem.Answer.YES -> "Yes"
            currQuestion.mAnswer == QuestionItem.Answer.NO -> "No"
            else -> "None"
        }

        // find resources
        mQuestionText = v.findViewById(R.id.question_num_text) as TextView
        mAnswerText = v.findViewById(R.id.answer_text) as TextView

        // update text
        mQuestionText!!.text = currQuestion.mQuestionNum!!.toString() + ". " + currQuestion.mQuestion!!
        mAnswerText!!.text = answerString

        return v
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }
}
