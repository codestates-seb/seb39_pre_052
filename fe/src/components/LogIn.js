import SocialLogIn from "./SocialLogIn";
import { useState } from "react";

const LogIn = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emptyEmailMsg, setEmptyEmailMsg] = useState("");
  const [emptyPasswordMsg, setEmptyPasswordMsg] = useState("");
  const [incorrectMessage, setIncorrectMessage] = useState("");

  //Sign up 버튼 누르면 POST 요청하기
  const handleSubmit = (event) => {
    event.preventDefault();
    let user = { email: email, password: password };
    // console.log(user);

    //email, password 빈칸으로 로그인하면 cannot be empty.
    if (email.length === 0) {
      setEmptyEmailMsg("Email cannot be empty.");
    }
    if (password.length === 0) {
      setEmptyPasswordMsg("Password cannot be empty.");
    }

    fetch("/login", {
      method: "POST",
      body: JSON.stringify(user),
    })
      // .then((res) => console.log(res))
      // .then((res) => res.json()) //SyntaxError: Unexpected end of JSON input
      // .then((res) => console.log(res.status)) //200
      .then((res) => {
        if (res.status === 200) {
          console.log(res.headers.get("authorization")); //Bearer eyJ0eXAi...
          const token = res.headers.get("authorization");
          localStorage.setItem("access-token", token); //로컬 스토리지에 저장
        }
        // else if (!res.ok)
        else if (res.status === 401) {
          console.log(res);
          setIncorrectMessage("The email or password is incorrect.");
        }
      });
    // .catch(() => console.log("error"));

    // fetch("/login", {
    //   method: "GET",
    //   headers: {
    //     "Content-Type": "application/json",
    //     Authorization: localStorage.getItem("access-token"),
    //   },
    // }).then((res) => console.log(res));
  };
  //데이터에 맞지않는 이메일, pw 입력시 The email or password is incorrect.
  //not a valid email address

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
              onChange={(e) => setEmail(e.target.value)}
            ></input>
            {email.length === 0 ? (
              <p className="message error">{emptyEmailMsg}</p>
            ) : (
              <p className="message error">{incorrectMessage}</p>
            )}
          </div>
          <div>
            <label>Password</label>
            <input
              type="password"
              onChange={(e) => setPassword(e.target.value)}
            ></input>
            {<p className="message error">{emptyPasswordMsg}</p>}
          </div>
          <button className="log_in_btn">Log in</button>
        </form>
      </section>
    </div>
  );
};

export default LogIn;
