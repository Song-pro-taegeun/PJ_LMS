import { useState } from "react";
import { signInApiCopy } from "../../service/authService";

export default function SingInCopy() {
  // 로그인 useState
  const [formData, setFormData] = useState({
    email: null,
    password: null,
  });

  // 로그인 에러 useState
  const [formError, setFormError] = useState({
    emailError: null,
    passwordError: null,
  });

  // 로그인 버튼 함수
  const handleSignInBtn = async () => {
    if (nullCheck()) {
      signInApiCall();
    }
  };

  // 필수 체크
  const nullCheck = () => {
    let formErrorInfo = {
      emailError: formError.emailError,
      passwordError: formError.passwordError,
    };

    // 이메일 입력값 없을시
    if (!formData.email) {
      formErrorInfo.emailError = "이메일을 입력해야합니다.";
    } else {
      formErrorInfo.emailError = null;
    }

    // 비밀번호 입력값 없을시
    if (!formData.password) {
      formErrorInfo.passwordError = "비밀번호을 입력해야합니다.";
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

  // API 호출
  const signInApiCall = async () => {
    const param = {
      email: formData.email,
      password: formData.password,
    };

    const response = await signInApiCopy(param);
    if (response.status === 200) {
      localStorage.setItem("pl_access_token", response.data.accessToken);
      localStorage.setItem("pl_user_info", JSON.stringify(response.data));

      const userInfo = localStorage.getItem("pl_user_info");
      console.log(JSON.parse(userInfo));
    }
  };

  return (
    <>
      <div className="signInCopy">SignInCopy</div>
      <input
        type="text"
        onChange={(e) => {
          setFormData({ ...formData, email: e.target.value });
        }}
      />

      {formError.emailError && (
        <span style={{ color: "red" }}>{formError.emailError}</span>
      )}

      <input
        type="password"
        onChange={(e) => {
          setFormData({ ...formData, password: e.target.value });
        }}
      />

      {formError.passwordError && (
        <span style={{ color: "red" }}>{formError.passwordError}</span>
      )}

      <button onClick={handleSignInBtn}>로그인 버튼</button>
    </>
  );
}
