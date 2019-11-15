package com.group.changemyview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioGroup
import android.widget.TextView
import java.util.*

class QuestionListAdapter(private val mContext: Context,
                          private val dataSource: ArrayList<QuestionItem>) : BaseAdapter() {

    private val mItems = dataSource
    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    // Returns the number of QuestionItems
    override fun getCount(): Int {
        return mItems.size
    }

    // Retrieve the specific ToDoItem at pos
    override fun getItem(pos: Int): Any {
        return mItems[pos]
    }

    // Get the ID for the QuestionItem
    // In this case it's just the position
    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        Log.i(TAG, "${javaClass.simpleName}::entered getView()")

        val newView = mLayoutInflater.inflate(R.layout.question_item, parent, false)

        // get views
        val mQuestionNumView = newView.findViewById(R.id.question_number) as TextView
        val mQuestionTextView = newView.findViewById(R.id.question_text) as TextView

        // set data
        val item = getItem(position) as QuestionItem
        mQuestionNumView.text = "Question ${item.mQuestionNum.toString()}"
        mQuestionTextView.text = item.mQuestion

        return newView
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }
}
