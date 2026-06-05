using Backend.DTOs.Song;
using Backend.Repositories.Interfaces;
using Backend.Services.Interfaces;

namespace Backend.Services
{
    public class SongService : ISongService
    {
        private readonly ISongRepository _songRepository;

        public SongService(ISongRepository songRepository)
        {
            _songRepository = songRepository;
        }

        public async Task<IEnumerable<SongResponse>> GetSongsCatalogAsync()
        {
            var songs = await _songRepository.GetSongsCatalogAsync();

            return songs.Select(song => new SongResponse
            {
                IdSong = song.IdSong,
                Title = song.Title,
                Artist = song.Artist,
                Album = song.Album,
                Genre = song.Genre,
                LapseTime = song.LapseTime
            });
        }

        public async Task<SongResponse?> InsertSongCatalogAsync(CreateSongRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.Title))
            {
                throw new ArgumentException("The song title is required.");
            }

            if (string.IsNullOrWhiteSpace(request.Artist))
            {
                throw new ArgumentException("The artist is required.");
            }

            if (string.IsNullOrWhiteSpace(request.Album))
            {
                throw new ArgumentException("The album is required.");
            }

            if (string.IsNullOrWhiteSpace(request.Genre))
            {
                throw new ArgumentException("The genre is required.");
            }

            if (string.IsNullOrWhiteSpace(request.LapseTime))
            {
                throw new ArgumentException("The lapse time is required.");
            }

            var song = await _songRepository.InsertSongCatalogAsync(
                request.Title.Trim(),
                request.Artist.Trim(),
                request.Album.Trim(),
                request.Genre.Trim(),
                request.LapseTime.Trim()
            );

            if (song == null)
            {
                return null;
            }

            return new SongResponse
            {
                IdSong = song.IdSong,
                Title = song.Title,
                Artist = song.Artist,
                Album = song.Album,
                Genre = song.Genre,
                LapseTime = song.LapseTime
            };
        }
    }
}
