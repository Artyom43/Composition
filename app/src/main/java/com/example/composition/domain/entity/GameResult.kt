package com.example.composition.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings
)