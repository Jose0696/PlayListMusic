using Backend.DTOs.PlayList;
using Backend.DTOs.Song;

namespace Backend.Services.Interfaces
{
    public interface IPlayListService
    {
        Task<IEnumerable<PlayListResponse>> GetPlayListsAsync();

        Task<PlayListResponse?> CreatePlayListAsync(CreatePlayListRequest request);

        Task<PlayListResponse?> UpdatePlayListAsync(
            int idPlayList,
            UpdatePlayListRequest request
        );

        Task<string> DeletePlayListAsync(int idPlayList);

        Task<AssignedSongResponse?> AddSongToPlayListAsync(
            int idPlayList,
            AssignSongRequest request
        );

        Task<string> RemoveSongFromPlayListAsync(
            int idPlayList,
            int idSong
        );

        Task<IEnumerable<SongByPlayListResponse>> GetSongsByPlayListAsync(
            int idPlayList
        );
    }
}
