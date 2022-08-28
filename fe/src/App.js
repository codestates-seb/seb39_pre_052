import "./App.css";
import SignUp from "./components/SignUp";
import LogIn from "./components/LogIn";
import { useState } from "react";

function App() {
  const [isLoggedin, setIsLoggedin] = useState(false);
  return (
    <div className="App">
      <SignUp></SignUp>
      <LogIn></LogIn>
    </div>
  );
}

export default App;
