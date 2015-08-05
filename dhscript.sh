#!/bin/bash
DIALOG=$(zenity --question --title="BIEN VENUE A FIREWALLDHAW" --text="VOULEZ VOUS OUVRIR LE FIREWALLDHAW ?)

case $? in
        0)#!/bin/sh
       (
          echo "10" ; sleep 1
           echo "# verification de la chaine input " ; sleep 1
             echo "20" ; sleep 1
           echo "# verification de la chaine output" ; sleep 1
               echo "50" ; sleep 1
            echo "verification de la chaine forward " ; sleep 1
          echo "75" ; sleep 1
             echo "# authentification des regles  " ; sleep 1
               echo "100" ; sleep 3
             ./firewalldhaw.sc start
             echo "# verification termine.. le fire wall est ouvert " ; 
               
                 ) |
                     opt=` zenity --progress \
                     --title="ouverture du firewalldhaw" \
                      --text="verification des chaines ..." \
                       --percentage=0 `

                    case $opt in 
                     0) zenity --error  --text="Overture du firewall annulé." ;;
                     1) echo "jaw" ;;
                     *) exit 1 ;;
                esac ;;
                       
       


                  1)    #!/bin/sh
                   (
                      echo "10" ; sleep 1
                        echo "# abandon de la chaine input" ; sleep 1
                       echo "20" ; sleep 1
                        echo "# abandon de la chaine output" ; sleep 1
                           echo "50" ; sleep 1
                           echo "abandon de la chaine forward" ; sleep 1
                           echo "75" ; sleep 1
                            echo "# abandon des regles " ; sleep 1
                             echo "100" ; sleep 3
                             ./firewalldhw.sc stop
                            echo "# l abandon est termine.. le firewall est fermé  " ;
                              ) |
                                    zenity --progress \
                                    --title="fermeture de firewall" \
                                      --text="abandon des chaines..." \
                                        --percentage=0

                                       opt1=` zenity --progress \
                     --title="fermeture du firewalldhaw" \
                      --text="abandon des chaines ..." \
                       --percentage=0 `

                    case $opt1 in 
                     0) zenity --error  --text="Fermeture de firewall annulé." ;;
                     1) echo "jaw 1" ;;
                     *) exit 1 ;;
                    esac ;;
                                 
*)exit 1 ;;

 esac
 
