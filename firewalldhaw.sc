#!/bin/bash
#############################################################################################################
###############################################FONCTION FLUSH################################################
#############################################################################################################

function flush_firewall
{
			iptables -F
			iptables -t nat -F
			iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE
}
##############################################################################################################
########################################FONCTION START FIREWALL###############################################
##############################################################################################################
function start_firewall
{
###############################################REGLE DE BASE##################################################
			iptables -P FORWARD DROP
			iptables -P INPUT DROP
			iptables -P OUTPUT DROP
			flush_firewall
#############################################################################################################
##############################################INPUT - OUTPUT#################################################
#############################################################################################################
			iptables -A INPUT -i lo -s 127.0.0.0/8 -j ACCEPT
			iptables -A OUTPUT -o lo -d 127.0.0.0/8 -j ACCEPT
##############################################HTTP-HTTPS####################################################
                       
		#HTTP INTERNE			
			iptables -A INPUT -p tcp --sport 1024:65535 --dport 80 -m state --state NEW,ESTABLISHED -j ACCEPT      
			iptables -A OUTPUT -p tcp --sport 80 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
		#HTTP ALTERNATIF INTERNE
			iptables -A INPUT -p tcp --sport 1024:65535 --dport 8080 -m state --state NEW,ESTABLISHED -j ACCEPT    
			iptables -A OUTPUT -p tcp --sport 8080 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT             
		#HTTPS INTERNE
			iptables -A INPUT -p tcp --sport 1024:65535 --dport 443 -m state --state NEW,ESTABLISHED -j ACCEPT     
			iptables -A OUTPUT -p tcp --sport 443 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	              
		#HTTP EXTERNE
                        iptables -A OUTPUT -p tcp --sport 1024:65535 --dport 80 -m state --state NEW,ESTABLISHED -j ACCEPT
                        iptables -A INPUT -p tcp --sport 80 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
		#HTTP ALTERNATIF EXTERNE
                        iptables -A OUTPUT -p tcp --sport 1024:65535 --dport 8080 -m state --state NEW,ESTABLISHED -j ACCEPT
                        iptables -A INPUT -p tcp --sport 8080 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
		#HTTPS EXTERNE
                        iptables -A OUTPUT -p tcp --sport 1024:65535 --dport 443 -m state --state NEW,ESTABLISHED -j ACCEPT
                        iptables -A INPUT -p tcp --sport 443 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT		       	      		       
###############################################SSH###########################################################
		#SERVEUR SSH
                        iptables -A INPUT -p tcp --sport 1024:65535 --dport 22 -m state --state NEW,ESTABLISHED -j ACCEPT
                        iptables -A OUTPUT -p tcp --sport 22 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
		#VERS SERVEUR SSH  
			iptables -A OUTPUT -p tcp --sport 1024:65535 --dport 22 -m state --state NEW,ESTABLISHED -j ACCEPT                      
			iptables -A INPUT -p tcp --sport 22 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
##############################################ICMP###########################################################
			iptables -A INPUT -p icmp -j ACCEPT
			iptables -A OUTPUT -p icmp -j ACCEPT
##############################################DNS############################################################                       
			iptables -A OUTPUT -p udp --sport 1024:65535 --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
			iptables -A INPUT -p udp --sport 53 --dport 1024:65535  -m state --state ESTABLISHED -j ACCEPT
			iptables -A INPUT -p udp --sport 1024:65535 --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
			iptables -A OUTPUT -p udp --sport 53 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
#################################################FTP#######################################################
		#FTP INTERNE
			iptables -A INPUT -p tcp --sport 1024:65535 --dport 21 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A OUTPUT -p tcp --sport 21 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
		#FTP DATA INTERNE ACTIF
			iptables -A OUTPUT -p tcp --sport 20 --dport 1024:65535 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A INPUT -p tcp --sport 1024:65535 --dport 20 -m state --state ESTABLISHED -j ACCEPT		
		#FTP DATA INTERNE PASSIF
			iptables -A INPUT -p tcp -s 10.10.1.0/24 --sport 1024:65535 --dport 1024:65535 -m state --state NEW,ESTABLISHED -j ACCEPT
			iptables -A OUTPUT -p tcp -d 10.10.1.0/24 --sport 1024:65535 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
		#FTP EXTERNE	
			iptables -A OUTPUT -p tcp --sport 1024:65535 --dport 21 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A INPUT -p tcp --sport 21 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
		#FTP DATA EXTERNE ACTIF
			iptables -A INPUT -p tcp --sport 20 --dport 1024:65535 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A OUTPUT -p tcp --sport 1024:65535 --dport 20 -m state --state ESTABLISHED -j ACCEPT		
		#FTP DATA EXTERNE PASSIF
			iptables -A INPUT -p tcp -s 10.10.1.0/24 --sport 1024:65535 --dport 1024:65535 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A OUTPUT -p tcp -d 10.10.1.0/24 --sport 1024:65535 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
###########################################################################################################
#################################################FORWARD####################################################
############################################################################################################
##############################################HTTP-HTTPS####################################################
		#HTTP
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 80 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 80 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT		
		#HTTP ALTERNATIF
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 8080 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 8080 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
		#HTTPS
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 443 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 443 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
##############################################MAILING########################################################
		#SMTP
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 25 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 25 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
		#SMTP INFOMANIAK
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 587 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 587 --dport 1024:65535 -m state --state NEW,ESTABLISHED -j ACCEPT	
		#IMAP
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 143 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 143 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
		#POP3
			iptables -A FORWARD -p tcp --sport 1024:65535 --dport 110 -m state --state NEW,ESTABLISHED -j ACCEPT	
			iptables -A FORWARD -p tcp --sport 110 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
##############################################ICMP###########################################################
			iptables -A FORWARD -p icmp -j ACCEPT
###############################################SSH###########################################################
                        iptables -A FORWARD -p tcp --sport 1024:65535 --dport 22 -m state --state NEW,ESTABLISHED -j ACCEPT
                        iptables -A FORWARD -p tcp --sport 22 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
##############################################DNS############################################################
			iptables -A FORWARD -p udp --sport 1024:65535 --dport 53 -m state --state NEW,ESTABLISHED -j ACCEPT
			iptables -A FORWARD -p udp --sport 53 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
##############################################FTP############################################################
		#CONNECTION FTP
			iptables -A FORWARD -p tcp -s 10.10.1.0/24 --sport 1024:65535 --dport 21 -m state --state NEW,ESTABLISHED -j ACCEPT		
			iptables -A FORWARD -p tcp -d 10.10.1.0/24 --sport 21 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT	
		#FTP DATA MODE PASSIF		
			iptables -A FORWARD -p tcp -s 10.10.1.0/24 --sport 1024:65535 --dport 1024:65353 -m state --state NEW,ESTABLISHED -j ACCEPT 
			iptables -A FORWARD -p tcp -d 10.10.1.0/24 --sport 1024:65535 --dport 1024:65353 -m state --state ESTABLISHED -j ACCEPT		#####################################################NTP#####################################################
			iptables -A FORWARD -p udp --sport 1024:65535 --dport 123 -m state --state NEW,ESTABLISHED -j ACCEPT
			iptables -A FORWARD -p udp --sport 123 --dport 1024:65535 -m state --state ESTABLISHED -j ACCEPT
#############################################################################################################
}
#############################################################################################################
########################################FONCTION STOP FIREWALL###############################################
#############################################################################################################
function stop_firewall
{
#############################################REGLE DE BASE###################################################
			iptables -P FORWARD ACCEPT
			iptables -P INPUT ACCEPT
			iptables -P OUTPUT ACCEPT
			flush_firewall
############################################################################################################
}
############################################################################################################
##########################################SWITCH CASE#######################################################
############################################################################################################
case $1 in
	start)
			start_firewall
			echo "Demarrage du FireWall"
;;
	stop)
			stop_firewall
			echo "Arret du FireWall"
;;
	restart)
			stop_firewall
			start_firewall
			echo "Redemarrage du FireWall"
;;
	status)		echo "Table netfilter"
			iptables -L
			echo "Table Nat"
			iptables -t nat -L
;;
	*)
			echo "Utilisation : $0{start:stop:restart:status}"
;;
esac
###########################################################################################################
############################################FIN CONDITION##################################################
###########################################################################################################

