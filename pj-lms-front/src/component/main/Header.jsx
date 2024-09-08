import { Link } from "react-router-dom";

export default function Header() {
  return (
    <>
      <div className="header">
        <div className="wrap">
          <Link to="/">
            <div className="logo">Code Lap</div>
          </Link>
          <ul className="menu">
            <li>
              <span>MENU1</span>
              <ul>
                <li>
                  <span>MENU1</span>
                </li>
                <li>
                  <span>MENU1</span>
                </li>
                <li>
                  <span>MENU1</span>
                </li>
              </ul>
            </li>
            <li>
              <span>MENU2</span>
            </li>
            <li>
              <span>MENU3</span>
            </li>
            <li>
              <span>MENU4</span>
            </li>
          </ul>
          <Link to="/signIn" className="login">
            <div>로그인</div>
          </Link>
          <div className="menu_bg"></div>
        </div>
      </div>
    </>
  );
}
