package com.example.rockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.model.Strings
import com.example.rockpaperscissors.model.Game
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.history_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MainActivity : AppCompatActivity() {
    private val mainScope = CoroutineScope((Dispatchers.Main))
    private lateinit var gameRepository: GameRepository
    private val game = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(game)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        ibRock.setOnClickListener { playGame(0) }
        ibPaper.setOnClickListener { playGame(1) }
        ibScissors.setOnClickListener { playGame(2) }
    }

    /**
     * Play the game and save the results to the history list.
     */
    private fun playGame(player: Int) {
        val computer = (0..2).random()
        val winner = determinateWinner(player, computer)

        val playedGame = Game(
            playerPick = player,
            computerPick = computer,
            result = winner,
            date = Calendar.getInstance().time.toString()
        )

        updateState(playedGame)
        saveGame(playedGame)
    }

    private fun updateState(game: Game) {
        tvResult.text = game.result
        tvTimestamp.text = game.date
        tvTimestamp.text = ""
        ivComputer.setImageResource(Game.GAME_RES_DRAWABLE_IDS[game.computerPick])
        ivPlayer.setImageResource(Game.GAME_RES_DRAWABLE_IDS[game.playerPick])
    }

    /**
     * Saves a played game in the game history.
     */
    private fun saveGame(playedGame: Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(playedGame)
            }
        }
    }

    /**
     * Determines who won the game.
     */
    private fun determinateWinner(playerPick: Int, computerPick: Int): String {
        // User played a tie
        if (playerPick == computerPick) {
            return Strings.DRAW.state
        }
        // User wins as rock beats scissors, paper beats rock, scissors beat paper
        else if (
            playerPick == 0 && computerPick == 2 ||
            playerPick == 1 && computerPick == 0 ||
            playerPick == 2 && computerPick == 2
        ) return Strings.WON.state

        return Strings.LOST.state
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_history -> {
                startHistoryActivity()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun startHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}
