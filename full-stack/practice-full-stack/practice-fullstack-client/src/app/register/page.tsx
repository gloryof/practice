
export default function Login() {
  return (
    <div>
      <h1>ユーザ登録</h1>
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
        <a href="/login">ログイン</a>
      </div>
    </div>
  )
}