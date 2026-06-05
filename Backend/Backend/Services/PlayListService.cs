using Backend.DTOs.PlayList;
using Backend.DTOs.Song;
using Backend.Repositories.Interfaces;
using Backend.Services.Interfaces;

namespace Backend.Services
{
    public class PlayListService : IPlayListService
    {
        private readonly IPlayListRepository _playListRepository;

        public PlayListService(IPlayListRepository playListRepository)
        {
            _playListRepository = playListRepository;
        }

        public async Task<IEnumerable<PlayListResponse>> GetPlayListsAsync()
        {
            var playLists = await _playListRepository.GetPlayListsAsync();

            return playLists.Select(playList => new PlayListResponse
            {
                IdPlayList = playList.IdPlayList,
                NamePlayList = playList.NamePlayList,
                Information = playList.Information,
                DateCreate = playList.DateCreate
            });
        }

        public async Task<PlayListResponse?> CreatePlayListAsync(CreatePlayListRequest request)
        {
            if (string.IsNullOrWhiteSpace(request.NamePlayList))
            {
                throw new ArgumentException("The playlist name is required.");
            }

            var playList = await _playListRepository.CreatePlayListAsync(
                request.NamePlayList.Trim(),
                request.Information?.Trim()
            );

            if (playList == null)
            {
                return null;
            }

            return new PlayListResponse
            {
                IdPlayList = playList.IdPlayList,
                NamePlayList = playList.NamePlayList,
                Information = playList.Information,
                DateCreate = playList.DateCreate
            };
        }

        public async Task<PlayListResponse?> UpdatePlayListAsync(
            int idPlayList,
            UpdatePlayListRequest request
        )
        {
            if (idPlayList <= 0)
            {
                throw new ArgumentException("The playlist id is invalid.");
            }

            if (string.IsNullOrWhiteSpace(request.NamePlayList))
            {
                throw new ArgumentException("The playlist name is required.");
            }

            var playList = await _playListRepository.UpdatePlayListAsync(
                idPlayList,
                request.NamePlayList.Trim(),
                request.Information?.Trim()
            );

            if (playList == null)
            {
                return null;
            }

            return new PlayListResponse
            {
                IdPlayList = playList.IdPlayList,
                NamePlayList = playList.NamePlayList,
                Information = playList.Information,
                DateCreate = playList.DateCreate
            };
        }

        public async Task<string> DeletePlayListAsync(int idPlayList)
        {
            if (idPlayList <= 0)
            {
                throw new ArgumentException("The playlist id is invalid.");
            }

            return await _playListRepository.DeletePlayListAsync(idPlayList);
        }

        public async Task<AssignedSongResponse?> AddSongToPlayListAsync(
            int idPlayList,
            AssignSongRequest request
        )
        {
            if (idPlayList <= 0)
            {
                throw new ArgumentException("The playlist id is invalid.");
            }

            if (request.IdSong <= 0)
            {
                throw new ArgumentException("The song id is invalid.");
            }

            var assignedSong = await _playListRepository.AddSongToPlayListAsync(
                idPlayList,
                request.IdSong
            );

            if (assignedSong == null)
            {
                return null;
            }

            return new AssignedSongResponse
            {
                IdPlayListSong = assignedSong.IdPlayListSong,
                IdPlayList = assignedSong.IdPlayList,
                IdSong = assignedSong.IdSong,
                Title = assignedSong.Title,
                Artist = assignedSong.Artist,
                Album = assignedSong.Album,
                Genre = assignedSong.Genre,
                LapseTime = assignedSong.LapseTime,
                AssignmentDate = assignedSong.AssignmentDate
            };
        }

        public async Task<string> RemoveSongFromPlayListAsync(
            int idPlayList,
            int idSong
        )
        {
            if (idPlayList <= 0)
            {
                throw new ArgumentException("The playlist id is invalid.");
            }

            if (idSong <= 0)
            {
                throw new ArgumentException("The song id is invalid.");
            }

            return await _playListRepository.RemoveSongFromPlayListAsync(
                idPlayList,
                idSong
            );
        }

        public async Task<IEnumerable<SongByPlayListResponse>> GetSongsByPlayListAsync(
            int idPlayList
        )
        {
            if (idPlayList <= 0)
            {
                throw new ArgumentException("The playlist id is invalid.");
            }

            var songs = await _playListRepository.GetSongsByPlayListAsync(idPlayList);

            return songs.Select(song => new SongByPlayListResponse
            {
                IdSong = song.IdSong,
                Title = song.Title,
                Artist = song.Artist,
                Album = song.Album,
                Genre = song.Genre,
                LapseTime = song.LapseTime,
                AssignmentDate = song.AssignmentDate
            });
        }
    }
}
