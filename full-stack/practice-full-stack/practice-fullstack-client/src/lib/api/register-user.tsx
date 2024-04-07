import { fail } from "assert"
import { ApiClient } from "./api-clinet"
import { WebErrorResponse, WebResponse } from "./web-reseponse"

export type RegisterUserRequest = {
    name: string,
    password: string,
    birthday: string
}

export type RegisterUserResponse = {
    id: string,
}


export async function registerUser(request: RegisterUserRequest): Promise<WebResponse<RegisterUserResponse>> {
    const client = ApiClient.createApiClient()
    
    return client.post<RegisterUserRequest, RegisterUserResponse>(
        {
            path: "/api/user/register",
            request: request
        }
    )
}
