// 이메일 정규식 체크 공통함수
export const validateEmail = (email) => {
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  return emailPattern.test(email);
};

// 비밀번호 정규식 체크 공통함수
export const validatePassword = (password) => {
  const passwordPattern =
    /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()-]).{8,20}$/;
  return passwordPattern.test(password);
};
