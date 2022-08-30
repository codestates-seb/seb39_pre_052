const Footer = () => {
  const linksCollection = [
    { name: "Blog" },
    { name: "Facebook" },
    { name: "Twitter" },
    { name: "LinkedIn" },
    { name: "Instagram" },
  ];

  return (
    <>
      <footer>
        <div className="site-footer-logo"></div>
        <div className="site-footer-nav">
          <a>STACK OVERFLOW</a>
          <ul>
            <li>Questions</li>
          </ul>
        </div>
        <div className="site-footer-social">
          <ul>
            {linksCollection.map((el, idx) => (
              <li key={idx}>{el.name}</li>
            ))}
          </ul>
        </div>
      </footer>
    </>
  );
};

export default Footer;
