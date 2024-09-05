import { Link } from "react-router-dom";

export default function Header() {
  return (
    <>
      <div className="header">header</div>
      <Link to="/signIn">
        <div>로그인</div>
      </Link>
      <Link to="/signInCopy">
        <div>로그인 COPY</div>
      </Link>
    </>
  );
}