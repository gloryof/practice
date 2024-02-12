
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
        <ul>
          <li>
            <dl>
              <dt>タイトル</dt>
              <dd><a href="/reviews/1">レビュータイトル1</a></dd>
              <dt>レーティング</dt>
              <dd>★★★</dd>
              <dt>レビュー日時</dt>
              <dd>2024-02-01 17:00</dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>タイトル</dt>
              <dd><a href="/reviews/2">レビュータイトル2</a></dd>
              <dt>レーティング</dt>
              <dd>★★★</dd>
              <dt>レビュー日時</dt>
              <dd>2024-02-01 17:00</dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>タイトル</dt>
              <dd><a href="/reviews/3">レビュータイトル3</a></dd>
              <dt>レーティング</dt>
              <dd>★★★</dd>
              <dt>レビュー日時</dt>
              <dd>2024-02-01 17:00</dd>
            </dl>
          </li>
        </ul>
      </div>
    </div>
  )
}