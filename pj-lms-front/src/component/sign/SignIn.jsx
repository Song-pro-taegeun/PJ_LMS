import { useState } from "react";
import { signInApi } from "../../service/authService";

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
    if (response.status === 200) {
      localStorage.setItem("pl_access_token", response.data.accessToken);
      localStorage.setItem("pl_user_info", JSON.stringify(response.data));

      const userInfo = localStorage.getItem("pl_user_info");
      console.log(JSON.parse(userInfo));
    }
  };

  return (
    <>
      <div className="signIn">SignIn</div>
      <input
        type="text"
        onChange={(e) => {
          setFormData({ ...formData, email: e.target.value });
        }}
      />
      {formError.emailError && <span style={{ color: "red" }}>{formError.emailError}</span>}

      <input
        type="password"
        onChange={(e) => {
          setFormData({ ...formData, password: e.target.value });
        }}
      />
      {formError.passwordError && <span style={{ color: "red" }}>{formError.passwordError}</span>}
      <button onClick={handleSignInBtn}>로그인</button>
    </>
  );
}
