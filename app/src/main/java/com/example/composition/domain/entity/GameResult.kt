package com.example.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable {

    val percentOfRightAnswers: Int
        get() = ((countOfRightAnswers / countQuestions.toDouble()) * 100).toInt()
}