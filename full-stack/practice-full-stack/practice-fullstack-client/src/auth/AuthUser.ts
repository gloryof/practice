import { User } from "next-auth"

export class AuthUser implements User {
    id: string
    token: string

    constructor (
        id: string,
        token: string
    ) {
        this.id = id
        this.token = token
    }
}