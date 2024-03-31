"use client";
import { MouseEventHandler } from "react";

 

interface RegisterPageProps {
  registerUser: MouseEventHandler
  login: MouseEventHandler
}

export default function RegisterPage({
  registerUser,
  login
}: RegisterPageProps) {

  return (
    <main>
      <h1>ユーザ登録</h1>
      <form className="input-form" method="POST">
        <div>
          <dl>
            <dt>ユーザID</dt>
            <dd><input type="text" name="user-id" id="user-id" /></dd>
            <dt>誕生日</dt>
            <dd><input type="date" name="birthday" id="birthday" /></dd>
            <dt>パスワード</dt>
            <dd><input type="password" name="password" id="password" /></dd>
          </dl>
        </div>
        <div>
          <button onClick={registerUser}>ユーザ作成</button>
        </div>
        <div>
          <button onClick={login}>ログイン</button>
        </div>
      </form>
    </main>
  )
}