import { what2show } from "./MapHandler";
import { StationVlib } from "../Interfaces/ListeVlib";
import { bounds } from "leaflet";
import { Resto } from "../Interfaces/ListeRestos";
import { ZoneTravaux } from "../Interfaces/ListeTravaux";
import { Loaders } from "./Loaders";

export async function showList(
  show: what2show = { resto: true, velib: true, travaux: true },
) {
  let loarder = Loaders.getInstance();
  let listDiv = document.createElement("div");

  let page = document.querySelector("#list")!;
  page.innerHTML = "";
  page.append(listDiv);

  try {
    if (show.resto) {
      (await loarder.getRestos()).forEach((value) => {
        listDiv.append(restoListTile(value));
      });
    }
  } catch (e) {
    console.log("Resto Inaccessible");
  }

  try {
    if (show.velib) {
      (await loarder.getVelib()).forEach((value) => {
        listDiv.append(velibListTile(value));
      });
    }
  } catch (e) {
    console.log("Velib inaccessible");
  }

  try {
    if (show.travaux) {
      (await loarder.getTravaux()).forEach((value) => {
        listDiv.append(travauxListTile(value));
      });
    }
  } catch (e) {
    console.log("Travaux Indisponibles");
  }

  if (!listDiv.childElementCount) {
    listDiv.innerText = "Nothing To Display";
  }
}

function restoListTile(resto: Resto) {
  let body = document.createElement("label");
  body.textContent = resto.nom;

  return genListTile("data/icon/resto.png", body);
}

function velibListTile(station: StationVlib) {
  let body = document.createElement("label");
  body.textContent = station.name;

  return genListTile("data/icon/velo.png", body);
}

function travauxListTile(travaux: ZoneTravaux) {
  let body = document.createElement("label");
  body.textContent = travaux.location.polyline;

  return genListTile("data/icon/danger.png", body);
}

function genListTile(imageSource: string, cardBody: HTMLElement) {
  let card = document.createElement("div");
  card.classList.add("card", "mb-3");

  let rowDiv = document.createElement("div");
  rowDiv.classList.add("row", "g-0");

  let imageDiv = document.createElement("div");
  imageDiv.classList.add("col-md-4");

  let image = document.createElement("img");
  image.src = imageSource;
  image.style.width = "10%"

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
