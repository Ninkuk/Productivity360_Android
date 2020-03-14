package com.pathtofbla.productivity360

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pathtofbla.productivity360.fragments.AddSubjectDialog
import kotlinx.android.synthetic.main.info_dialog.view.*
import kotlinx.android.synthetic.main.subject_cells.view.*

class SubjectsRecyclerAdapter(
    private val subjects: MutableList<Subject>,
    private val fragmentManager: FragmentManager
) :
    RecyclerView.Adapter<SubjectsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.subject_cells, parent, false)
        val subject =
            Subject("", "", "","", "", Teacher("", ""), 0, 0)

        return SubjectsViewHolder(view, subject, subjects, this, fragmentManager)
    }

    override fun getItemCount() = subjects.size

    override fun onBindViewHolder(holder: SubjectsViewHolder, position: Int) {
        holder.subject = subjects[position]

        holder.view.className.text = subjects[position].title
        holder.view.building.text = subjects[position].building
        holder.view.classTime.text =
            subjects[position].startTime + " - " + subjects[position].endTime
        holder.view.professor.text = subjects[position].teacher.name

        val textColor =
            subjects[position].textColor + 4278190080.toInt() //adds the alpha values because setTextColor() requires an 8 digit hex number
        holder.view.subjectCard.setCardBackgroundColor(subjects[position].backgroundColor)
        holder.view.className.setTextColor(textColor)
        holder.view.building.setTextColor(textColor)
        holder.view.classTime.setTextColor(textColor)
        holder.view.professor.setTextColor(textColor)
    }
}

/**
 * @param subject - Used to know which subject object to edit
 * @param subjects - Used to delete and edit subjects in the list
 * @param adapter - Used to update the adapter after the data in subjects has changed
 * @param fragmentManager - Used to switch between fragments because the subject dialog is a fragment of its own
 */
class SubjectsViewHolder(
    val view: View,
    var subject: Subject,
    var subjects: MutableList<Subject>,
    var adapter: SubjectsRecyclerAdapter,
    val fragmentManager: FragmentManager
) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnLongClickListener {
            val builder =
                MaterialAlertDialogBuilder(view.context) //alertDialog to show description of subject
            val activity = view.context as AppCompatActivity
            val inflater = activity.layoutInflater
            val alertLayout = inflater.inflate(R.layout.info_dialog, null)

            alertLayout.dialogTitle.text = "Subject Info"
            alertLayout.dialogDescription.text = """Class Name: ${subject.title}
                            |Building: ${subject.building}
                            |Professor: ${subject.teacher.name}
                            |E-mail: ${subject.teacher.email}
                            |Address: ${subject.address}
                            |Start Time: ${subject.startTime}
                            |End Time: ${subject.endTime}
                        """.trimMargin()
            builder.setView(alertLayout)

            builder.setPositiveButton(
                "Edit"
            ) { dialogInterface: DialogInterface, _: Int ->
                val bundle = Bundle()
                bundle.putString("className", subject.title)
                bundle.putString("building", subject.building)
                bundle.putString("professor", subject.teacher.name)
                bundle.putString("email", subject.teacher.email)
                bundle.putString("address", subject.address)
                bundle.putString("timeStart", subject.startTime)
                bundle.putString("timeEnd", subject.endTime)
                bundle.putInt("subjectColor", subject.backgroundColor)
                bundle.putInt("subjectTextColor", subject.textColor)

                val newFragment = AddSubjectDialog()
                newFragment.arguments = bundle
                val transaction = fragmentManager.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.replace(R.id.fragment_container, newFragment).addToBackStack(null)
                    .commit()
            }.setNegativeButton(
                "Delete"
            ) { dialogInterface: DialogInterface, _: Int ->
                //TODO I/O operation to remove reward
                subjects.remove(subject)
                adapter.notifyDataSetChanged()
            }

            val alert = builder.create()
            alert.show()

            //removes the weird button BG and changes text color
            val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setBackgroundResource(0)
            negativeButton.setTextColor(Color.parseColor("#f44336"))
            val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setBackgroundResource(0)
            positiveButton.setTextColor(Color.parseColor("#FFCA28"))
            return@setOnLongClickListener true
        }
    }
}
