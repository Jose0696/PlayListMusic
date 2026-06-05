package com.example.ftmusicapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ftmusicapp.data.model.CreatePlayListRequest
import com.example.ftmusicapp.data.model.PlayList
import com.example.ftmusicapp.data.model.UpdatePlayListRequest
import com.example.ftmusicapp.databinding.ActivityMainBinding
import com.example.ftmusicapp.ui.adapter.PlayListAdapter
import com.example.ftmusicapp.utils.UiState
import com.example.ftmusicapp.viewmodel.PlayListViewModel
import android.content.Intent
import com.example.ftmusicapp.ui.playlist.PlayListDetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val playListViewModel: PlayListViewModel by viewModels()

    private lateinit var playListAdapter: PlayListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeViewModel()

        playListViewModel.getPlayLists()
    }

    private fun setupRecyclerView() {
        playListAdapter = PlayListAdapter(
            onItemClick = { playList ->
                val intent = Intent(this, PlayListDetailActivity::class.java).apply {
                    putExtra("ID_PLAYLIST", playList.idPlayList)
                    putExtra("NAME_PLAYLIST", playList.namePlayList)
                    putExtra("INFORMATION", playList.information ?: "")
                }

                startActivity(intent)
            },
            onEditClick = { playList ->
                showPlayListDialog(playList)
            },
            onDeleteClick = { playList ->
                showDeleteConfirmation(playList)
            }
        )

        binding.rvPlayLists.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = playListAdapter
        }
    }

    private fun setupListeners() {
        binding.fabAddPlayList.setOnClickListener {
            showPlayListDialog(null)
        }
    }

    private fun observeViewModel() {
        playListViewModel.playListsState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val playLists = state.data
                    playListAdapter.submitList(playLists)

                    binding.tvEmpty.visibility =
                        if (playLists.isEmpty()) View.VISIBLE else View.GONE
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        playListViewModel.createPlayListState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Playlist created successfully", Toast.LENGTH_SHORT).show()
                    playListViewModel.resetCreatePlayListState()
                    playListViewModel.getPlayLists()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    playListViewModel.resetCreatePlayListState()
                }
            }
        }

        playListViewModel.updatePlayListState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Playlist updated successfully", Toast.LENGTH_SHORT).show()
                    playListViewModel.resetUpdatePlayListState()
                    playListViewModel.getPlayLists()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    playListViewModel.resetUpdatePlayListState()
                }
            }
        }

        playListViewModel.deletePlayListState.observe(this) { state ->
            when (state) {
                is UiState.Idle -> Unit

                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Playlist deleted successfully", Toast.LENGTH_SHORT).show()
                    playListViewModel.resetDeletePlayListState()
                    playListViewModel.getPlayLists()
                }

                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    playListViewModel.resetDeletePlayListState()
                }
            }
        }
    }

    private fun showPlayListDialog(playList: PlayList?) {
        val isEditing = playList != null

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 0)
        }

        val inputName = EditText(this).apply {
            hint = "Playlist name"
            setText(playList?.namePlayList ?: "")
        }

        val inputInformation = EditText(this).apply {
            hint = "Description"
            setText(playList?.information ?: "")
        }

        container.addView(inputName)
        container.addView(inputInformation)

        AlertDialog.Builder(this)
            .setTitle(if (isEditing) "Edit playlist" else "Create playlist")
            .setView(container)
            .setPositiveButton(if (isEditing) "Update" else "Create") { _, _ ->
                val name = inputName.text.toString().trim()
                val information = inputInformation.text.toString().trim()

                if (name.isEmpty()) {
                    Toast.makeText(this, "Playlist name is required", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (isEditing && playList != null) {
                    playListViewModel.updatePlayList(
                        playList.idPlayList,
                        UpdatePlayListRequest(
                            namePlayList = name,
                            information = information
                        )
                    )
                } else {
                    playListViewModel.createPlayList(
                        CreatePlayListRequest(
                            namePlayList = name,
                            information = information
                        )
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmation(playList: PlayList) {
        AlertDialog.Builder(this)
            .setTitle("Delete playlist")
            .setMessage("Are you sure you want to delete '${playList.namePlayList}'?")
            .setPositiveButton("Delete") { _, _ ->
                playListViewModel.deletePlayList(playList.idPlayList)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}