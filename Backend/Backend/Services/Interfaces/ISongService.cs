using Backend.DTOs.Song;

namespace Backend.Services.Interfaces
{
    public interface ISongService
    {
        Task<IEnumerable<SongResponse>> GetSongsCatalogAsync();

        Task<SongResponse?> InsertSongCatalogAsync(CreateSongRequest request);
    }
}
