#!/bin/sh

opt=$(zenity --list --radiolist --width=500 --height=400 \
  --title="Choose the Bugs You Wish to View" \
  --column=" " --column="Regles"  \
    1             "Bloquer une adresse IP"  \
    2             "Bloquer un port"  \
    3             "Bloquer une adresse IP avec un port" \
    4		  "voir les regles" )
if [ $? = 1 ] ; then 
echo "annulr"

else 

      case $opt in 

      "Bloquer une adresse IP"              ) echo "salam" ;;

      "Bloquer un port"                     ) echo "hala" ;;

      "Bloquer un port pour une adresse IP" ) echo "zaza" ;;

      "voir les regles"                     ) echo "dada" ;;

       *                                    ) echo "error" ;;

     esac
 
fi
