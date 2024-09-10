import { useState } from "react";
import { validators } from "../../util/commonFunction";

export default function SignUp() {
  // 회원가입 데이터
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
    passwordConfirm: "",
  });

  // 회원가입 핸들러
  const handleSignUpBtn = async () => {
    console.log("현재 입력값 : ", formData);
    const hasErrors = inputCheck();
    console.log("유효성 검사 결과 (오류 있음) :", hasErrors);
    // 유효성 검사 오류 있으면 true 반환 오류 없으면 false 반환
    if (!inputCheck()) {
      console.log("회원가입 실행");
    }
  };

  // 회원가입 유효성 검사 에러메시지
  const [formError, setFormErrors] = useState({});

  // null 체크
  const inputCheck = () => {
    let formErrorInfo = {};

    // 이름
    if (!validators.name(formData.name)) {
      formErrorInfo.name = "규칙에 맞는 이름을 사용해주세요.";
    }
    // 이메일
    if (!validators.email(formData.email)) {
      formErrorInfo.email = "이메일 형식이 잘못되었습니다.";
    }
    // 휴대폰 번호
    if (!validators.phone(formData.phone)) {
      formErrorInfo.phone = "규칙에 맞는 휴대폰 번호를 입력해주세요.";
    }
    // 비밀번호
    if (!validators.password(formData.password)) {
      formErrorInfo.password = "비밀번호 형식이 잘못되었습니다.";
    }
    // 비밀번호 확인
    if (formData.password !== formData.passwordConfirm) {
      formErrorInfo.passwordConfirm =
        "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
    }
    setFormErrors(formErrorInfo);

    // 오류가 있는지 여부 반환
    return Object.keys(formErrorInfo).length > 0;
  };

  return (
    <>
      <div className="signUp">SignUp</div>

      <div>
        <label htmlFor="html">이름</label>
        <input
          type="text"
          id="name"
          maxLength="16"
          onChange={(e) => {
            setFormData({ ...formData, name: e.target.value });
          }}
        ></input>
        {formError.name && <p>{formError.name}</p>}
      </div>

      <div>
        <label htmlFor="email">이메일</label>
        <input
          type="email"
          id="email"
          onChange={(e) => {
            setFormData({ ...formData, email: e.target.value });
          }}
        ></input>
        <button type="submit">이메일 전송</button>
        {formError.email && <p>{formError.email}</p>}
      </div>

      <div>
        <label htmlFor="phone">휴대폰 번호(숫자만)</label>
        <input
          type="tel"
          id="phone"
          onChange={(e) => {
            setFormData({ ...formData, phone: e.target.value });
          }}
        ></input>
        {formError.phone && <p>{formError.phone}</p>}
      </div>

      <div>
        <label htmlFor="password">비밀번호</label>
        <input
          type="password"
          id="password"
          onChange={(e) => {
            setFormData({ ...formData, password: e.target.value });
          }}
        ></input>
        {formError.password && <p>{formError.password}</p>}
      </div>

      <div>
        <label htmlFor="passwordConfrim">비밀번호 확인</label>
        <input
          type="password"
          id="passwordConfrim"
          onChange={(e) => {
            setFormData({ ...formData, passwordConfirm: e.target.value });
          }}
        ></input>
        {formError.passwordConfirm && <p>{formError.passwordConfirm}</p>}
      </div>

      <div>
        <button type="button" onClick={handleSignUpBtn}>
          회원가입하기
        </button>
      </div>
    </>
  );
}
