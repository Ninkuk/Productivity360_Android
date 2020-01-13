package com.pathtofbla.productivity360.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.pathtofbla.productivity360.R
import com.pathtofbla.productivity360.Subject
import com.pathtofbla.productivity360.SubjectsRecyclerAdapter
import com.pathtofbla.productivity360.Teacher
import kotlinx.android.synthetic.main.fragment_subjects.*


class SubjectsFragment : Fragment() {

    private val subjects = mutableListOf<Subject>() //TODO I/O operation to get subjects

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subjects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragmentManager = fragmentManager!!

        subjectsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = SubjectsRecyclerAdapter(subjects, fragmentManager)
        subjectsRecyclerView.adapter = adapter

        addSubject.setOnClickListener {
            val newFragment = AddSubjectDialog()
            newFragment.arguments = null

            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.replace(R.id.fragment_container, newFragment).addToBackStack(null)
                .commit()
        }

        val bundle = this.arguments
        if (bundle != null) {
            if (!bundle.getBoolean("isEdit")) {
                val className = bundle.getString("className")!!
                val building = bundle.getString("building")!!
                val professor = bundle.getString("professor")!!
                val email = bundle.getString("email")!!
                val timeStart = bundle.getString("timeStart")!!
                val timeEnd = bundle.getString("timeEnd")!!
                val subjectColor = bundle.getInt("subjectColor")!!
                val subjectTextColor = bundle.getInt("subjectTextColor")!!

                val subject = Subject(
                    className,
                    building,
                    timeStart,
                    timeEnd,
                    Teacher(professor, email),
                    subjectColor,
                    subjectTextColor
                )

                subjects.add(subject) //TODO I/O operation to write new subject
                adapter.notifyDataSetChanged()
            } else {
                val oldClassName = bundle.getString("className")!!
                val oldBuilding = bundle.getString("building")!!
                val oldProfessor = bundle.getString("professor")!!
                val oldEmail = bundle.getString("email")!!
                val oldTimeStart = bundle.getString("timeStart")!!
                val oldTimeEnd = bundle.getString("timeEnd")!!
                val oldSubjectColor = bundle.getInt("subjectColor")!!
                val oldSubjectTextColor = bundle.getInt("subjectTextColor")!!

                val oldSubject = Subject(
                    oldClassName,
                    oldBuilding,
                    oldTimeStart,
                    oldTimeEnd,
                    Teacher(oldProfessor, oldEmail),
                    oldSubjectColor,
                    oldSubjectTextColor
                )

                val className = bundle.getString("newClassName")!!
                val building = bundle.getString("newBuilding")!!
                val professor = bundle.getString("newProfessor")!!
                val email = bundle.getString("newEmail")!!
                val timeStart = bundle.getString("newTimeStart")!!
                val timeEnd = bundle.getString("newTimeEnd")!!
                val subjectColor = bundle.getInt("newSubjectColor")!!
                val subjectTextColor = bundle.getInt("newSubjectTextColor")!!

                val subject = Subject(
                    className,
                    building,
                    timeStart,
                    timeEnd,
                    Teacher(professor, email),
                    subjectColor,
                    subjectTextColor
                )

//                TODO change this code when I/O operations to read are available
//                val index = subjects.indexOf(oldSubject)
//                subjects[index] = subject
                subjects.add(subject)

                adapter.notifyDataSetChanged() //TODO I/O operation to edit reward
            }
        }
    }
}