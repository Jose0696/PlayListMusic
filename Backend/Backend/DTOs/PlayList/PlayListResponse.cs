namespace Backend.DTOs.PlayList
{
    public class PlayListResponse
    {
        public int IdPlayList { get; set; }
        public string NamePlayList { get; set; } = string.Empty;
        public string? Information { get; set; }
        public DateTime DateCreate { get; set; }
    }
}
