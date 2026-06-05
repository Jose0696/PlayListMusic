using Backend.Models;

namespace Backend.Repositories.Interfaces
{
    public interface ISongRepository
    {
        Task<IEnumerable<Song>> GetSongsCatalogAsync();

        Task<Song?> InsertSongCatalogAsync(
            string title,
            string artist,
            string? album,
            string? genre,
            string? lapseTime
        );
    }
}
