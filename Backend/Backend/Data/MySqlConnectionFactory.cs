using MySqlConnector;
using System.Data;

namespace Backend.Data
{
    public class MySqlConnectionFactory
    {
        private readonly string _connectionString;

        public MySqlConnectionFactory(IConfiguration configuration) 
        {
            _connectionString = configuration.GetConnectionString("MySqlConnection") ?? throw new Exception("No se pudo conectar");
        
        }

        public IDbConnection CreateConnection()
        { 
            return new MySqlConnection(_connectionString);
        }

    }
}
