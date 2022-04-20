package com.android.e_learning.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.e_learning.R
import java.util.ArrayList

class QuizzesAdapter(val onClicks: (String?) -> Unit) : RecyclerView.Adapter<QuizzesAdapter.MyNewHolder>() {

    private var listOfQuizzes = ArrayList<String>()

    fun setListOfQuizzes(list: ArrayList<String>){
        this.listOfQuizzes = list
        notifyItemChanged(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stud_quizzes, parent, false)
        return MyNewHolder(view)

    }

    override fun onBindViewHolder(holder: MyNewHolder, position: Int) {
        holder.coursePosition.text = "Quiz " + (position + 1)
    }

    override fun getItemCount(): Int = if (listOfQuizzes!!.size == null) 0 else listOfQuizzes.size

    inner class MyNewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val coursePosition: TextView = itemView.findViewById(R.id.quizCounter)

        init {
            itemView.setOnClickListener {
                onClicks.invoke(listOfQuizzes[layoutPosition])
            }
        }
    }

}