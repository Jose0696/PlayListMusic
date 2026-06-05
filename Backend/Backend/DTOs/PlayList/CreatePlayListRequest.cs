using System.ComponentModel.DataAnnotations;

namespace Backend.DTOs.PlayList
{
    public class CreatePlayListRequest
    {
        [Required(ErrorMessage = "The playlist name is required.")]
        [StringLength(100, ErrorMessage = "The playlist name cannot exceed 100 characters.")]
        public string NamePlayList { get; set; } = string.Empty;

        [StringLength(255, ErrorMessage = "The playlist information cannot exceed 255 characters.")]
        public string? Information { get; set; }
    }
}
