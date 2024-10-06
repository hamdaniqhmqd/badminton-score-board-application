package com.tugas.kabaddikounter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AlertDialog
import com.tugas.kabaddikounter.databinding.ActivityInputTeamBinding

class InputTeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tombolSubmit.setOnClickListener {
            var inputTeamA = binding.inputTeamA.text.toString()
            var inputTeamB = binding.inputTeamB.text.toString()

            val submit = Intent(this, MainActivity::class.java)
            submit.putExtra("TeamA", inputTeamA)
            submit.putExtra("TeamB", inputTeamB)
            startActivity(submit)
        }
    }
}