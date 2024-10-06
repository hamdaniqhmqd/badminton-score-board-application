package com.tugas.kabaddikounter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class ScoreViewModel : ViewModel() {
    private val _scoreA = MutableLiveData<Int>(0)
    private val _scoreA_R1 = MutableLiveData<Int>(0)
    private val _scoreA_R2 = MutableLiveData<Int>(0)
    private val _scoreA_R3 = MutableLiveData<Int>(0)

    private val _scoreB = MutableLiveData<Int>(0)
    private val _scoreB_R1 = MutableLiveData<Int>(0)
    private val _scoreB_R2 = MutableLiveData<Int>(0)
    private val _scoreB_R3 = MutableLiveData<Int>(0)

    var namaTeamA: String = ""
    var namaTeamB: String = ""

    val scoreA: LiveData<Int> get() = _scoreA
    val scoreB: LiveData<Int> get() = _scoreB

    val scoreA_Round1: LiveData<Int> get() = _scoreA_R1
    val scoreA_Round2: LiveData<Int> get() = _scoreA_R2
    val scoreA_Round3: LiveData<Int> get() = _scoreA_R3

    val scoreB_Round1: LiveData<Int> get() = _scoreB_R1
    val scoreB_Round2: LiveData<Int> get() = _scoreB_R2
    val scoreB_Round3: LiveData<Int> get() = _scoreB_R3

    val hasilA: LiveData<String> = _scoreA.map { scoreA ->
        if (scoreA > _scoreB.value!! && scoreA >= 21 && (scoreA - _scoreB.value!!) >= 2) {
            "team A menang"
        } else ""
    }

    val hasilB: LiveData<String> = _scoreB.map { scoreB ->
        if (scoreB > _scoreA.value!! && scoreB >= 21 && (scoreB - _scoreA.value!!) >= 2) {
            "team B menang"
        } else ""
    }

    val hasilRoundA: LiveData<String> = _scoreA.map { SA1 ->
        if (_scoreA_R1.value!! >= 21 && _scoreA_R1.value!! > _scoreB_R1.value!!) {
            if (_scoreA_R2.value!! >= 21 && _scoreA_R2.value!! > _scoreB_R2.value!!) {
                "Team $namaTeamA menang"
            } else if (_scoreB_R2.value!! >= 21 && _scoreB_R2.value!! > _scoreA_R2.value!!) {
                if (SA1 >= 21 && SA1 > _scoreB_R1.value!!) {
                    "Team $namaTeamA menang"
                } else if (_scoreB.value!! >= 21 && _scoreB.value!! > SA1) {
                    "Team $namaTeamB menang"
                } else ""
            } else ""
        } else {
            if (_scoreA_R2.value!! >= 21 && _scoreA_R2.value!! > _scoreB_R2.value!!) {
                if (SA1 >= 21 && SA1 > _scoreB.value!!) {
                    "Team $namaTeamA menang"
                } else if (_scoreB.value!! >= 21 && _scoreB.value!! > SA1) {
                    "Team $namaTeamB menang"
                } else ""
            } else ""
        }
    }

    val hasilRoundB: LiveData<String> = _scoreB.map { SB1 ->
        if (_scoreB_R1.value!! >= 21 && _scoreB_R1.value!! > _scoreA_R1.value!!) {
            if (_scoreB_R2.value!! >= 21 && _scoreB_R2.value!! > _scoreA_R2.value!!) {
                "Team $namaTeamB menang"
            } else if (_scoreA_R2.value!! >= 21 && _scoreA_R2.value!! > _scoreB_R2.value!!) {
                if (SB1 >= 21 && SB1 > _scoreA.value!!) {
                    "Team $namaTeamB menang"
                } else if (_scoreA.value!! >= 21 && _scoreA.value!! > SB1) {
                    "Team $namaTeamA menang"
                } else ""
            } else ""
        } else {
            if (_scoreB_R2.value!! >= 21 && _scoreB_R2.value!! > _scoreA_R2.value!!) {
                if (SB1 >= 21 && SB1 > _scoreA.value!!) {
                    "Team $namaTeamB menang"
                } else if (_scoreA.value!! >= 21 && _scoreA.value!! > SB1) {
                    "Team $namaTeamA menang"
                } else ""
            } else ""
        }
    }

    fun round1() {
        _scoreA_R1.value = scoreA.value!!
        _scoreB_R1.value = scoreB.value!!

        _scoreA.value = 0
        _scoreB.value = 0
    }

    fun round2() {
        _scoreA_R2.value = scoreA.value!!
        _scoreB_R2.value = scoreB.value!!

        _scoreA.value = 0
        _scoreB.value = 0
    }

    fun round3() {
        _scoreA_R3.value = scoreA.value!!
        _scoreB_R3.value = scoreB.value!!
    }

    fun incermentScore(isTeamA: Boolean) {
        if (isTeamA) {
            _scoreA.value = _scoreA.value!! + 1
        } else {
            _scoreB.value = _scoreB.value!! + 1
        }
    }

    fun resetScore() {
        _scoreA.value = 0
        _scoreB.value = 0

        _scoreA_R1.value = 0
        _scoreA_R2.value = 0
        _scoreA_R3.value = 0
        _scoreB_R1.value = 0
        _scoreB_R2.value = 0
        _scoreB_R3.value = 0
    }
}