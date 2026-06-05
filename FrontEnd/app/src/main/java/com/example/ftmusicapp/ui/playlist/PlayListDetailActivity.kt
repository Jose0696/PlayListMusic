package com.example.ftmusicapp.ui.playlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ftmusicapp.databinding.ActivityPlayListDetailBinding
import com.example.ftmusicapp.ui.adapter.SongByPlayListAdapter
import com.example.ftmusicapp.utils.UiState
import com.example.ftmusicapp.viewmodel.PlayListViewModel
import android.content.Intent
import com.example.ftmusicapp.ui.song.SongsCatalogActivity

class PlayListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayListDetailBinding
    private val playListViewModel: PlayListViewModel by viewModels()

    private lateinit var songByPlayListAdapter: SongByPlayListAdapter

    private var idPlayList: Int = 0
    private var namePlayList: String = ""
    private var information: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        setupScreen()
        setupRecyclerView()
        setupListeners()
        observeViewModel()

        playListViewModel.getSongsByPlayList(idPlayList)
    }

    override fun onResume() {
        super.onResume()

        if (idPlayList > 0) {
            playListViewModel.getSongsByPlayList(idPlayList)
        }
    }

    private fun getIntentData() {
        idPlayList = intent.getIntExtra("ID_PLAYLIST", 0)
        namePlayList = intent.getStringExtra("NAME_PLAYLIST") ?: ""
        information = intent.getStringExtra("INFORMATION") ?: ""
    }

    private fun setupScreen() {
        binding.tvPlayListTitle.text = namePlayList
        binding.tvPlayListInformation.text =
            if (information.isBlank()) "No description" else information
    }

    private fun setupRecyclerView() {
        songByPlayListAdapter = SongByPlayListAdapter(
            onRemoveClick = { song ->
                showRemoveSongConfirmation(song.idSong, song.title)
            }
        )

        binding.rvSongsByPlayList.apply {
            layoutManager = LinearLayoutManager(this@PlayListDetailActivity)
            adapter = songByPlayListAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnAddSongs.setOnClickListener {
            val intent = Intent(this, SongsCatalogActivity::class.java).apply {
                putExtra("ID_PLAYLIST", idPlayList)
                putExtra("NAME_PLAYLIST", namePlayList)
            }

            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        playListViewModel.songsByPlayListState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvEmptySongs.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val songs = state.data
                    songByPlayListAdapter.submitList(songs)

                    binding.tvEmptySongs.visibility =
                        if (songs.isEmpty()) View.VISIBLE else View.GONE
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        playListViewModel.removeSongFromPlayListState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Song removed successfully", Toast.LENGTH_SHORT).show()
                    playListViewModel.resetRemoveSongFromPlayListState()
                    playListViewModel.getSongsByPlayList(idPlayList)
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    playListViewModel.resetRemoveSongFromPlayListState()
                }
            }
        }
    }

    private fun showRemoveSongConfirmation(idSong: Int, songTitle: String) {
        AlertDialog.Builder(this)
            .setTitle("Remove song")
            .setMessage("Do you want to remove '$songTitle' from this playlist?")
            .setPositiveButton("Remove") { _, _ ->
                playListViewModel.removeSongFromPlayList(idPlayList, idSong)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}