package com.example.composition.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R

// fragment_game_finished.xml

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score), count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers), count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage), count
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage), count
    )
}

@BindingAdapter("resultEmoji")
fun bindEmoji(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(
        if (winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    )
}

// fragment_game.xml

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("answerProgress")
fun bindAnswerProgress(textView: TextView, progress: String) {
    textView.text = progress
}

@BindingAdapter("percentOfRightAnswers")
fun bindPercentOfRightAnswers(progressBar: ProgressBar, percent: Int) {
    progressBar.setProgress(percent, true)
}

@BindingAdapter("enoughPercentChecker")
fun bindPercentChecker(progressBar: ProgressBar, isEnough: Boolean) {
    progressBar.progressTintList = ColorStateList.valueOf(getColorByState(isEnough))
}

@BindingAdapter("enoughScoreChecker")
fun bindScoreChecker(textView: TextView, isEnough: Boolean) {
    textView.setTextColor(getColorByState(isEnough))
}

@BindingAdapter("setSecondaryProgress")
fun bindSecondaryProgress(progressBar: ProgressBar, minPercentage: Int) {
    progressBar.secondaryProgress = minPercentage
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}


private fun getColorByState(state: Boolean): Int {
    return if (state) {
        Color.GREEN
    } else {
        Color.RED
    }
}