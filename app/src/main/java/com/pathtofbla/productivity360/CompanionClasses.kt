package com.pathtofbla.productivity360

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
    title: String,
//    physicalLocation: Geo,
    classroomLocation: String,
    startTime: Long,
    duration: Long,
    teacher: Teacher
)

class Teacher(
    name: String,
    email: String
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

