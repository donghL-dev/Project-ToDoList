function openNav() {
    document.getElementById("mySidenav").style.width = "15%";
    document.getElementById("main2").style.marginLeft = "15%";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main2").style.marginLeft = "0";
}

document.getElementsByClassName("tablink")[0].click();

function openCity(evt, cityName) {
    var i, x, tablinks;
    x = document.getElementsByClassName("TAB");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < x.length; i++) {
        tablinks[i].classList.remove("w3-light-grey");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.classList.add("w3-light-grey");
}