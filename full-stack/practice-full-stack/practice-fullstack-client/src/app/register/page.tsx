import Link from "next/link";

export default function Login() {
  return (
    <main>
      <h1>ユーザ登録</h1>
      <form className="input-form" method="POST">
        <div>
          <dl>
            <dt>ユーザID</dt>
            <dd><input type="text" name="user-id" id="user-id" /></dd>
            <dt>パスワード</dt>
            <dd><input type="password" name="password" id="password" /></dd>
          </dl>
        </div>
        <div>
          <button>ユーザ作成</button>
        </div>
        <div>
          <Link href="/login">ログイン</Link>
        </div>
      </form>
    </main>
  )
}