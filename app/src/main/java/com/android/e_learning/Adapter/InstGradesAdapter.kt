package com.android.e_learning.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.e_learning.Model.UserAnswerModel
import com.android.e_learning.R

class InstGradesAdapter : RecyclerView.Adapter<InstGradesAdapter.MyGradesHolder>(){

    lateinit var mList: ArrayList<UserAnswerModel>

    fun setListToAdapter(list: ArrayList<UserAnswerModel>){
        this.mList = list
        notifyItemChanged(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGradesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inst_grades, parent, false)
        return MyGradesHolder(view)
    }

    override fun onBindViewHolder(holder: MyGradesHolder, position: Int) {
        holder.studeName.text = mList[position].userName
        holder.studeEmail.text = mList[position].email
        holder.studeGrade.text = mList[position].grade
    }

    override fun getItemCount(): Int = if (mList.size == null) 0 else mList.size

    inner class MyGradesHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val studeName: TextView = itemView.findViewById(R.id.item_grade_StudentName_TV)
        val studeEmail: TextView = itemView.findViewById(R.id.item_grade_StudentEmail_TV)
        val studeGrade: TextView = itemView.findViewById(R.id.item_grade_StudentGrade_TV)
    }
}