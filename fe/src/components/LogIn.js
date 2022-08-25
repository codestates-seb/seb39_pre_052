import SocialLogIn from "./SocialLogIn";
import { useState } from "react";

const LogIn = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  //Sign up 버튼 누르면 POST 요청하기
  const handleSubmit = (event) => {
    event.preventDefault();
    let user = { email: email, password: password };
    console.log(user);
  };

  //유효성 검사
  //email, password 빈칸으로 로그인하면 cannot be empty.
  //데이터에 맞지않는 이메일, pw 입력시 not a valid email address

  return (
    <div className="login_wrapper">
      {/* 소셜 로그인 */}
      <SocialLogIn />

      {/* 일반 로그인 */}
      <section className="form_container">
        <form onSubmit={handleSubmit}>
          <div>
            <label>Email</label>
            <input
              type="text"
              name="email"
              required
              onChange={(e) => setEmail(e.target.value)}
            ></input>
          </div>
          <div>
            <label>Password</label>
            <input
              type="password"
              required
              onChange={(e) => setPassword(e.target.value)}
            ></input>
          </div>
          <button className="log_in_btn">Log in</button>
        </form>
      </section>
    </div>
  );
};

export default LogIn;
