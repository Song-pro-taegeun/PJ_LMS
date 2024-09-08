import { useState } from "react";
import { signInApi } from "../../service/authService";
import { validateEmail } from "../../util/commonFunction";

export default function SignIn() {
  const [formData, setFormData] = useState({
    email: null,
    password: null,
  });

  const [formError, setFormError] = useState({
    emailError: null,
    passwordError: null,
  });

  // 로그인 핸들러
  const handleSignInBtn = async () => {
    if (inputCheck()) {
      signInApiCall();
    }
  };

  // 필수 체크
  const inputCheck = () => {
    let formErrorInfo = {
      emailError: formError.emailError,
      passwordError: formError.passwordError,
    };

    // 1. 이메일 정규식 check
    emailCheck();

    // 2. null check
    if (!formData.email) {
      formErrorInfo.emailError = "이메일을 입력해주세요.";
    } else {
      formErrorInfo.emailError = null;
    }

    if (!formData.password) {
      formErrorInfo.passwordError = "패스워드를 입력해주세요.";
    } else {
      formErrorInfo.passwordError = null;
    }
    setFormError(formErrorInfo);

    if (formErrorInfo.emailError || formErrorInfo.passwordError) {
      return false;
    } else {
      return true;
    }
  };

  // api 호출
  const signInApiCall = async () => {
    const param = {
      email: formData.email,
      password: formData.password,
    };

    const response = await signInApi(param);
    try {
      if (response.status === 200) {
        localStorage.setItem("pl_access_token", response.data.accessToken);
        localStorage.setItem("pl_user_info", JSON.stringify(response.data));

        const userInfo = localStorage.getItem("pl_user_info");
        console.log(JSON.parse(userInfo));
      }
    } catch (error) {
      console.log("🚀 ~ signInApiCall ~ error:", error);

      // 이메일 정규식이 정상적이라면 아이디 / 비밀번호가 잘못된 것임
      if (emailCheck()) {
        setFormError({
          emailError: "아이디를 확인해주세요.",
          passwordError: "비밀번호를 확인해주세요.",
        });
      }
    }
  };

  function emailCheck() {
    // 정규식 check
    if (!validateEmail(formData.email)) {
      setFormError({
        emailError: "이메일 형식이 잘못되었습니다.",
        passwordError: null,
      });
    }

    return validateEmail(formData.email); // 정상이면 true, 틀리면 false 반환
  }
  return (
    <>
      <div className="SignIn">
        <div className="wrap">
          <h5 className="title">로그인</h5>
          <div className="login-box">
            <div className="input id">
              <input
                type="text"
                className="inp"
                placeholder="아이디(영문 및 숫자조합)"
                required="아이디"
              ></input>
              <p className="error-msg">
                <span className="material-symbols-outlined">error</span>
                아이디를 정확하게 입력해주세요.
              </p>
            </div>
            <div className="input pw">
              <input
                type="password"
                className="inp"
                placeholder="비밀번호"
                required="비밀번호"
              ></input>
              <p className="error-msg">
                <span className="material-symbols-outlined">error</span>
                비밀번호를 정확하게 입력해주세요.
              </p>
            </div>

            <div className="pw_find">비밀번호 찾기</div>
            <div className="btn bt_green">로그인</div>
            <div className="btn join">회원가입</div>
          </div>
        </div>
      </div>
    </>
    // <>
    //   <div className="signIn">SignIn</div>
    //   <input
    //     type="text"
    //     onChange={(e) => {
    //       setFormData({ ...formData, email: e.target.value });
    //     }}
    //   />
    //   {formError.emailError && (
    //     <span style={{ color: "red" }}>{formError.emailError}</span>
    //   )}

    //   <input
    //     type="password"
    //     onChange={(e) => {
    //       setFormData({ ...formData, password: e.target.value });
    //     }}
    //   />
    //   {formError.passwordError && (
    //     <span style={{ color: "red" }}>{formError.passwordError}</span>
    //   )}
    //   <button onClick={handleSignInBtn}>로그인</button>
    // </>
  );
}
