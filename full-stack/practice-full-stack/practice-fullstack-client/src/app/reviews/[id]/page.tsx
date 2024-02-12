
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
          <dt>タイトル</dt>
          <dd>レビュータイトル1</dd>
          <dt>レーティング</dt>
          <dd>★★★</dd>
          <dt>レビュー日時</dt>
          <dd>2024-02-01 17:00</dd>
        </dl>
      </div>
      <div>
        <button><a href="/reviews">一覧に戻る</a></button>
      </div>
    </div>
  )
}