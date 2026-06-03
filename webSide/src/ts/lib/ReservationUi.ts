import { serverHost } from "../env";
import { Resto } from "../Interfaces/ListeRestos";
import { StationVlib, ListeVlib } from "../Interfaces/ListeVlib";
import { ResaInterface } from "../Interfaces/ResaInterface";

export function showResa(resto: Resto) {
  let nodeResa = document.getElementById("resa")!;

  nodeResa.querySelector("#nom-resto")!.innerHTML = resto.nom;

  (nodeResa.querySelector("#submitButton")! as HTMLButtonElement).onclick = prepareResa(resto)
  console.log(nodeResa.querySelector("#submitButton")! as HTMLButtonElement)
}

export function prepareResa(resto: Resto) {
  return async function postResa() {
    console.log("Il repassera par là")
    let nodeResa = document.getElementById("resa")!;

    let date = new Date(
      (nodeResa.querySelector("#date-input") as HTMLInputElement).value,
    );

    let heure = (nodeResa.querySelector("#hour-input") as HTMLInputElement)
      .value;

    let nom = (nodeResa.querySelector("#lastname-input") as HTMLInputElement)
      .value;

    let prenom = (nodeResa.querySelector("#name-input") as HTMLInputElement)
      .value;

    let numT = (nodeResa.querySelector("#numero-input") as HTMLInputElement)
      .value;

    let nbPersonnes = Number.parseInt(
      (nodeResa.querySelector("#nombre-input") as HTMLInputElement).value,
    );

    let values: ResaInterface = {
      date,
      heure,
      nom,
      prenom,
      numT,
      nbPersonnes,
      idResto: resto.id,
    };

    nodeResa.querySelector("#submitButton")!.classList.add("hidden")
    nodeResa.querySelector("#resa-spinner")!.classList.remove("hidden")
    try{
    let response = await fetch(serverHost+"/reserver", {
      method: "POST",
      body: JSON.stringify(values),
    });
  }catch(e ){
    alert("Réservation Impossible")
  };
    nodeResa.querySelector("#submitButton")!.classList.remove("hidden")
    nodeResa.querySelector("#resa-spinner")!.classList.add("hidden")
  };
}
