import { SideMenu } from "@/components/common/side-menu/SideMenu"

export default function Login() {
  return (
    <main className="login-contents">
      <SideMenu/>
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
    </main>
  )
}