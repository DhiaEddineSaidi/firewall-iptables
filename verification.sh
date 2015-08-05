#!/bin/bash

zenity --warning --text="le firewal sera ferme etes vous sur de valider ? " --title="ATTENTION"
case $? in 
0)./firewalldhaw.sc stop ;;
*) exit 1 ;;
esac 

