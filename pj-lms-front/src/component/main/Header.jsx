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

          {/* <ul className="menu">
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
              <span>MENU3</span>
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
              <span>MENU4</span>
            </li>
          </ul> */}
          <Link to="/signIn" className="login">
            <div>로그인</div>
          </Link>
          {/* <div className="menu_bg" /> */}
        </div>
      </div>
    </>
  );
}
