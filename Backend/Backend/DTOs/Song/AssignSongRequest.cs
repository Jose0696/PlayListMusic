using System.ComponentModel.DataAnnotations;

namespace Backend.DTOs.Song
{
    public class AssignSongRequest
    {
        [Required(ErrorMessage = "The song id is required.")]
        public int IdSong { get; set; }
    }
}
