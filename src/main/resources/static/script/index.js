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

function auto_size(e) {
    e.style.height = 'auto';
    e.style.height = (e.scrollHeight) + 'px';
}

isLogin = (document.cookie.match(/^(?:.*;)?\s*accountId\s*=\s*([^;]+)(?:.*)?$/) || [undefined, null])[1];
if (isLogin == null) {
    document.getElementById("post_form").style.display = "none";
}