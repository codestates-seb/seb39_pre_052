import GNB from "./Components/GNB";
import SNB from "./Components/SNB";
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
