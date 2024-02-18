
export default function Login() {
  return (
    <main>
      <h1>ログイン</h1>
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
          <button><a href="/top">ログイン</a></button>
        </div>
        <div>
          <a href="/register">
            <button>ユーザ登録</button>
          </a>
        </div>
      </form>
    </main>
  )
}