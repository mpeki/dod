import { AxiosError } from 'axios';

export default function handleAxiosError(err: any): never {
    const axiosError = err as AxiosError;
    const statusCode = axiosError.response?.status;

    // Handle 401 Unauthorized status
    if (statusCode === 401) {
        throw new Error("Error: Unauthorized access. Please login again.");
    }

    const errorData = axiosError.response?.data;
    const { message = "Unknown Error", error = "unknown.error" } = errorData as { message?: string, error?: string };
    throw new Error(`${error} ${message}`);
}