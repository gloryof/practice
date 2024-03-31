"use client";
import { MouseEventHandler } from "react";

 

interface RegisterPageProps {
  registerUser: (formData: FormData) => void
  login: MouseEventHandler
}

export default function RegisterPage({
  registerUser,
  login
}: RegisterPageProps) {

  return (
    <main>
      <h1>ユーザ登録</h1>
      <form className="input-form" action={registerUser}>
        <div>
          <dl>
            <dt>ユーザ名</dt>
            <dd><input type="text" name="name" id="name" /></dd>
            <dt>誕生日</dt>
            <dd><input type="date" name="birthday" id="birthday" /></dd>
            <dt>パスワード</dt>
            <dd><input type="password" name="password" id="password" /></dd>
          </dl>
        </div>
        <div>
          <button type="submit">ユーザ作成</button>
        </div>
      </form>
      <div>
        <button onClick={login}>ログイン</button>
      </div>
    </main>
  )
}