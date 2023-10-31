function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

lastPage = 5;
haslastPage = (document.cookie.match(/^(?:.*;)?\s*lastPage\s*=\s*([^;]+)(?:.*)?$/) || [undefined, null])[1]
if (haslastPage == null) {
    document.cookie = "lastPage=5";
} else {
    lastPage = Number(getCookie("lastPage"));
}

function switchMenu(num) {
    const content = document.getElementById("contents")
    for (let i = 0; i < content.children.length; i++) {
        if (num !== i) {
            content.children[i].style.display = "none";
        } else {
            content.children[i].style.display = "block";
        }
    }
}

switchMenu(2);

var lastPageContent = "null";

function loadPage() {
    let url = '/load';
    fetch(url + "?lastPage=" + lastPage)
        .then(res => {
            res.text().then(function (text) {
                const json = JSON.parse(text)
                for (let i = 0; i < json.length; i++) {
                    const result = '<div class=\"post_list\">' +
                        '<div class=\"post_list_name\">' + json[i].author + '</div>' +
                        '<div class=\"post_list_content\">' + json[i].content + '</div> ' +
                        '<hr>' +
                        '<div class=\"post_list_nav\"> ' +
                        '<div class=\"post_list_menu\"> ' +
                        '<a href=""><span class=\"post_list_like_image\">👍</span> ' +
                        '<span class=\"post_list_like_count\">좋아요  </a>' + json[i].like + '</span>' +
                        '<span class=\"post_list_bookmark_image\">🏷</span> ' +
                        '<span class=\"post_list_bookmark_count\">북마크 ' + json[i].bookmark + '</span> ' +
                        '</div> ' +
                        '</div> ' +
                        '</div>';
                    if (lastPageContent !== document.getElementById("post_box").innerHTML) {
                        document.getElementById("post_box").innerHTML += result;
                    }
                }
                lastPageContent = document.getElementById("post_box").innerHTML;
            })
        })
        .catch(err => {
            console.log(err);
        })

    lastPage += 5;
}

loadPage()

window.addEventListener('scroll', function () {
    const isScrollAtBottom = window.innerHeight + window.scrollY >= document.getElementById("contents").offsetHeight;
    if (isScrollAtBottom) {
        loadPage()
    }
});

function auto_size(e) {
    e.style.height = 'auto';
    e.style.height = (e.scrollHeight) + 'px';
}

isLogin = (document.cookie.match(/^(?:.*;)?\s*accountId\s*=\s*([^;]+)(?:.*)?$/) || [undefined, null])[1];
if (isLogin == null) {
    document.getElementById("post_form").style.display = "none";
}