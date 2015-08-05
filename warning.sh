#!/bin/bash
./firewalldhaw.sc stop
zenity --warning --text="le firewal est ferme appuyer sur 'valider' pour retourner a la page d'acceuil" --title="ATTENTION"
case $? in 
0) ./loula.sh ;;
*) exit 1 ;;
esac 

