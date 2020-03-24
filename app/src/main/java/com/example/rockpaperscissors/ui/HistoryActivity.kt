package com.example.rockpaperscissors.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.model.Game
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private val historyList = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(historyList)

    private val mainScope = CoroutineScope((Dispatchers.Main))
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        rvHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gameAdapter
        rvHistory.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )
        getGamesFromDB()
    }

    /**
     * Gets all games from the database and reverses them so the last played game is shown.
     */
    private fun getGamesFromDB() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }

            this@HistoryActivity.historyList.clear()
            games?.let { this@HistoryActivity.historyList.addAll(games) }
            this@HistoryActivity.historyList.reverse()
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_game_history -> {
                deleteHistory()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * Deletes all games from history.
     */
    private fun deleteHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesFromDB()
        }
    }

}