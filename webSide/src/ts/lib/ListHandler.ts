import { what2show } from "./MapHandler";
import { StationVlib } from "../Interfaces/ListeVlib";
import { bounds } from "leaflet";
import { Resto } from "../Interfaces/ListeRestos";
import { ZoneTravaux } from "../Interfaces/ListeTravaux";
import { Loaders } from "./Loaders";
import { map, toggleHidden } from "..";
import { showResa } from "./ReservationUi";

export async function showList(
  show: what2show = { resto: true, velib: true, travaux: true },
) {
  let loarder = Loaders.getInstance();
  let listDiv = document.createElement("div");

  let page = document.querySelector("#list")!;
  page.innerHTML = "";
  page.append(listDiv);

  (async () => {
    if (show.resto) {
      (await loarder.getRestos()).forEach((value) => {
        listDiv.append(restoListTile(value));
      });
    }
  })().catch(() => {
    console.log("Resto Inaccessible");
  });

  (async () => {
    if (show.velib) {
      (await loarder.getVelib()).forEach((value) => {
        listDiv.append(velibListTile(value));
      });
    }
  })().catch(() => {
    console.log("Velib inaccessible");
  });

  (async () => {
    if (show.travaux) {
      (await loarder.getTravaux()).forEach((value) => {
        listDiv.append(travauxListTile(value));
      });
    }
  })().catch(() => {
    console.log("Travaux Indisponibles");
  });
}

function restoListTile(resto: Resto) {
  let body = document.createElement("div");

  let label = document.createElement("label");
  label.textContent = `${resto.nom} (${resto.adresse})`;

  let redirectMap = document.createElement("button");
  redirectMap.onclick = () => redirect2Map(resto.lat, resto.lon);
  redirectMap.innerText = "Map";
  redirectMap.classList.add("btn", "btn-secondary", "mx-5");

  let redirectResa = document.createElement("button");
  redirectResa.onclick = () => {
    showResa(resto);
    toggleHidden("resa");
  };
  redirectResa.innerText = "Réserver";
  redirectResa.classList.add("btn", "btn-secondary", "mx-5");

  body.append(label, redirectMap, redirectResa);

  return genListTile("data/icon/resto.png", body);
}

function velibListTile(station: StationVlib) {
  let body = document.createElement("div");

  let label = document.createElement("label");
  label.textContent = `${station.name.toLowerCase()} | Capacité: ${station.num_bikes_available} / ${station.capacity} | Docks : ${station.num_docks_available}`;

  let redirectMap = document.createElement("button");

  redirectMap.onclick = () => redirect2Map(station.lat, station.lon);
  redirectMap.innerText = "Map";
  redirectMap.classList.add("btn", "btn-secondary", "mx-5");

  body.append(label, redirectMap);

  return genListTile("data/icon/velo.png", body);
}

function travauxListTile(travaux: ZoneTravaux) {
  let body = document.createElement("div");

  let label = document.createElement("label");
  label.textContent = `${travaux.type} | ${travaux.short_description}`;

  let redirectMap = document.createElement("button");
  let coords = travaux.location.polyline.split(" ");

  redirectMap.onclick = () =>
    redirect2Map(Number.parseFloat(coords[0]), Number.parseFloat(coords[1]));
  redirectMap.innerText = "Map";
  redirectMap.classList.add("btn", "btn-secondary", "mx-5");

  body.append(label, redirectMap);

  return genListTile("data/icon/danger.png", body);
}

function genListTile(imageSource: string, cardBody: HTMLElement) {
  let card = document.createElement("div");
  card.classList.add("card", "m-3", "p-2");

  let rowDiv = document.createElement("div");
  rowDiv.classList.add("row", "g-0");

  let imageDiv = document.createElement("div");
  imageDiv.classList.add("col-md-2");

  let image = document.createElement("img");
  image.src = imageSource;
  image.style.width = "10%";

  let div2 = document.createElement("div");
  div2.classList.add("col-md-8");

  let body = document.createElement("card-body");
  body.appendChild(cardBody);

  card.appendChild(rowDiv);

  rowDiv.append(imageDiv, div2);

  imageDiv.append(image);
  div2.append(body);

  return card;
}

function redirect2Map(lat: number, long: number) {
  map.setView([lat, long], 20);
  toggleHidden("mappage");
}
