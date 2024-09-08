import { useState } from "react";
import { Link } from "react-router-dom";
import { menuList } from "../../util/dataJson";

export default function Header() {
  const [hoverMenu, setHoverMenu] = useState();
  return (
    <>
      <div className="header">
        <div className="wrap">
          <Link to="/">
            <div className="logo">Code Lap</div>
          </Link>

          <ul className="menu">
            {menuList.map((data, i) => (
              <li key={i}>
                <span
                  onMouseOver={() => {
                    setHoverMenu(data);
                  }}
                >
                  {data.title}
                </span>

                <div>
                  {hoverMenu === data && (
                    <ul>
                      {hoverMenu?.child.map((subData, subIndex) => (
                        <li key={subIndex}>
                          <Link to={subData?.to}>
                            <span
                              onClick={() => {
                                setHoverMenu(null);
                              }}
                            >
                              {subData.subTitle}
                            </span>
                          </Link>
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
              </li>
            ))}
          </ul>
          {hoverMenu && (
            <div
              className="menu_bg"
              onMouseOut={() => {
                setHoverMenu(null);
              }}
            />
          )}
          <Link to="/signIn" className="login">
            <span className="material-symbols-outlined">account_circle</span>
            <ul>
              <li>로그인</li>
              <li>마이페이지</li>
            </ul>
          </Link>
        </div>
      </div>
    </>
  );
}
