package com.pathtofbla.productivity360.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pathtofbla.productivity360.R
import com.pathtofbla.productivity360.Subject
import com.pathtofbla.productivity360.SubjectsRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_subjects.*


class SubjectsFragment: Fragment() {
    private val physics = Subject("PHY 121", "BYENG 120", "11:45 AM", "Dr. Alrifai", "#29B6F6")
    private val math = Subject("MAT 243", "BYENG 120", "11:45 AM", "Dr. Alrifai", "#FFF176")
    private val digital = Subject("CSE 120", "BYENG 120", "11:45 AM", "Dr. Alrifai", "#4CAF50")
    private val cs = Subject("CSE 205", "BYENG 120", "11:45 AM", "Dr. Alrifai", "#5E35B1")
    private val calc = Subject("MAT 265", "BYENG 120", "11:45 AM", "Dr. Alrifai", "#e53935")

    private val subjects = listOf(
        physics,
        math,
        digital,
        cs,
        calc,
        calc,
        calc,
        calc
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subjects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subjectsRecyclerView.layoutManager = LinearLayoutManager(context)
        subjectsRecyclerView.adapter = SubjectsRecyclerAdapter(subjects)

        addSubject.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Title")
                .setMessage("This is a Message")
                .setPositiveButton("Ok", DialogInterface.OnClickListener {
                        dialogInterface: DialogInterface, i: Int ->
                            Toast.makeText(
                                context,
                                "Positive button has been clicked",
                                Toast.LENGTH_LONG
                            ).show()
                }).setNegativeButton("No", DialogInterface.OnClickListener {
                        dialogInterface: DialogInterface, i: Int ->
                            Toast.makeText(
                                context,
                                "Ok",
                                Toast.LENGTH_LONG
                            ).show()
                })



            val inflater = layoutInflater
            val alertLayout = inflater.inflate(R.layout.add_subject_dialog, null)
            builder.setView(alertLayout)

            val alert = builder.create()
            alert.show()

            val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setBackgroundResource(0)
            negativeButton.setTextColor(Color.parseColor("#FFFFFF"))
            val positiveButton= alert.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setBackgroundResource(0)
            positiveButton.setTextColor(Color.parseColor("#FFC107"))
        }
    }
}