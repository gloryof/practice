import { fail } from "assert"
import { ApiClient } from "./api-clinet"
import { WebErrorResponse } from "./web-reseponse"

export type RegisterUserRequest = {
    name: string,
    password: string,
    birthday: string
}

export type RegisterUserResponse = {
    id: string,
}


export async function registerUser(request: RegisterUserRequest) {
    const client = ApiClient.createApiClient()
    const successFn = (res: RegisterUserResponse): void  => {
        alert("User Id [" + res.id + "]で登録されました")
    }
    const failFn = (res: WebErrorResponse): void => {
        alert("登録に失敗しました")
    }
    
    client.post<RegisterUserRequest, RegisterUserResponse>(
        {
            path: "/api/user/register",
            request: request
        }
    )
        .then((response) => {
            response
                .map(
                    successFn,
                    failFn
                )
        })
}
