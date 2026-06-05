using System.ComponentModel.DataAnnotations;

namespace Backend.DTOs.Song
{
    public class CreateSongRequest
    {
        [Required(ErrorMessage = "The title is required.")]
        [StringLength(150, ErrorMessage = "The title cannot exceed 150 characters.")]
        public string Title { get; set; } = string.Empty;

        [Required(ErrorMessage = "The artist is required.")]
        [StringLength(150, ErrorMessage = "The artist cannot exceed 150 characters.")]
        public string Artist { get; set; } = string.Empty;

        [StringLength(150, ErrorMessage = "The album cannot exceed 150 characters.")]
        public string? Album { get; set; }

        [StringLength(100, ErrorMessage = "The genre cannot exceed 100 characters.")]
        public string? Genre { get; set; }

        [StringLength(10, ErrorMessage = "The lapse time cannot exceed 10 characters.")]
        public string? LapseTime { get; set; }
    }
}
