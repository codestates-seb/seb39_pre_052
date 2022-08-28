import SocialSignUp from "./SocialSignUp";
import { useState } from "react";

const SignUp = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  //유효성검사
  const [isEmail, setIsEmail] = useState(false);
  const [isPassword, setIsPassword] = useState(false);

  //빈 상태로 회원가입 버튼 누를 시 empty 메시지
  const [emptyEmailMsg, setEmptyEmailMsg] = useState("");
  const [emptyPasswordMsg, setEmptyPasswordMsg] = useState("");

  //오류메시지 상태저장
  const [emailMessage, setEmailMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");

  //Sign up 버튼 누르면 POST 요청하기
  const handleSubmit = (event) => {
    event.preventDefault();
    let newUser = { name: name, email: email, password: password };
    console.log(newUser);
    if (email.length === 0) {
      setEmptyEmailMsg("Email cannot be empty.");
    }
    if (password.length === 0) {
      setEmptyPasswordMsg("Password cannot be empty.");
    }
  };

  //유효성 체크 (정규 표현식 사용)
  const onChangeEmail = (e) => {
    const emailCurrent = e.target.value;
    setEmail(emailCurrent);
    const emailRegex =
      /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (!emailRegex.test(emailCurrent)) {
      setEmailMessage(`${emailCurrent} is not a valid email address`);
      setIsEmail(false);
    } else {
      setEmailMessage("");
      setIsEmail(true);
    }
  };

  const onChangePassword = (e) => {
    const passwordCurrent = e.target.value;
    setPassword(passwordCurrent);
    const passwordRegex =
      //   /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/; //영문, 숫자, 특수 1자 이상 8자리 필수
      /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&]{8,}$/; //영문, 숫자 1자이상 8자리 필수 //특수문자 포함은 되도 ok

    if (!passwordRegex.test(passwordCurrent)) {
      setPasswordMessage(
        "Passwords must contain at least eight characters, including at least 1 letter and 1 number."
      );
      setIsPassword(false);
    } else {
      setPasswordMessage("");
      setIsPassword(true);
    }
  };

  return (
    <div className="signup_wrapper">
      {/* 소셜 회원 가입 */}
      <SocialSignUp></SocialSignUp>

      {/* 일반 회원 가입 */}
      <section className="form_container">
        <form onSubmit={handleSubmit}>
          <div>
            <label>Display name</label>
            <input
              type="text"
              name="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></input>
            <p></p>
          </div>
          <div>
            <label>Email</label>
            <input
              type="text"
              name="email"
              value={email}
              onChange={onChangeEmail}
            ></input>
            {email.length === 0 ? (
              <p className="message error">{emptyEmailMsg}</p>
            ) : (
              <p className={`message ${isEmail ? "success" : "error"}`}>
                {emailMessage}
              </p>
            )}
            {/* 아무것도 안쓰면 Email cannot be empty. 는 어떻게 추가? */}
          </div>
          <div>
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={onChangePassword}
            ></input>
            {password.length === 0 ? (
              <p className="message error">{emptyPasswordMsg}</p>
            ) : (
              <p className={`message ${isPassword ? "success" : "error"}`}>
                {passwordMessage}
              </p>
            )}
          </div>
          <button className="sign_up_btn">Sign up</button>
        </form>
      </section>
    </div>
  );
};

export default SignUp;
