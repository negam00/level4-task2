package com.example.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "game_table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "computer_pick")
    var computerPick: String,

    @ColumnInfo(name = "player_pick")
    var playerPick: Int,

    @ColumnInfo(name = "winner")
    var winner: String,

    @ColumnInfo(name = "date")
    var date: String

): Parcelable