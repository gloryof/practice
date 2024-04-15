import { useState } from "react"
import { MouseEventHandler } from "react"
import Link from 'next/link'
import { signIn } from "@/auth"


interface LoginPageProps {
    redirectToRegister: MouseEventHandler,
    csrfToken: string
}

export default function LoginPage({
    redirectToRegister,
    csrfToken
}: LoginPageProps) {
  const [userId, setUserId] = useState("")
  const [password, setPassword] = useState("")
  return (
    <main>
      <h1>ログイン</h1>
      <form
        className="input-form"
        method="POST"
        action={async () => {
          await signIn("credentials", {
            userId,
            password
          })
        }}>
        <input name="csrfToken" type="hidden" defaultValue={csrfToken} />
        <div>
          <dl>
            <dt>ユーザID</dt>
            <dd>
              <input
                type="text"
                name="user-id"
                id="user-id"
                onChange={(e) => setUserId(e.target.value)}
              />
              </dd>
            <dt>パスワード</dt>
            <dd>
              <input
                type="password"
                name="password"
                id="password"
                onChange={(e) => setPassword(e.target.value)}
              />
            </dd>
          </dl>
        </div>
        <div>
          <button type="submit">ログイン</button>
        </div>
      </form>
      <div>
        <Link href="/register">ユーザ登録</Link>
      </div>
    </main>
  )
  }