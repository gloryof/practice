import { SideMenu } from "@/components/common/side-menu/SideMenu"


export default function Login() {
  return (
    <main className="login-contents">
      <SideMenu/>
      <form className="input-form" method="POST">
        <div>
          <dl>
            <dt>ユーザID</dt>
            <dd><input type="text" name="user-id" id="user-id" /></dd>
            <dt>パスワード</dt>
            <dd><input type="password" name="password" id="password" /></dd>
          </dl>
          <button>更新</button>
        </div>
      </form>

    </main>
  )
}