import { MouseEventHandler } from "react"


interface LoginPageProps {
    loginUser: (formData: FormData) => void
    redirectToRegister: MouseEventHandler
  }

export default function LoginPage({
    loginUser,
    redirectToRegister
}: LoginPageProps) {
    return (
      <main>
        <h1>ログイン</h1>
        <form className="input-form" action={loginUser}>
          <div>
            <dl>
              <dt>ユーザID</dt>
              <dd><input type="text" name="user-id" id="user-id" /></dd>
              <dt>パスワード</dt>
              <dd><input type="password" name="password" id="password" /></dd>
            </dl>
          </div>
          <div>
            <button type="submit">ログイン</button>
          </div>
          <div>
            <button onClick={redirectToRegister}>ユーザ登録</button>
          </div>
        </form>
      </main>
    )
  }