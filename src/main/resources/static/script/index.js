const tab1 = document.getElementById("tab1");
const tab2 = document.getElementById("tab2");
const tab3 = document.getElementById("tab3");
const tab4 = document.getElementById("tab4");

const tab1_window = document.getElementById("notice");
const tab2_window = document.getElementById("message");
const tab3_window = document.getElementById("feed");
const tab4_window = document.getElementById("setting");

function tabMenu_1() {
    tab1_window.style.display="block";
    tab2_window.style.display="none";
    tab3_window.style.display="none";
    tab4_window.style.display="none";
}

function tabMenu_2() {
    tab1_window.style.display="none";
    tab2_window.style.display="block";
    tab3_window.style.display="none";
    tab4_window.style.display="none";
}

function tabMenu_3() {
    tab1_window.style.display="none";
    tab2_window.style.display="none";
    tab3_window.style.display="block";
    tab4_window.style.display="none";
}

function tabMenu_4() {
    tab1_window.style.display="none";
    tab2_window.style.display="none";
    tab3_window.style.display="none";
    tab4_window.style.display="block";
}