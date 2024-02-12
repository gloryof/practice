
export default function Login() {
  return (
    <div>
      <div>
        <h1>メニュー</h1>
        <ul>
          <li><a href="/reviews">レビュー</a></li>
          <li><a href="/account">アカウント</a></li>
        </ul>
        <div>
          <button><a href="/login">ログアウト</a></button>
        </div>
      </div>
      <div>
        <dl>
          <dt>ユーザID</dt>
          <dd><input type="text" name="user-id" id="user-id" /></dd>
          <dt>パスワード</dt>
          <dd><input type="password" name="password" id="password" /></dd>
        </dl>
        <button>更新</button>
      </div>

    </div>
  )
}