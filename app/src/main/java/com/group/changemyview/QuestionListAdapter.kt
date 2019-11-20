package com.group.changemyview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class QuestionListAdapter(private val mContext: Context,
                          private val dataSource: ArrayList<QuestionItem>) : BaseAdapter() {

    private val mItems = dataSource
    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

//    fun add(item: ToDoItem) {
//
//        mItems.add(item)
//        notifyDataSetChanged()
//
//    }

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


    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val viewHolder: ViewHolder
        val newView: View

        if (null == convertView) {

            // TODO - Inflate the View for this ToDoItem
            newView = mLayoutInflater.inflate(R.layout.question_item, parent, false)

            viewHolder = ViewHolder()
            newView.tag = viewHolder
            viewHolder.mQuestionNumber = newView.findViewById(R.id.question_number)
            viewHolder.mQuestionText = newView.findViewById(R.id.question_text)
            viewHolder.mAnswerView = newView.findViewById(R.id.answerGroup)

        } else {
            viewHolder = convertView.tag as ViewHolder
            viewHolder.mAnswerView!!.setOnCheckedChangeListener(null)
            newView = convertView

        }

        // TODO - Fill in specific Question Item data
        // Remember that the data that goes in this View
        // corresponds to the user interface elements defined
        // in the layout file
        val storeViewHolder = newView.tag as ViewHolder

        // TODO - Display Title in TextView
        val item = getItem(position) as QuestionItem
        storeViewHolder.mQuestionNumber!!.text = "Question ${item.mQuestionNum.toString()}"

        // TODO - Display Priority in a TextView
        storeViewHolder.mQuestionText!!.text = item.mQuestion

        return newView
    }

    internal class ViewHolder {
        var position: Int = 0
        var mItemLayout: RelativeLayout? = null
        var mQuestionNumber: TextView? = null
        var mQuestionText: TextView? = null
        var mAnswerView: RadioGroup? = null
    }

    companion object {
        private val TAG = "FinalProject-ChangeMyView"
    }
}
