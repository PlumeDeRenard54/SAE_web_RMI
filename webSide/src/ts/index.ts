import * as L from "leaflet";
import { ListeVlib } from "./Interfaces/ListeVlib";
import { showResa } from "./lib/ReservationUi";
import { ListeTravaux } from "./Interfaces/ListeTravaux";
import { ListeRestos } from "./Interfaces/ListeRestos";
import { serverHost } from "./env";
import { Loaders } from "./lib/Loaders";
import { showAll } from "./lib/MapHandler";
import { $ } from "jquery";
import showdown from 'showdown';

export var map = L.map("map").setView([48.6936, 6.1846], 13);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  referrerPolicy: "origin",
}).addTo(map);

(document.querySelector("#reload-map-button")! as HTMLButtonElement).onclick =
  () => {
    let travaux = (document.querySelector("#travaux-check") as HTMLInputElement)
      .checked;
    let velib = (document.querySelector("#velib-check") as HTMLInputElement)
      .checked;
    let resto = (document.querySelector("#resto-check") as HTMLInputElement)
      .checked;

    map.eachLayer((layer) => {
      if ((layer as any)["_latlng"] != undefined) layer.remove();
    });

    showAll({
      resto,
      velib,
      travaux,
    });
  };

export function toggleHidden(id: string) {
  let mainDiv = document.querySelector("main")!;
  for (let mainPart of mainDiv.childNodes) {
    if (!("id" in mainPart)) {
      continue;
    }

    let mainPartCast = mainPart as HTMLDivElement;
    if (id == mainPartCast.id) {
      mainPartCast.classList.remove("hidden");
    } else {
      mainPartCast.classList.add("hidden");
    }
  }
}


fetch("data/Compte-Rendu.md").then(async data =>{

  var converter = new showdown.Converter(),
    text      = await data.text(),
    html      = converter.makeHtml(text);

    document.querySelector("#cr")!.innerHTML = html

})

// Initialisation boutons header
let header = document.querySelector("header")!;
header.childNodes.forEach((node) => {
  if (!("id" in node)) {
    return;
  }

  let nodeCast = node as HTMLSpanElement;
  let id = nodeCast.id.split("-")[0];
  nodeCast.onclick = () => {
    toggleHidden(id);
  };
});

showAll();
toggleHidden("mappage");
