package com.android.e_learning.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.e_learning.Model.SubscribedCoursesModel
import com.android.e_learning.R
import java.util.ArrayList

class StudCoursesAdapter(val onClick: (String?) -> Unit) : RecyclerView.Adapter<StudCoursesAdapter.MyHolder>() {

    private var listOfStudentCourses = ArrayList<SubscribedCoursesModel>()

    fun setStudentCoursesList(list: ArrayList<SubscribedCoursesModel>) {
        this.listOfStudentCourses = list
        notifyItemChanged(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.courseName.text = listOfStudentCourses[position].courseName
    }

    override fun getItemCount(): Int =
        if (listOfStudentCourses.size == null) 0 else listOfStudentCourses.size

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseName: TextView = itemView.findViewById(R.id.item_course_Name_TV)

        init {
            itemView.setOnClickListener {
                onClick.invoke(listOfStudentCourses[position].courseId)
            }

        }
    }

}