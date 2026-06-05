using Backend.Common;
using Backend.DTOs.Song;
using Backend.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using MySqlConnector;

namespace Backend.Controllers
{
    [ApiController]
    [Route("api/songs")]
    public class SongsController : ControllerBase
    {
        private readonly ISongService _songService;

        public SongsController(ISongService songService)
        {
            _songService = songService;
        }

        [HttpGet]
        public async Task<IActionResult> GetSongsCatalog()
        {
            try
            {
                var songs = await _songService.GetSongsCatalogAsync();

                return Ok(ApiResponse<IEnumerable<SongResponse>>.Success(
                    songs,
                    "Songs catalog retrieved successfully."
                ));
            }
            catch (Exception ex)
            {
                return StatusCode(500, ApiResponse<string>.Error(ex.Message));
            }
        }

        [HttpPost]
        public async Task<IActionResult> InsertSongCatalog([FromBody] CreateSongRequest request)
        {
            try
            {
                var song = await _songService.InsertSongCatalogAsync(request);

                if (song == null)
                {
                    return BadRequest(ApiResponse<string>.Error("Could not create song."));
                }

                return Ok(ApiResponse<SongResponse>.Success(
                    song,
                    "Song created successfully."
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
