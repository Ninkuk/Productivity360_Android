package com.pathtofbla.productivity360

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.attendance_cells.view.*

class AttendanceRecyclerAdapter(private val absences: List<Absence>) :
    RecyclerView.Adapter<AttendanceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.attendance_cells, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun getItemCount() = absences.size

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        holder.view.className.text = absences[position].title
        holder.view.dateMissed.text = absences[position].date.toString()
    }
}

class AttendanceViewHolder(val view: View) : RecyclerView.ViewHolder(view)
