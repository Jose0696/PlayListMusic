using Backend.Repositories.Interfaces;
using Backend.Data;
using Backend.Models;
using Dapper;
using System.Data;

namespace Backend.Repositories
{
    public class PlayListRepository : IPlayListRepository
    {
        private readonly MySqlConnectionFactory _connectionFactory;

        public PlayListRepository(MySqlConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
        }

        public async Task<IEnumerable<PlayList>> GetPlayListsAsync()
        {
            using var connection = _connectionFactory.CreateConnection();

            return await connection.QueryAsync<PlayList>(
                "sp_GetPlayLists",
                commandType: CommandType.StoredProcedure
            );
        }

        public async Task<PlayList?> CreatePlayListAsync(
            string namePlayList,
            string? information
        )
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_NamePlayList = namePlayList,
                p_Information = information
            };

            return await connection.QueryFirstOrDefaultAsync<PlayList>(
                "sp_CreatePlayList",
                parameters,
                commandType: CommandType.StoredProcedure
            );
        }

        public async Task<PlayList?> UpdatePlayListAsync(
            int idPlayList,
            string namePlayList,
            string? information
        )
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_IdPlayList = idPlayList,
                p_NamePlayList = namePlayList,
                p_Information = information
            };

            return await connection.QueryFirstOrDefaultAsync<PlayList>(
                "sp_UpdatePlayList",
                parameters,
                commandType: CommandType.StoredProcedure
            );
        }

        public async Task<string> DeletePlayListAsync(int idPlayList)
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_IdPlayList = idPlayList
            };

            return await connection.QueryFirstOrDefaultAsync<string>(
                "sp_DeletePlayList",
                parameters,
                commandType: CommandType.StoredProcedure
            ) ?? "PlayList deleted successfully";
        }

        public async Task<PlayListSongDetail?> AddSongToPlayListAsync(
            int idPlayList,
            int idSong
        )
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_IdPlayList = idPlayList,
                p_IdSong = idSong
            };

            return await connection.QueryFirstOrDefaultAsync<PlayListSongDetail>(
                "sp_AddSongToPlayList",
                parameters,
                commandType: CommandType.StoredProcedure
            );
        }

        public async Task<string> RemoveSongFromPlayListAsync(
            int idPlayList,
            int idSong
        )
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_IdPlayList = idPlayList,
                p_IdSong = idSong
            };

            return await connection.QueryFirstOrDefaultAsync<string>(
                "sp_RemoveSongFromPlayList",
                parameters,
                commandType: CommandType.StoredProcedure
            ) ?? "Song removed from playlist successfully";
        }

        public async Task<IEnumerable<SongByPlayList>> GetSongsByPlayListAsync(
            int idPlayList
        )
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_IdPlayList = idPlayList
            };

            return await connection.QueryAsync<SongByPlayList>(
                "sp_GetSongsByPlayList",
                parameters,
                commandType: CommandType.StoredProcedure
            );
        }
    }
}
