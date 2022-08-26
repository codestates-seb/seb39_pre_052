import GNB from "./components/GNB";
import SNB from "./components/SNB";
import styled from "styled-components";

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
    </>
  );
}

export default App;
