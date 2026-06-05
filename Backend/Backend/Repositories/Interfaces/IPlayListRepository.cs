using Backend.Models;

namespace Backend.Repositories.Interfaces
{
    public interface IPlayListRepository
    {
        Task<IEnumerable<PlayList>> GetPlayListsAsync();

        Task<PlayList?> CreatePlayListAsync(
            string namePlayList,
            string? information
        );

        Task<PlayList?> UpdatePlayListAsync(
            int idPlayList,
            string namePlayList,
            string? information
        );

        Task<string> DeletePlayListAsync(int idPlayList);

        Task<PlayListSongDetail?> AddSongToPlayListAsync(
            int idPlayList,
            int idSong
        );

        Task<string> RemoveSongFromPlayListAsync(
            int idPlayList,
            int idSong
        );

        Task<IEnumerable<SongByPlayList>> GetSongsByPlayListAsync(
            int idPlayList
        );
    }
}
