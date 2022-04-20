package com.android.e_learning.Model

data class CourseModel(
    var instructorId: String? = null,
    var courseId: String? = null,
    var courseName: String? = null,
    var assignmentGrade: Double? = null,
    var attendanceGrade: Double? = null,
    var projectsGrade: Double? = null)