package com.android.e_learning.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.e_learning.Model.CourseModel
import com.android.e_learning.R

class InstCoursesAdapter(val OnClick: (String?) -> Unit) : RecyclerView.Adapter<InstCoursesAdapter.MyViewHolder>() {

    var list: ArrayList<CourseModel>? = null

    fun setData(list : ArrayList<CourseModel>){
        this.list = list
        notifyItemChanged(list.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.name_widget.text = list?.get(position)?.courseName
    }

    override fun getItemCount(): Int = if (list == null) 0 else list?.size!!



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_widget: ImageView = itemView.findViewById(R.id.item_course_photo)
        var name_widget: TextView = itemView.findViewById(R.id.item_course_Name_TV)

        init {
            itemView.setOnClickListener{
                OnClick.invoke(list?.get(layoutPosition)?.courseId)
            }
        }
    }

}