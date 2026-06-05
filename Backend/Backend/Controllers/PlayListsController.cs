using Backend.Common;
using Backend.DTOs.PlayList;
using Backend.DTOs.Song;
using Backend.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using MySqlConnector;

namespace Backend.Controllers
{
    [ApiController]
    [Route("api/playlists")]
    public class PlayListsController : ControllerBase
    {
        private readonly IPlayListService _playListService;

        public PlayListsController(IPlayListService playListService)
        {
            _playListService = playListService;
        }

        [HttpGet]
        public async Task<IActionResult> GetPlayLists()
        {
            try
            {
                var playLists = await _playListService.GetPlayListsAsync();

                return Ok(ApiResponse<IEnumerable<PlayListResponse>>.Success(
                    playLists,
                    "Playlists retrieved successfully."
                ));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpPost]
        public async Task<IActionResult> CreatePlayList([FromBody] CreatePlayListRequest request)
        {
            try
            {
                var playList = await _playListService.CreatePlayListAsync(request);

                if (playList == null)
                {
                    return BadRequest(ApiResponse<string>.Error("Could not create playlist."));
                }

                return Ok(ApiResponse<PlayListResponse>.Success(
                    playList,
                    "Playlist created successfully."
                ));
            }
            catch (ArgumentException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (MySqlException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpPut("{idPlayList}")]
        public async Task<IActionResult> UpdatePlayList(
            int idPlayList,
            [FromBody] UpdatePlayListRequest request
        )
        {
            try
            {
                var playList = await _playListService.UpdatePlayListAsync(
                    idPlayList,
                    request
                );

                if (playList == null)
                {
                    return NotFound(ApiResponse<string>.Error("Playlist not found."));
                }

                return Ok(ApiResponse<PlayListResponse>.Success(
                    playList,
                    "Playlist updated successfully."
                ));
            }
            catch (ArgumentException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (MySqlException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpDelete("{idPlayList}")]
        public async Task<IActionResult> DeletePlayList(int idPlayList)
        {
            try
            {
                var message = await _playListService.DeletePlayListAsync(idPlayList);

                return Ok(ApiResponse<string>.Success(
                    message,
                    "Playlist deleted successfully."
                ));
            }
            catch (ArgumentException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (MySqlException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpGet("{idPlayList}/songs")]
        public async Task<IActionResult> GetSongsByPlayList(int idPlayList)
        {
            try
            {
                var songs = await _playListService.GetSongsByPlayListAsync(idPlayList);

                return Ok(ApiResponse<IEnumerable<SongByPlayListResponse>>.Success(
                    songs,
                    "Songs by playlist retrieved successfully."
                ));
            }
            catch (ArgumentException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (MySqlException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpPost("{idPlayList}/songs")]
        public async Task<IActionResult> AddSongToPlayList(
            int idPlayList,
            [FromBody] AssignSongRequest request
        )
        {
            try
            {
                var assignedSong = await _playListService.AddSongToPlayListAsync(
                    idPlayList,
                    request
                );

                if (assignedSong == null)
                {
                    return BadRequest(ApiResponse<string>.Error(
                        "Could not assign song to playlist."
                    ));
                }

                return Ok(ApiResponse<AssignedSongResponse>.Success(
                    assignedSong,
                    "Song assigned to playlist successfully."
                ));
            }
            catch (ArgumentException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (MySqlException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpDelete("{idPlayList}/songs/{idSong}")]
        public async Task<IActionResult> RemoveSongFromPlayList(
            int idPlayList,
            int idSong
        )
        {
            try
            {
                var message = await _playListService.RemoveSongFromPlayListAsync(
                    idPlayList,
                    idSong
                );

                return Ok(ApiResponse<string>.Success(
                    message,
                    "Song removed from playlist successfully."
                ));
            }
            catch (ArgumentException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (MySqlException ex)
            {
                return BadRequest(ApiResponse<string>.Error(ex.Message));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }
    }
}
