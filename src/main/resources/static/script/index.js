var lastPage = 0;

function switchMenu(num) {
    const tapMenu = document.getElementById("contents")
    for (let i = 0; i < tapMenu.children.length; i++) {
        if (num !== i) {
            tapMenu.children[i].style.display = "none";
        } else {
            tapMenu.children[i].style.display = "block";
        }
    }
}

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
                        '<div class=\"post_list_nav\"> ' +
                        '<div class=\"post_list_like\"> ' +
                        '<span class=\"post_list_like_image\">' + json[i].like + '</span> ' +
                        '<span class=\"post_list_like_count\"></span> ' +
                        '</div> ' +
                        '<div class=\"post_list_bookmark\"> ' +
                        '<span class=\"post_list_bookmark_image\"></span> ' +
                        '<span class=\"post_list_bookmark_count\">' + json[i].bookmark + '</span> ' +
                        '</div> ' +
                        '</div> ' +
                        '</div>';
                    document.getElementById("post_box").innerHTML += result;
                }
            })
        })
        .catch(err => {
            console.log(err);
        })

    lastPage += 5;
}

loadPage();