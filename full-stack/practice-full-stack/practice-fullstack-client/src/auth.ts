import { AuthUser } from "@/auth/AuthUser";
import { loginUser } from "@/lib/api/login-user";
import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";


const nextAuth = NextAuth({
    providers: [
        CredentialsProvider({
            name: "credentials",
            credentials: {
                userId: { label: "ユーザID", type: "text" },
                password: { label: "パスワード", type: "password" }
            },
            async authorize(credentials, req): Promise<AuthUser | null> {
                var result = await loginUser({
                    userId: credentials?.userId || "",
                    password: credentials?.password || ""
                })
                    .then((response) => {
                        var result = response.getResult()
                        if (result != null) {
                            return new AuthUser(
                                result.token,
                                result.token
                            )
                        } else {
                            return null
                        }
                    })
                return result
            }
        })
    ],
    pages: {
        signIn: "/login"
    },
    callbacks: {
        async signIn({ user, account, profile, email, credentials }) {
            console.log("SignIn!")
            return true
        },
        async redirect({ url, baseUrl }) {
            console.log("Callback[Redirect]")
            console.log({
                url: url,
                baseUrl: baseUrl
            })
            return baseUrl
        },
        async session({ session, user, token }) {
            console.log("Callback[Session]")
            console.log({
                session: session,
                user: user,
                token: token
            })
            return session
        },
        async jwt({ token, user, account, profile, isNewUser }) {
            console.log("Callback[jwt]")
            console.log({
                token: token,
                user: user,
                account: account,
                isNewUser: isNewUser
            })
            return token
        }
    },
    events: {
        async signIn(message) {
            console.log({
                label: "Events[SignIn]",
                message: message
            })
        },
        async signOut(message) {
            console.log({
                label: "Events[SignOut]",
                message: message
            })
        },
        async createUser(message) {
            console.log({
                label: "Events[CreateUser]",
                message: message
            })
        },
        async updateUser(message) {
            console.log({
                label: "Events[UpdateUser]",
                message: message
            })
        },
        async linkAccount(message) {
            console.log({
                label: "Events[LinkAccount]",
                message: message
            })
        },
        async session(message) {
            console.log({
                label: "Events[Session]",
                message: message
            })
        },
    },
    debug: true,
    logger: {
        error(code, metadata) {
            console.log(code, metadata)
        },
        warn(code) {
            console.log(code)
        },
        debug(code, metadata) {
            console.log(code, metadata)
        }
    }
})

export const { handelr, auth } = nextAuth