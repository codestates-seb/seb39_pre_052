import "./App.css";
import SignUp from "./components/SignUp";
import LogIn from "./components/LogIn";
import Footer from "./components/Footer";
import { useState } from "react";

function App() {
  const [isLoggedin, setIsLoggedin] = useState(false);
  return (
    <div className="App">
      <SignUp></SignUp>
      <LogIn></LogIn>
      {/* <Footer></Footer> */}
    </div>
  );
}

export default App;
