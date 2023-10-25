import { AxiosError } from 'axios';

export class ApiError extends Error {
    constructor(public messageKey: string, public errorCode: number, message?: string) {
        super(message);
    }
}

export default function handleAxiosError(err: any): never {
    const axiosError = err as AxiosError;
    const statusCode = axiosError.response?.status;

    if (!statusCode){
        if(axiosError.code === 'ERR_NETWORK'){
            throw new ApiError("network.error", 1, "Error: Network error. Please check your connection.");
        }
    }


    // Handle 401 Unauthorized status
    if (statusCode === 401) {
        throw new ApiError("unauthorized.error", statusCode, "Error: Unauthorized access. Please login again.");
    }

    const errorData = axiosError.response?.data;
    const { message = "Unknown Error", error = "unknown.error" } = errorData as { message?: string, error?: string };
    throw new ApiError(error, statusCode || 666, `${error} ${message}`);
}