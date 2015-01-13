function alertWelcome() {
  alert("Bienvenue " + document.getElementById("name").value + " ;-)");
}

function showWelcome() {
  document.getElementById("welcome-text").hidden = false;
  document.getElementById("welcome-text").value = "Bienvenue " + document.getElementById("name").value + " ;-)";
}

function showMore() {  
  document.getElementById("more-text").hidden = false;
}

function openWindow() {
  window.open("chrome://browser/content/places/places.xul", "bmarks", "chrome,width=600,height=300");    
}
