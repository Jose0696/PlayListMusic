namespace Backend.DTOs.Song
{
    public class SongByPlayListResponse
    {
        public int IdSong { get; set; }
        public string Title { get; set; } = string.Empty;
        public string Artist { get; set; } = string.Empty;
        public string? Album { get; set; }
        public string? Genre { get; set; }
        public string? LapseTime { get; set; }
        public DateTime AssignmentDate { get; set; }
    }
}
