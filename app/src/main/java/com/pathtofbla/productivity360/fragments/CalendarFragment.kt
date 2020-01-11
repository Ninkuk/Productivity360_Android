package com.pathtofbla.productivity360.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.pathtofbla.productivity360.FormatDate
import com.pathtofbla.productivity360.R
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        Log.d("YO", "yo")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_calendar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switchCalendar -> {
                //TODO:Change to the other string and switch view
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

        }

//        val months = mutableListOf(
//            "January",
//            "February",
//            "March",
//            "April",
//            "May",
//            "June",
//            "July",
//            "August",
//            "September",
//            "October",
//            "November",
//            "December"
//        )

    }
}