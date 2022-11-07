package com.app.logoquiz.ui.game

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.app.logoquiz.R
import com.app.logoquiz.data.db.Question
import com.app.logoquiz.databinding.FragmentGameBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update this value and send the query again to view model
        val questionIndex = 1

        viewModel.getQuestionData(questionIndex)

        viewModel.question.observe(viewLifecycleOwner) { question ->
            question?.let { createQuestionView(it) }
        }

        viewModel.correctGuess.observe(viewLifecycleOwner) { result ->
            result?.let {
                val view = binding.glInput.findViewWithTag<TextView>(it.first)
                view.text = it.second.toString()
            }
        }
    }

    private fun createQuestionView(it: Question) {
        Glide.with(binding.ivLogo.context).load(it.photoUrl)
            .into(binding.ivLogo)

        binding.glInput.rowCount = 1
        binding.glInput.columnCount = it.answer.length

        it.answer.forEachIndexed { index, _ ->
            val view = TextView(requireContext())
            view.layoutParams = ConstraintLayout.LayoutParams(100, 100)
            view.setBackgroundResource(R.drawable.grey_box_black_border)
            view.tag = index
            view.gravity = Gravity.CENTER
            view.setTextColor(Color.GREEN)
            binding.glInput.addView(view)
        }

        binding.glOptions.rowCount = 2
        binding.glOptions.columnCount = 9

        it.options.forEachIndexed { index, character ->
            val view = TextView(requireContext())
            view.layoutParams = ConstraintLayout.LayoutParams(100, 100)
            view.gravity = Gravity.CENTER
            view.setTextColor(Color.BLUE)
            view.tag = index
            view.setBackgroundResource(R.drawable.grey_box_black_border)
            view.text = character.toString()
            binding.glOptions.addView(view)


            view.setOnClickListener {
                val tagValue = it.tag as Int
                viewModel.checkContains(tagValue)
            }
        }
    }
}