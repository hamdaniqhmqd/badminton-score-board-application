package com.tugas.kabaddikounter

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.tugas.kabaddikounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val teamA = intent.getStringExtra("TeamA")
        val teamB = intent.getStringExtra("TeamB")

        binding.labelSkorA.text = teamA.toString()
        binding.labelSkorB.text = teamB.toString()

        viewModel.namaTeamA = teamA.toString()
        viewModel.namaTeamB = teamB.toString()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val scoreA_Round1 = Observer<Int> { newValue ->
            binding.SkorARound1.text = newValue.toString()
        }

        val scoreA_Round2 = Observer<Int> { newValue ->
            binding.SkorARound2.text = newValue.toString()
        }

        val scoreA_Round3 = Observer<Int> { newValue ->
            binding.SkorARound3.text = newValue.toString()
        }

        val scoreB_Round1 = Observer<Int> { newValue ->
            binding.SkorBRound1.text = newValue.toString()
        }

        val scoreB_Round2 = Observer<Int> { newValue ->
            binding.SkorBRound2.text = newValue.toString()
        }

        val scoreB_Round3 = Observer<Int> { newValue ->
            binding.SkorBRound3.text = newValue.toString()
        }

        val scoreA_Observer = Observer<Int> { newValue ->
            binding.SkorA.text = newValue.toString()
        }

        val scoreB_Observer = Observer<Int> { newValue ->
            binding.SkorB.text = newValue.toString()
        }

        viewModel.scoreA.observe(this, scoreA_Observer)
        viewModel.scoreB.observe(this, scoreB_Observer)

        viewModel.scoreA_Round1.observe(this, scoreA_Round1)
        viewModel.scoreA_Round2.observe(this, scoreA_Round2)
        viewModel.scoreA_Round3.observe(this, scoreA_Round3)

        viewModel.scoreB_Round1.observe(this, scoreB_Round1)
        viewModel.scoreB_Round2.observe(this, scoreB_Round2)
        viewModel.scoreB_Round3.observe(this, scoreB_Round3)

        binding.TombolSatuSkorA.setOnClickListener{
            viewModel.incermentScore(true)
        }

        binding.TombolSatuSkorB.setOnClickListener{
            viewModel.incermentScore(false)
        }

        binding.TombolReset.setOnClickListener{
            viewModel.resetScore()

            binding.SkorARound1.setTextColor(ContextCompat.getColor(this, R.color.putih))
            binding.SkorARound2.setTextColor(ContextCompat.getColor(this, R.color.putih))
            binding.SkorARound3.setTextColor(ContextCompat.getColor(this, R.color.putih))

            binding.SkorBRound1.setTextColor(ContextCompat.getColor(this, R.color.putih))
            binding.SkorBRound2.setTextColor(ContextCompat.getColor(this, R.color.putih))
            binding.SkorBRound3.setTextColor(ContextCompat.getColor(this, R.color.putih))

            binding.TombolSatuSkorA.isEnabled = true
            binding.TombolSatuSkorA.setBackgroundColor(ContextCompat.getColor(this, R.color.putih))

            binding.TombolSatuSkorB.isEnabled = true
            binding.TombolSatuSkorB.setBackgroundColor(ContextCompat.getColor(this, R.color.putih))
        }

        val hasilScore = Observer<String> { hasil ->
            binding.labelMenang.text = hasil
        }

        viewModel.hasilRoundA.observe(this, hasilScore)
        viewModel.hasilRoundB.observe(this, hasilScore)

        viewModel.hasilA.observe(this) { resultA ->
            when {
                resultA.isNotEmpty() -> {
                    when {
                        viewModel.scoreA_Round1.value == 0 && viewModel.scoreB_Round1.value == 0 -> {
                            viewModel.round1()

                            if (viewModel.scoreA_Round1.value!! > viewModel.scoreB_Round1.value!!) {
                                binding.SkorARound1.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            } else {
                                binding.SkorBRound1.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            }
                        }

                        viewModel.scoreA_Round2.value == 0 && viewModel.scoreB_Round2.value == 0 -> {
                            viewModel.round2()

                            if (
                                (viewModel.scoreA_Round1.value!! > viewModel.scoreB_Round1.value!! &&
                                viewModel.scoreA_Round2.value!! > viewModel.scoreB_Round2.value!!) ||
                                (viewModel.scoreA_Round1.value!! < viewModel.scoreB_Round1.value!! &&
                                viewModel.scoreA_Round2.value!! < viewModel.scoreB_Round2.value!!)
                            ) {
                                binding.cardPopUp.visibility = View.VISIBLE

                                binding.TombolSatuSkorA.isEnabled = false
                                binding.TombolSatuSkorA.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))

                                binding.TombolSatuSkorB.isEnabled = false
                                binding.TombolSatuSkorB.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))
                            }

                            if (viewModel.scoreA_Round2.value!! > viewModel.scoreB_Round2.value!!) {
                                binding.SkorARound2.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            } else {
                                binding.SkorBRound2.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            }
                        }

                        viewModel.scoreA_Round3.value == 0 && viewModel.scoreB_Round3.value == 0 -> {
                            viewModel.round3()

                            binding.cardPopUp.visibility = View.VISIBLE

                            if (viewModel.scoreA_Round3.value!! > viewModel.scoreB_Round3.value!!) {
                                binding.SkorARound3.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            } else {
                                binding.SkorBRound3.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            }

                            binding.TombolSatuSkorA.isEnabled = false
                            binding.TombolSatuSkorA.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))

                            binding.TombolSatuSkorB.isEnabled = false
                            binding.TombolSatuSkorB.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))
                        }
                    }
                }
            }
        }

        viewModel.hasilB.observe(this) { resultB ->
            when {
                resultB.isNotEmpty() -> {
                    when {
                        viewModel.scoreA_Round1.value == 0 && viewModel.scoreB_Round1.value == 0 -> {
                            viewModel.round1()

                            if (viewModel.scoreA_Round1.value!! > viewModel.scoreB_Round1.value!!) {
                                binding.SkorARound1.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            } else {
                                binding.SkorBRound1.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            }
                        }

                        viewModel.scoreA_Round2.value == 0 && viewModel.scoreB_Round2.value == 0 -> {
                            viewModel.round2()

                            if (
                                (viewModel.scoreA_Round1.value!! > viewModel.scoreB_Round1.value!! &&
                                viewModel.scoreA_Round2.value!! > viewModel.scoreB_Round2.value!!) ||
                                (viewModel.scoreA_Round1.value!! < viewModel.scoreB_Round1.value!! &&
                                viewModel.scoreA_Round2.value!! < viewModel.scoreB_Round2.value!!)
                            ) {
                                binding.cardPopUp.visibility = View.VISIBLE

                                binding.TombolSatuSkorA.isEnabled = false
                                binding.TombolSatuSkorA.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))

                                binding.TombolSatuSkorB.isEnabled = false
                                binding.TombolSatuSkorB.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))
                            }

                            if (viewModel.scoreA_Round2.value!! > viewModel.scoreB_Round2.value!!) {
                                binding.SkorARound2.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            } else {
                                binding.SkorBRound2.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            }
                        }

                        viewModel.scoreA_Round3.value == 0 && viewModel.scoreB_Round3.value == 0 -> {
                            viewModel.round3()

                            if (viewModel.scoreA_Round3.value!! > viewModel.scoreB_Round3.value!!) {
                                binding.SkorARound3.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            } else {
                                binding.SkorBRound3.setTextColor(ContextCompat.getColor(this, R.color.biru))
                            }

                            binding.cardPopUp.visibility = View.VISIBLE

                            binding.TombolSatuSkorA.isEnabled = false
                            binding.TombolSatuSkorA.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))

                            binding.TombolSatuSkorB.isEnabled = false
                            binding.TombolSatuSkorB.setBackgroundColor(ContextCompat.getColor(this, R.color.hitam_smooth))
                        }
                    }
                }
            }
        }

        binding.tombolTutup.setOnClickListener {
            binding.cardPopUp.visibility = View.GONE
        }
    }
}