import { useState } from "react";
import { passwordResetApi, sendCodeApi } from "../../service/authService";
import { aesDecrypt } from "../../util/aesCrypto";
import { useNavigate } from "react-router-dom";
import Verification from "./Verification";

export default function PwdReset() {
  const nav = useNavigate();
  const [step, setStep] = useState({
    level1: true,
    level2: false,
    level3: false,
  });

  const [formData, setFormData] = useState({
    email: null,
    code: null,
    password: null,
    passwordConfirm: null,
  });

  const [decryptValue, setDecryptValue] = useState();

  const prop = {
    formData,
    setFormData,
    step,
    setStep,
    decryptValue,
  };

  const handleSendClick = () => {
    callSendCode();
  };

  const callSendCode = async () => {
    const response = await sendCodeApi(formData.email);
    if (response.status === 200) {
      setDecryptValue(aesDecrypt(response.data));
      setStep({ ...step, level1: false, level2: true });
    }
  };

  const handleSubmit = () => {
    if (formData.password === formData.passwordConfirm) {
      callPasswordReset();
    }
  };

  const callPasswordReset = async () => {
    const param = {
      pmUserId: formData.email,
      pmPwd: formData.password,
    };
    const response = await passwordResetApi(param);
    if (response.status === 200) {
      nav("/signIn");
    } else {
      alert("뭔가 잘못됨");
    }
  };
  return (
    <>
      <h2>PWD RESET</h2>
      {step.level1 && (
        <>
          <span>이메일</span>
          <input
            type="text"
            onChange={(e) => {
              setFormData({ ...formData, email: e.target.value });
            }}
          />
          <button onClick={handleSendClick}>발송</button>
        </>
      )}

      {step.level2 && <Verification prop={prop} />}
      {step.level3 && (
        <>
          <span>새 비밀번호 입력</span>
          <input
            type="password"
            onChange={(e) => {
              setFormData({ ...formData, password: e.target.value });
            }}
          />
          <br />
          <span>새 비밀번호 확인</span>
          <input
            type="password"
            onChange={(e) => {
              setFormData({ ...formData, passwordConfirm: e.target.value });
            }}
          />
          <button onClick={handleSubmit}>설정완료</button>
        </>
      )}
    </>
  );
}
