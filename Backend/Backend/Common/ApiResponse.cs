namespace Backend.Common
{
    public class ApiResponse<T>
    {
        public bool Exitoso { get; set; }
        public string Mensaje { get; set; } = string.Empty;
        public T? Data { get; set; }

        public static ApiResponse<T> Success(
            T data,
            string mensaje = "Operation completed successfully."
        )
        {
            return new ApiResponse<T>
            {
                Exitoso = true,
                Mensaje = mensaje,
                Data = data
            };
        }

        public static ApiResponse<T> Error(string mensaje)
        {
            return new ApiResponse<T>
            {
                Exitoso = false,
                Mensaje = mensaje,
                Data = default
            };
        }
    }
}