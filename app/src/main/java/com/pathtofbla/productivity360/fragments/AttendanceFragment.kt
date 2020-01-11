package com.pathtofbla.productivity360.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pathtofbla.productivity360.Absence
import com.pathtofbla.productivity360.AttendanceRecyclerAdapter
import com.pathtofbla.productivity360.FormatDate
import com.pathtofbla.productivity360.R
import kotlinx.android.synthetic.main.fragment_attendance.*
import java.util.*

class AttendanceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attendance, container, false)
    }

    private val absences = mutableListOf<Absence>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //File IO TODO: get list of absences

        if (absences.isEmpty()){
            noabsencesView.visibility = View.VISIBLE
            attendanceRecyclerView.visibility = View.GONE
        } else {
            attendanceRecyclerView.layoutManager = LinearLayoutManager(this.context)
            val adapter = AttendanceRecyclerAdapter(mutableListOf())
            attendanceRecyclerView.adapter = adapter
        }


    }
}