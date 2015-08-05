
#!/bin/sh

 opt=$(zenity --entry \
--title="blo" \
--text="Enter name of new profile:" \
--entry-text "l'adresse est de la forme xxx.xxx.xxx.xxx" )
 
echo $opt 
 case $? in 
 
0 )   echo $? ;; 
1 )   echo $? ;;

 esac
