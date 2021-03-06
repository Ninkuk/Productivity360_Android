package com.pathtofbla.productivity360.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.common.base.Ascii.toUpperCase
import com.pathtofbla.productivity360.R
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.add_subject_dialog.*
import kotlinx.android.synthetic.main.add_subject_dialog.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.color_picker_dialog.view.*
import java.util.*


class AddSubjectDialog : Fragment(), View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private var lastColorClicked: Int = 0xFFFFFFFF.toInt()
    private var currentSubjectColor: Int = 0xFFF44336.toInt() //Red
    private var subjectTextColor: Int = 0xFFFFFFFF.toInt()
    lateinit var alertLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_subject_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity!!.toolbar.visibility = GONE //hides the menu

        closeButton.setOnClickListener {
            cancel()
        }

        timeStart()
        timeEnd()
        chooseSubjectColor()

        subjectCancel.setOnClickListener {
            cancel()
        }

        val bundle = this.arguments
        if (bundle == null) {
            createMode()
        } else {
            editMode(bundle)
        }
    }

    private fun timeStart() {
        timeStartLayout.setOnClickListener {
            val now = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.isThemeDark = true
            timePickerDialog.setAccentColor("#42A5F5")
            timePickerDialog.setOkColor("#FFCA28")
            timePickerDialog.setCancelColor("#DEFFFFFF")
            timePickerDialog.title = "Class Starting Time"
            timePickerDialog.show(fragmentManager!!, "DatePicker")
        }
    }

    private fun timeEnd() {
        timeEndLayout.setOnClickListener {
            val now = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.isThemeDark = true
            timePickerDialog.setAccentColor("#42A5F5")
            timePickerDialog.setOkColor("#FFCA28")
            timePickerDialog.setCancelColor("#DEFFFFFF")
            timePickerDialog.title = "Class Ending Time"
            timePickerDialog.show(fragmentManager!!, "DatePicker")

            it.timeStart
        }
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {

        //adds a 0 to the tens digit place if minute is less than 10
        val minuteWithZero = if (minute < 10) {
            "0$minute"
        } else {
            minute.toString()
        }

        //converts 24 hour notation to 12 hour
        val time = if (hourOfDay >= 13) {
            "${hourOfDay - 12}:$minuteWithZero PM"
        } else {
            "$hourOfDay:$minuteWithZero AM"
        }

        //checks which textView to change
        if (view!!.title == "Class Starting Time") {
            timeStart.text = time
            return
        }

        timeEnd.text = time
    }

    private fun chooseSubjectColor() {
        subjectColorImage.setColorFilter(0xFFF44336.toInt()) //sets the red color as the default bg

        backgroundColorLayout.setOnClickListener {
            val colorBuilder = MaterialAlertDialogBuilder(context)
            val inflater = layoutInflater
            alertLayout = inflater.inflate(R.layout.color_picker_dialog, null)
            colorBuilder.setView(alertLayout)

            alertLayout.colorPickerTitle.text = "Choose a Background Color"
            lastColorClicked = currentSubjectColor
            alertLayout.chosenColor.setColorFilter(currentSubjectColor)
            val hexString =
                "#" + toUpperCase(Integer.toHexString(currentSubjectColor).removeRange(0, 2))
            alertLayout.hexTextInputEditText.setText(hexString)
            colorsOnClickSetup(alertLayout)

            colorBuilder.setPositiveButton(
                "Choose"
            ) { dialog: DialogInterface?, which: Int ->
                subjectColorImage.setColorFilter(lastColorClicked)
                currentSubjectColor = lastColorClicked
                chooseSubjectTextColor()
            }.setNegativeButton(
                "Cancel"
            ) { dialogInterface: DialogInterface, _: Int ->
                lastColorClicked = currentSubjectColor
            }

            alertLayout.hexTextInputEditText.addTextChangedListener {
                try {
                    val hexColor = Color.parseColor(it.toString())
                    alertLayout.chosenColor.setColorFilter(hexColor)
                    lastColorClicked = hexColor
                    removeCheckmarks()
                } catch (ignored: Exception) {

                }
            }

            val alert = colorBuilder.create()
            alert.show()

            //changes the negative and positive buttons color
            val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setBackgroundResource(0)
            negativeButton.setTextColor(Color.parseColor("#DEFFFFFF"))
            val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setBackgroundResource(0)
            positiveButton.setTextColor(Color.parseColor("#FFCA28"))
        }
    }

    private fun chooseSubjectTextColor() {
        val hex = Integer.toHexString(currentSubjectColor)
        val red = Integer.parseInt(hex.substring(0, 2), 16)
        val green = Integer.parseInt(hex.substring(2, 4), 16)
        val blue = Integer.parseInt(hex.substring(4, 6), 16)

        subjectTextColor = if((red*0.299 + green*0.587 + blue*0.114) > 186) {
            0xFF000000.toInt()
        } else {
            0xFFFFFFFF.toInt()
        }
    }

    //by passing "this" to the onClickListener, Android will call the onClick method I overrode
    private fun colorsOnClickSetup(view: View) {
        view.redColor.setOnClickListener(this)
        view.pinkColor.setOnClickListener(this)
        view.purpleColor.setOnClickListener(this)
        view.deepPurpleColor.setOnClickListener(this)
        view.indigoColor.setOnClickListener(this)
        view.blueColor.setOnClickListener(this)
        view.lightBlueColor.setOnClickListener(this)
        view.cyanColor.setOnClickListener(this)
        view.tealColor.setOnClickListener(this)
        view.greenColor.setOnClickListener(this)
        view.lightGreenColor.setOnClickListener(this)
        view.limeColor.setOnClickListener(this)
        view.yellowColor.setOnClickListener(this)
        view.amberColor.setOnClickListener(this)
        view.orangeColor.setOnClickListener(this)
        view.deepOrangeColor.setOnClickListener(this)
        view.brownColor.setOnClickListener(this)
        view.grayColor.setOnClickListener(this)
        view.blueGrayColor.setOnClickListener(this)
        view.whiteColor.setOnClickListener(this)
    }

    //override the onClick method so that I only have to write one onclick code for all the colors
    override fun onClick(view: View?) {
        lastColorClicked = ImageViewCompat.getImageTintList(view as ImageView)!!.defaultColor
        val hexString = "#" + toUpperCase(Integer.toHexString(lastColorClicked).removeRange(0, 2))
        alertLayout.hexTextInputEditText.setText(hexString)
        alertLayout.chosenColor.setColorFilter(lastColorClicked)

        removeCheckmarks() //unchecks any checked color
        view.setImageResource(R.drawable.ic_outline_circle_black_48dp) //adds a check mark to the clicked image
    }

    //removes all checkMarks
    private fun removeCheckmarks() {
        //The id of each color
        val colorsTagList = listOf(
            "redColor",
            "pinkColor",
            "purpleColor",
            "deepPurpleColor",
            "indigoColor",
            "blueColor",
            "lightBlueColor",
            "cyanColor",
            "tealColor",
            "greenColor",
            "lightGreenColor",
            "limeColor",
            "yellowColor",
            "amberColor",
            "orangeColor",
            "deepOrangeColor",
            "brownColor",
            "grayColor",
            "blueGrayColor",
            "whiteColor"
        )

        colorsTagList.forEach {
            (alertLayout.findViewWithTag<View>(it) as ImageView).setImageResource(R.drawable.ic_brightness_1_white_48dp)
        }
    }

    private fun cancel() {
        val fragmentManager = fragmentManager
        val newFragment = SubjectsFragment()
        newFragment.arguments = null

        activity!!.toolbar.visibility = View.VISIBLE //makes the hidden menu visible again
        val transaction = fragmentManager?.beginTransaction()
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        transaction?.replace(R.id.fragment_container, newFragment)?.commit()
    }

    private fun createMode() {
        subjectCreate.setOnClickListener {
            val className = classNameTextView.text.toString()
            val building = buildingTextView.text.toString()
            val professor = professorTextView.text.toString()
            val email = emailTextView.text.toString()
            val address = addressTextView.text.toString()
            val timeStart = timeStart.text.toString()
            val timeEnd = timeEnd.text.toString()

            if (className.isEmpty()) {
                classNameTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                classNameTextView.error = null
            }

            if (building.isEmpty()) {
                buildingTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                buildingTextView.error = null
            }

            if (professor.isEmpty()) {
                professorTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                professorTextView.error = null
            }

            if (email.isEmpty()) {
                emailTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                emailTextView.error = null
            }

            if (address.isEmpty()) {
                addressTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                addressTextView.error = null
            }

            if (timeStart == "Pick a time") {
                Toast.makeText(context, "Please choose a starting time", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (timeEnd == "Pick a time") {
                Toast.makeText(context, "Please choose an ending time", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val fragmentManager = fragmentManager
            val newFragment = SubjectsFragment()

            val bundle = Bundle()
            bundle.putString("className", className)
            bundle.putString("building", building)
            bundle.putString("professor", professor)
            bundle.putString("email", email)
            bundle.putString("address", address)
            bundle.putString("timeStart", timeStart)
            bundle.putString("timeEnd", timeEnd)
            bundle.putInt("subjectColor", currentSubjectColor)
            bundle.putInt("subjectTextColor", subjectTextColor)
            bundle.putBoolean("isEdit", false)
            newFragment.arguments = bundle

            //clears the back stack
            for (i in 0 until fragmentManager!!.backStackEntryCount) {
                fragmentManager.popBackStack()
            }

            activity!!.toolbar.visibility = View.VISIBLE
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            transaction.replace(R.id.fragment_container, newFragment).commit()
        }
    }

    private fun editMode(bundle: Bundle) {
        classNameTextView.setText(bundle.getString("className"))
        buildingTextView.setText(bundle.getString("building"))
        professorTextView.setText(bundle.getString("professor"))
        emailTextView.setText(bundle.getString("email"))
        addressTextView.setText(bundle.getString("address"))
        timeStart.text = bundle.getString("timeStart")
        timeEnd.text = bundle.getString("timeEnd")
        currentSubjectColor = bundle.getInt("subjectColor")
        subjectColorImage.setColorFilter(currentSubjectColor)
        subjectTextColor = bundle.getInt("subjectTextColor")

        subjectCreate.text = "Edit"
        subjectCreate.setOnClickListener {
            val className = classNameTextView.text.toString()
            val building = buildingTextView.text.toString()
            val professor = professorTextView.text.toString()
            val email = emailTextView.text.toString()
            val address = addressTextView.text.toString()
            val timeStart = timeStart.text.toString()
            val timeEnd = timeEnd.text.toString()

            if (className.isEmpty()) {
                classNameTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                classNameTextView.error = null
            }

            if (building.isEmpty()) {
                buildingTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                buildingTextView.error = null
            }

            if (professor.isEmpty()) {
                professorTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                professorTextView.error = null
            }

            if (email.isEmpty()) {
                emailTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                emailTextView.error = null
            }

            if (address.isEmpty()) {
                addressTextView.error = "This field is required"
                return@setOnClickListener
            } else {
                addressTextView.error = null
            }

            if (timeStart == "Pick a time") {
                Toast.makeText(context, "Please choose a starting time", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (timeEnd == "Pick a time") {
                Toast.makeText(context, "Please choose an ending time", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val fragmentManager = fragmentManager
            val newFragment = SubjectsFragment()

            bundle.putString("newClassName", className)
            bundle.putString("newBuilding", building)
            bundle.putString("newProfessor", professor)
            bundle.putString("newEmail", email)
            bundle.putString("newAddress", address)
            bundle.putString("newTimeStart", timeStart)
            bundle.putString("newTimeEnd", timeEnd)
            bundle.putInt("newSubjectColor", currentSubjectColor)
            bundle.putInt("newSubjectTextColor", subjectTextColor)
            bundle.putBoolean("isEdit", true)

            newFragment.arguments = bundle

            //clears the back stack
            for (i in 0 until fragmentManager!!.backStackEntryCount) {
                fragmentManager.popBackStack()
            }

            activity!!.toolbar.visibility = View.VISIBLE
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            transaction.replace(R.id.fragment_container, newFragment).commit()
        }
    }
}