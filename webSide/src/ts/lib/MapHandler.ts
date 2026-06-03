import L from "leaflet";
import { Loaders } from "./Loaders";
import { showResa } from "./ReservationUi";
import { map, toggleHidden } from "..";

let loader = Loaders.getInstance();

export async function showVelib() {
  //Récupération données Vélib
  for (const StationVlib of await loader.getVelib()) {
    let marker = L.marker([StationVlib.lat, StationVlib.lon]).addTo(map);
    marker.setIcon(
      L.icon({ iconUrl: "data/icon/velo.png", iconSize: L.point(30, 30) }),
    );
    let popup = marker.bindPopup(
      `${StationVlib.name.toLowerCase()} | Capacité: ${StationVlib.num_bikes_available} / ${StationVlib.capacity} | Docks : ${StationVlib.num_docks_available}`,
    );
  }
}

export async function showTravaux() {
  //Récupération données Travaux

  for (const travail of await loader.getTravaux()) {
    let coords = travail.location.polyline.split(" ");
    let marker = L.marker([
      Number.parseFloat(coords[0]),
      Number.parseFloat(coords[1]),
    ]).addTo(map);
    marker.setIcon(
      L.icon({ iconUrl: "data/icon/danger.png", iconSize: L.point(30, 30) }),
    );
    let popup = marker.bindPopup(
      `/!\\  ${travail.type} | ${travail.short_description}`,
    );
  }
}

export async function showRestos() {
  //Récuperation données resto

  for (const resto of await loader.getRestos()) {
    let marker = L.marker([resto.lat, resto.lon]).addTo(map);
    let popup = marker.bindPopup(resto.nom);
    popup.on("click", () => {
      showResa(resto);
      toggleHidden("resa");
    });
  }
}

export function showAll(
  show: what2show = { resto: true, velib: true, travaux: true },
) {
  if (show.resto) {
    showRestos();
  }

  if (show.velib) {
    showVelib();
  }

  if (show.travaux) {
    showTravaux();
  }
}

export type what2show = {
  resto: boolean;
  velib: boolean;
  travaux: boolean;
};
