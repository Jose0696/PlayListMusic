namespace Backend.Models
{
    public class PlayListSong
    {
        public int IdPlayListSong { get; set; }
        public int IdPlayList { get; set; }
        public int IdSong { get; set; }
        public DateTime AssignmentDate { get; set; }
    }
}
