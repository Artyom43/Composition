package com.example.composition.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOptionsClickListeners()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOptionsClickListeners() {
        for (i in 0 until tvOptions.size) {
            tvOptions[i].setOnClickListener {
                viewModel.chooseAnswer(viewModel.question.value?.options!![i])
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            formattedTime.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }

            question.observe(viewLifecycleOwner) { question ->
                with(binding) {
                    for (i in 0 until tvOptions.size) {
                        tvOptions[i].text = question.options[i].toString()
                    }

                    tvLeftNumber.text = question.visibleNumber.toString()
                    tvSum.text = question.sum.toString()
                }
            }

            progressAnswers.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.text = it
            }

            percentOfRightAnswers.observe(viewLifecycleOwner) {
                binding.progressBar.setProgress(it, true)
            }

            enoughPercent.observe(viewLifecycleOwner) {
                binding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
            }

            enoughCount.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.setTextColor(getColorByState(it))
            }

            gameResult.observe(viewLifecycleOwner) {
                launchGameFishedFragment(it)
            }

            minPercent.observe(viewLifecycleOwner) {
                binding.progressBar.secondaryProgress = it
            }
        }
    }

    private fun getColorByState(state: Boolean): Int {
        return if (state) {
            Color.GREEN
        } else {
            Color.RED
        }
    }

    private fun parseArgs() {
        level = requireArguments().getParcelable<Level>(KEY_LEVEL) as Level
    }

    private fun launchGameFishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "GameFragment"

        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}