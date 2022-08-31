import { useMemo } from 'react';
import styled from 'styled-components';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import React from 'react';

const CustomReactQuill = styled(ReactQuill)`
    height: 300px;
    border-bottom: 1px solid #D1D1D1;
`

const Toolbox = ({ htmlStr, setHtmlStr }) => {
    // const htmlTextArea = document.createElement('textarea');
    // const attrHtmlTextArea = document.createAttribute('quill__html');
    // htmlTextArea.setAttributeNode(attrHtmlTextArea);


    // const htmlHandler = () => {

    //     const activeTextArea = (htmlTextArea.getAttribute('quill__html').indexOf('-active-') > -1);

    //     if (activeTextArea) {

    //         //html editor to quill
    //         this.quill.pasteHTML(htmlTextArea.value);

    //     } else {

    //         if (!this.quill.container.querySelector('.ql-custom')) {
    //             // textArea 삽입
    //             const quillCustomDiv = this.quill.addContainer('ql-custom');
    //             quillCustomDiv.appendChild(htmlTextArea);
    //         }

    //         //quill to html editor
    //         const html = this.quill.container.querySelector('.ql-editor').innerHTML;
    //         htmlTextArea.value = html;
    //     }

    //     htmlTextArea.setAttribute('quill__html', activeTextArea ? '' : '-active-');
    // }

    const modules = useMemo(() => ({
        toolbar: {
            container: [
                // [{ 'font': [] }], // font 설정
                // [{ 'header': [1, 2, 3, 4, 5, 6, false] }], // header 설정
                ['bold', 'italic', 'underline', 'strike', 'blockquote', 'code-block', 'formula'], // 굵기, 기울기, 밑줄 등 부가 tool 설정
                [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'indent': '-1' }, { 'indent': '+1' }], // 리스트, 인덴트 설정
                // ['link', 'image', 'video'], // 링크, 이미지, 비디오 업로드 설정
                [{ 'align': [] }, { 'color': [] }, { 'background': [] }], // 정렬, 글씨 색깔, 글씨 배경색 설정
                ['clean'], // toolbar 설정 초기화 설정
            ],
            // handlers: {
            //     'code-block': htmlHandler,
            // }
            handlers: {
                'code-block': () => { console.log('code block was clicked') },
                'blockquote': () => { console.log('blockquote was clicked') },
                'strike': () => { console.log('strike was clicked') }
            },
        },
    }), [])

    const formats = [
        "header",
        "font",
        "size",
        "bold",
        "italic",
        "underline",
        "list",
        "bullet",
        "align",
        "color",
        "background",
    ];

    const handleText = (content, delta, source, editor) => {
      console.log(editor.getHTML());
      setHtmlStr(editor.getHTML());
    };

    return (
        <div>
            <div className="text-editor">
                <CustomReactQuill
                    modules={modules}
                    formats={formats}
                    value={htmlStr}
                    onChange={handleText}
                    theme="snow"
                />
            </div>
      </div>
    )
};

export default Toolbox;