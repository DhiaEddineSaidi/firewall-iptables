#!/bin/sh
(
echo "10" ; sleep 1
echo "# verification de la chaine input" ; sleep 1
echo "20" ; sleep 1
echo "# verification de la chaine forward" ; sleep 1
echo "30" ; sleep 1
echo "40" ; sleep 1
echo "50" ; sleep 1
echo "verification de la chine output" ; sleep 1
echo "60" ; sleep 1
echo "75" ; sleep 1
echo "# authentification des regles " ; sleep 1
echo "100" ; sleep 1

) |
zenity --progress --width=500 --height=400 \
  --title="Overture du firewall" \
  --text="authentification des chaines..." \
  --percentage=0

case $? in 
0) ./liste.sh ;;
1) ./warning.sh ;;
*) exit 1 ;; 
esac 
