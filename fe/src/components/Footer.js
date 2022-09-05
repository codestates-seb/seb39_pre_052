import styled from "styled-components";
import { Link } from 'react-router-dom';

const Footer = () => {
  const linksCollection = [
    { name: "Blog" , site: "https://stackoverflow.blog/?blb=1&_ga=2.19642040.1615183858.1662280300-2146038593.1639151021"},
    { name: "Facebook", site: "https://www.facebook.com/officialstackoverflow/"},
    { name: "Twitter", site: "https://twitter.com/stackoverflow"},
    { name: "LinkedIn", site: "https://www.linkedin.com/company/stack-overflow/" },
    { name: "Instagram", site: "https://www.instagram.com/thestackoverflow/"}
  ];

  return (
    <>
      {/* <footer> */}
      <FooterWrapper>
        <div className="site-footer-logo"> <img src="https://cdn.sstatic.net/Sites/stackoverflow/Img/apple-touch-icon@2.png?v=73d79a89bded" /></div>
        <div className="site-footer-nav">
          <ul>
            <li>STACK OVERFLOW</li>
            <Link to="/">
            <li>Questions</li>
            </Link>
          </ul>
        </div>
        <div className="site-footer-social">
          <ul>
            {linksCollection.map((el, idx) => (
              <a href= {el.site}>
              <li key={idx}>{el.name}</li>
              </a>
            ))}
          </ul>
        </div>
      {/* </footer> */}
      </FooterWrapper>
    </>
  );
};

const FooterWrapper = styled.footer`
  bottom: 0;
  background-color: rgb(35, 38, 40);
  color: rgb(167, 172, 177);
  /* text-align: center; */
  padding: 20px;
  display: flex;
  flex-direction: row;
  list-style-type: none;
  text-decoration: none;
  > * {
  }

  li {
    padding: 5px;
    font-size: 11px;
    :first-of-type {
      font-size: 14px;
      font-weight: bold;
    }

  }

  > div.site-footer-logo {
    flex-basis: 10%;
    
      > img {
      width: 70px;
      height: 70px;
    }
  }
  
  > div.site-footer-nav {
    flex-basis: 50%;
  }

  > div.site-footer-social {
    flex-basis: 40%;
    > ul {
      display: flex;
      flex-direction: row;
      justify-content: flex-end;
    }
  }
`

export default Footer;