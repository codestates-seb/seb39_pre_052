import GNB from "./tmp/GNB";
import SNB from "./tmp/SNB";
import styled from "styled-components";
import Footer from "./tmp/Footer"

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
