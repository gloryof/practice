export const SideMenu = () => {
    return (
      <nav className="side-menu">
      <h1>メニュー</h1>
      <ul className="side-menu-list">
        <li><a href="/top">TOP</a></li>
        <li><a href="/reviews">レビュー</a></li>
        <li><a href="/account">アカウント</a></li>
      </ul>
      <div className="logout-area">
        <button><a href="/login">ログアウト</a></button>
      </div>
    </nav>
    );
};