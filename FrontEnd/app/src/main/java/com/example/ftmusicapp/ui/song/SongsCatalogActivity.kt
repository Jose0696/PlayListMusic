package com.example.ftmusicapp.ui.song

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ftmusicapp.data.model.AssignSongRequest
import com.example.ftmusicapp.databinding.ActivitySongsCatalogBinding
import com.example.ftmusicapp.ui.adapter.SongAdapter
import com.example.ftmusicapp.utils.UiState
import com.example.ftmusicapp.viewmodel.PlayListViewModel
import com.example.ftmusicapp.viewmodel.SongViewModel
import android.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import com.example.ftmusicapp.data.model.CreateSongRequest

class SongsCatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongsCatalogBinding

    private val songViewModel: SongViewModel by viewModels()
    private val playListViewModel: PlayListViewModel by viewModels()

    private lateinit var songAdapter: SongAdapter

    private var idPlayList: Int = 0
    private var namePlayList: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongsCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentData()
        setupScreen()
        setupRecyclerView()
        setupListeners()
        observeViewModels()

        songViewModel.getSongsCatalog()
    }

    private fun getIntentData() {
        idPlayList = intent.getIntExtra("ID_PLAYLIST", 0)
        namePlayList = intent.getStringExtra("NAME_PLAYLIST") ?: ""
    }

    private fun setupScreen() {
        binding.tvSubtitle.text = if (namePlayList.isBlank()) {
            "Add songs to your playlist"
        } else {
            "Add songs to $namePlayList"
        }
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(
            onAddClick = { song ->
                if (idPlayList <= 0) {
                    Toast.makeText(this, "Invalid playlist", Toast.LENGTH_SHORT).show()
                    return@SongAdapter
                }

                playListViewModel.addSongToPlayList(
                    idPlayList,
                    AssignSongRequest(song.idSong)
                )
            }
        )

        binding.rvSongsCatalog.apply {
            layoutManager = LinearLayoutManager(this@SongsCatalogActivity)
            adapter = songAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.fabCreateSong.setOnClickListener {
            showCreateSongDialog()
        }
    }

    private fun showCreateSongDialog() {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 0)
        }

        val inputTitle = EditText(this).apply {
            hint = "Song title"
        }

        val inputArtist = EditText(this).apply {
            hint = "Artist"
        }

        val inputAlbum = EditText(this).apply {
            hint = "Album"
        }

        val inputGenre = EditText(this).apply {
            hint = "Genre"
        }

        val timeContainer = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        val inputMinutes = EditText(this).apply {
            hint = "Min"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        val separator = android.widget.TextView(this).apply {
            text = "  :  "
            textSize = 18f
        }

        val inputSeconds = EditText(this).apply {
            hint = "Sec"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        timeContainer.addView(inputMinutes)
        timeContainer.addView(separator)
        timeContainer.addView(inputSeconds)

        container.addView(inputTitle)
        container.addView(inputArtist)
        container.addView(inputAlbum)
        container.addView(inputGenre)
        container.addView(timeContainer)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Create song")
            .setView(container)
            .setPositiveButton("Create", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {
            val createButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

            createButton.setOnClickListener {
                val title = inputTitle.text.toString().trim()
                val artist = inputArtist.text.toString().trim()
                val album = inputAlbum.text.toString().trim()
                val genre = inputGenre.text.toString().trim()
                val minutesText = inputMinutes.text.toString().trim()
                val secondsText = inputSeconds.text.toString().trim()

                if (title.isEmpty()) {
                    inputTitle.error = "Song title is required"
                    inputTitle.requestFocus()
                    return@setOnClickListener
                }

                if (title.length < 2) {
                    inputTitle.error = "Song title must have at least 2 characters"
                    inputTitle.requestFocus()
                    return@setOnClickListener
                }

                if (artist.isEmpty()) {
                    inputArtist.error = "Artist is required"
                    inputArtist.requestFocus()
                    return@setOnClickListener
                }

                if (artist.length < 2) {
                    inputArtist.error = "Artist must have at least 2 characters"
                    inputArtist.requestFocus()
                    return@setOnClickListener
                }

                if (album.isEmpty()) {
                    inputAlbum.error = "Album is required"
                    inputAlbum.requestFocus()
                    return@setOnClickListener
                }

                if (genre.isEmpty()) {
                    inputGenre.error = "Genre is required"
                    inputGenre.requestFocus()
                    return@setOnClickListener
                }

                if (minutesText.isEmpty()) {
                    inputMinutes.error = "Minutes are required"
                    inputMinutes.requestFocus()
                    return@setOnClickListener
                }

                if (secondsText.isEmpty()) {
                    inputSeconds.error = "Seconds are required"
                    inputSeconds.requestFocus()
                    return@setOnClickListener
                }

                val minutes = minutesText.toIntOrNull()
                val seconds = secondsText.toIntOrNull()

                if (minutes == null || minutes < 0) {
                    inputMinutes.error = "Invalid minutes"
                    inputMinutes.requestFocus()
                    return@setOnClickListener
                }

                if (seconds == null || seconds !in 0..59) {
                    inputSeconds.error = "Seconds must be between 0 and 59"
                    inputSeconds.requestFocus()
                    return@setOnClickListener
                }

                if (minutes == 0 && seconds == 0) {
                    inputSeconds.error = "Duration must be greater than 00:00"
                    inputSeconds.requestFocus()
                    return@setOnClickListener
                }

                val lapseTime = String.format("%02d:%02d", minutes, seconds)

                songViewModel.createSong(
                    CreateSongRequest(
                        title = title,
                        artist = artist,
                        album = album,
                        genre = genre,
                        lapseTime = lapseTime
                    )
                )

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun observeViewModels() {
        songViewModel.createSongState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Song created successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    songViewModel.resetCreateSongState()
                    songViewModel.getSongsCatalog()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()

                    songViewModel.resetCreateSongState()
                }
            }
        }

        songViewModel.songsCatalogState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvEmptySongs.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val songs = state.data
                    songAdapter.submitList(songs)

                    binding.tvEmptySongs.visibility =
                        if (songs.isEmpty()) View.VISIBLE else View.GONE
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        playListViewModel.addSongToPlayListState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Song added to playlist",
                        Toast.LENGTH_SHORT
                    ).show()

                    playListViewModel.resetAddSongToPlayListState()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()

                    playListViewModel.resetAddSongToPlayListState()
                }
            }
        }
    }
}