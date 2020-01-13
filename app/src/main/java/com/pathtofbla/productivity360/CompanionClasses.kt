package com.pathtofbla.productivity360

import java.text.SimpleDateFormat
import java.util.*

open class Task(
    title: String,
    description: String,
    dueDate: Long,
    priority: String
)

class Homework(
    title: String,
    description: String,
    dueDate: Long,
    priority: String,
    subject: Subject
) :
    Task(
        title,
        description,
        dueDate,
        priority
    )

class Subject(
    val title: String,
    val classroomLocation: String,
    val startTime: String,
    val endTime: String,
    val teacher: Teacher,
    val backgroundColor: Int,
    val textColor: Int
)

class Teacher(
    val name: String,
    val email: String
)

class Streak(
    title: String,
    description: String,
    startDate: Long,
    milestoneType: String,
    visibilityType: String
)

class Rewards(
    title: String,
    description: String,
    points: Int
)

class Absence(
    val title: String,
    val date: Long,
    val cancelled: Boolean
)

class FormatDate {
    companion object {
        fun convertToString(pattern: String, datetime: Long): String {
            return SimpleDateFormat(pattern, Locale.US).format(datetime)
        }

        fun convertToString(pattern: String, datetime: Date): String{
            return SimpleDateFormat(pattern, Locale.US).format(datetime)
        }

//        fun converToLong(): Long{
//
//        }
    }
}