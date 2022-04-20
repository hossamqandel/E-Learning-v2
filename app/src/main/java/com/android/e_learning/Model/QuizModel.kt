package com.android.e_learning.Model

data class QuizModel(
    val question: String? = null,
    val firstAnswer: String? = null,
    val secondeAnswer: String? = null,
    val thirdAnswer: String? = null,
    val lastAnswer: String? = null,
    val rightAnswer: Int? = null,
)