// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        })
            .then(() => {
                alert('삭제가 완료되었습니다.');
                location.replace('/articles');
            });
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        const content = editorInstance.getData();


        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: content // CKEditor에서 가져온 content 값
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            });
    });
}

// 생성 기능
let editorInstance;

ClassicEditor
    .create(document.querySelector('#content'), {
        language: 'ko',
        ckfinder : {

            uploadUrl: '/image/upload',
            withCredentials: true
        }
    })
    .then(editor => {
        editorInstance = editor;
    })
    .catch(error => {
        console.error('Error initializing CKEditor:', error);
    });

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    createButton.addEventListener('click', event => {
        // CKEditor에서 content 값을 가져옴
        const content = editorInstance.getData();

        fetch('/api/articles', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: content // CKEditor에서 가져온 content 값
            })
        })
            .then(() => {
                alert('등록 완료되었습니다.');
                location.replace('/articles');
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
}