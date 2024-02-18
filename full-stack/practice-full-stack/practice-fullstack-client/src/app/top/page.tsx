import { SideMenu } from "@/components/common/side-menu/SideMenu"


export default function Login() {
  return (
    <main className="login-contents">
      <SideMenu/>
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
    </main>
  )
}