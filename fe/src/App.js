import styled from "styled-components";
import GNB from "./components/GNB";
import SNB from "./components/SNB";
import Footer from "./components/Footer"

const Main = styled.div`
  display: flex;
`

function App() {
  return (
    <>
      <GNB />
      <Main>
        <SNB />
      </Main>
      <Footer />
    </>
  );
}

export default App;
