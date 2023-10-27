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