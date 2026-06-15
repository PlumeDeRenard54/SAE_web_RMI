import * as L from "leaflet";
import { showMap } from "./lib/MapHandler";
import showdown from "showdown";
import { showList } from "./lib/ListHandler";

export var map = L.map("map").setView([48.6936, 6.1846], 13);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  referrerPolicy: "origin",
}).addTo(map);

let travaux = (document.querySelector("#travaux-check") as HTMLInputElement);
let velib = (document.querySelector("#velib-check") as HTMLInputElement)
let resto = (document.querySelector("#resto-check") as HTMLInputElement);

let mapReload =
  () => {

    map.eachLayer((layer) => {
      if ((layer as any)["_latlng"] != undefined) layer.remove();
    });

    showMap({
      resto : resto.checked,
      velib : velib.checked ,
      travaux : travaux.checked,
    });

    showList({
      resto : resto.checked,
      velib : velib.checked,
      travaux : travaux.checked,
    });
  };

travaux.onclick = mapReload
velib.onclick = mapReload
resto.onclick = mapReload

/**
 * Choix de la page affichée
 *
 * Page reliée au bouton grâce au nommage : xxx-button -> xxx
 */
export function toggleHidden(id: string) {
  let mainDiv = document.querySelector("main")!;
  for (let mainPart of mainDiv.childNodes) {
    if (!("id" in mainPart)) {
      continue;
    }

    let mainPartCast = mainPart as HTMLDivElement;
    if (id == mainPartCast.id) {
      mainPartCast.classList.remove("hidden");
      document
        .querySelector("#" + id + "-button")
        ?.childNodes.forEach((node) => {
          if ("id" in node) {
            (node as HTMLSpanElement).classList.add("active");
          }
        });
    } else {
      mainPartCast.classList.add("hidden");
      document
        .querySelector("#" + mainPartCast.id + "-button")
        ?.childNodes.forEach((node) => {
          if ("id" in node) {
            (node as HTMLSpanElement).classList.remove("active");
          }
        });
    }
  }
}

//Init du CR
fetch("data/Compte-Rendu.md").then(async (data) => {
  var converter = new showdown.Converter(),
    text = await data.text(),
    html = converter.makeHtml(text);

  document.querySelector("#cr")!.innerHTML = html;
});

// Initialisation boutons header
let header = document.querySelector("header ul")!;
header.childNodes.forEach((node) => {
  if (!("id" in node) || node.id == "drd") {
    return;
  }

  let nodeCast = node as HTMLSpanElement;
  let id = nodeCast.id.split("-")[0];
  nodeCast.onclick = () => {
    toggleHidden(id);
  };
});

(async () => showMap())().then(() => showList());

toggleHidden("mappage");
