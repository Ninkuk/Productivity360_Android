package com.pathtofbla.productivity360

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.subject_cells.view.*

class SubjectsRecyclerAdapter(private val subjects: List<Subject>): RecyclerView.Adapter<SubjectsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_cells, parent, false)
        return SubjectsViewHolder(view)
    }

    override fun getItemCount() = subjects.size

    override fun onBindViewHolder(holder: SubjectsViewHolder, position: Int) {
        holder.view.className.text = subjects[position].name
        holder.view.building.text = subjects[position].building
        holder.view.classTime.text = subjects[position].time
        holder.view.professor.text = subjects[position].professor
        holder.view.subjectCard.setCardBackgroundColor(Color.parseColor(subjects[position].color))
    }
}

class SubjectsViewHolder(val view: View): RecyclerView.ViewHolder(view)
