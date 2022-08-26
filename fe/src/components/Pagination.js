import styled from "styled-components";

const Nav = styled.nav`
    display: flex;
    justify-content: space-between;
    gap: 5px;
    margin: 16px;
`
const Pages = styled.div`

`

const Button = styled.button`
    padding: 8px;
    background: white;
    font-size: 1rem;
    border: none;

    &:hover {
        background: #c9c9c9;
        cursor: pointer;
    }

    &:disabled {
        cursor: revert;
    }

    &[aria-current] {
        background: #F48225;
        color: white;
        font-weight: bold;
    }
`

const Pagination = ( { total, limit, page, setPage, setLimit} ) => {

    const numPages = Math.ceil(total / limit);
    const options = [5, 10, 15];

    // Stays on the post point even page limit is changed from one number to another
    const setNewLimitStayOnPage = (opt) => {
        setLimit(opt);

        if (page === 1) {
            setPage(1)
        }
        else {
            let temp = (page-1) * limit + 1;
            let newPage = Math.ceil(temp / opt);
            setPage(newPage);
        }
    }

    return (
        <>
            <Nav>
                <Pages>
                    <Button
                        onClick={() => setPage(page - 1)}
                        disabled={page === 1} //cannot click when on first page
                    >
                        &lt;
                    </Button>
                    {Array(numPages)
                        .fill()
                        .map((_, i) => (
                            <Button
                                key={i + 1}
                                onClick={() => setPage(i + 1)}
                                aria-current={page === i + 1 ? "page" : null}
                            >
                                {i + 1}
                            </Button>
                        ))}
                    <Button
                        onClick={() => setPage(page + 1)}
                        disabled={page === numPages} //cannot click when on last page
                    >
                        &gt;
                    </Button>
                </Pages>
                <div>
                    {options.map((opt, idx) => {
                        return <Button
                            key={idx}
                            onClick={() => setNewLimitStayOnPage(opt)}
                            aria-current={opt === limit ? "option" : null}
                        >{opt}
                        </Button>
                    })}
                </div>
            </Nav>


        </>
    );
};

export default Pagination;