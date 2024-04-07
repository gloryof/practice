import { fail } from "assert"
import { ApiClient } from "./api-clinet"
import { WebErrorResponse, WebResponse } from "./web-reseponse"

export type LoginUserRequest = {
    userId: string,
    password: string,
}

export type LoginUserResponse = {
    token: string,
}

export async function loginUser(request: LoginUserRequest): Promise<WebResponse<LoginUserResponse>> {
    const client = ApiClient.createApiClient()
    return client.post<LoginUserRequest, LoginUserResponse>(
        {
            path: "/api/auth/login",
            request: request
        }
    )
}
