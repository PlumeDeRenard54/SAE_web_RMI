import * as L from "leaflet";
import { RestoInterface } from "./Interfaces/RestoInterface";

var map = L.map("map").setView([48.6936, 6.1846], 13);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
  maxZoom: 19,
  attribution:
    '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
  referrerPolicy: "origin",
}).addTo(map);

fetch("apiURL")
  .then(async (data: Response) => {
    let json: RestoInterface = await data.json();
    for (const resto of json.restos) {
      // let marker = L.marker([51.5, -0.09]).addTo(map);
      // marker.bindPopup("<NomDuResto>")
      // marker.on('click', onMapClick);
    }
  })
  .catch(() => console.log("API Not Found"));

// Initialisation boutons header
let header = document.querySelector("header")!;
console.log(header.childNodes);
header.childNodes.forEach((node) => {
  if (!("id" in node)) {
    return;
  }

  let nodeCast = node as HTMLSpanElement;
  let id = nodeCast.id.split("-")[0];
  nodeCast.onclick = () => {
    toggleHidden(id)
  };
});

function toggleHidden(id: string) {
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

toggleHidden("map")
