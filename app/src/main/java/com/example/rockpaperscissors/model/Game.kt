package com.example.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rockpaperscissors.R
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "computer_pick")
    var computerPick: Int,

    @ColumnInfo(name = "player_pick")
    var playerPick: Int,

    @ColumnInfo(name = "winner")
    var result: String,

    @ColumnInfo(name = "date")
    var date: String

) : Parcelable {
    companion object {
        val GAME_RES_DRAWABLE_IDS = arrayOf(
            R.drawable.rock,
            R.drawable.paper,
            R.drawable.scissors)
    }
}