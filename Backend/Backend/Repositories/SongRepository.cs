using Backend.Data;
using Backend.Models;
using Backend.Repositories.Interfaces;
using Dapper;
using System.Data;

namespace Backend.Repositories
{
    public class SongRepository : ISongRepository
    {
        private readonly MySqlConnectionFactory _connectionFactory;

        public SongRepository(MySqlConnectionFactory connectionFactory)
        {
            _connectionFactory = connectionFactory;
        }

        public async Task<IEnumerable<Song>> GetSongsCatalogAsync()
        {
            using var connection = _connectionFactory.CreateConnection();

            return await connection.QueryAsync<Song>(
                "sp_GetSongsCatalog",
                commandType: CommandType.StoredProcedure
            );
        }

        public async Task<Song?> InsertSongCatalogAsync(
            string title,
            string artist,
            string? album,
            string? genre,
            string? lapseTime
        )
        {
            using var connection = _connectionFactory.CreateConnection();

            var parameters = new
            {
                p_Title = title,
                p_Artist = artist,
                p_Album = album,
                p_Genre = genre,
                p_LapseTime = lapseTime
            };

            return await connection.QueryFirstOrDefaultAsync<Song>(
                "sp_InsertSongCatalog",
                parameters,
                commandType: CommandType.StoredProcedure
            );
        }
    }
}
