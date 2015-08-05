#!/bin/bash
zenity --question --title="FIREWALLDHAW" --text="voulez vous ouvrir le firewall ?" --width=500 --height=400
case $? in 
0) ./thenya_0.sh ;;
1) ./verification.sh ;;
*) exit 1;;
esac


