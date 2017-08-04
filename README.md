[![Build Status](https://travis-ci.org/adriens/tickets-resto-nc.svg?branch=master)](https://travis-ci.org/adriens/tickets-resto-nc) [![Join the chat at https://gitter.im/tickets-resto-nc-api/Lobby](https://badges.gitter.im/tickets-resto-nc-api/Lobby.svg)](https://gitter.im/tickets-resto-nc-api/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![](https://jitpack.io/v/adriens/tickets-resto-nc-api.svg)](https://jitpack.io/#adriens/tickets-resto-nc-api) [![Dependency Status](https://www.versioneye.com/user/projects/598428cb6725bd0076c18b3d/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/598428cb6725bd0076c18b3d)


# tickets-resto-nc-api


Implémentation Java de l'API de http://www.ticketrestaurant.nc

# Use case

Pour recuperer les datas, tout simplement :

```java
            String login = "xxxxxxxxx";
            String password = "xxxxxx";
            
            TicketsRestaurantsServiceWrapper wrap = new TicketsRestaurantsServiceWrapper(login, password);
            // now deal with with account, credit, transactions ;-p
            System.out.println("################################################");
            System.out.println("Solde (XPF) : " + wrap.getAccountBalance());
            System.out.println("Employeur : " + wrap.getAccountEmployeer());
            System.out.println("Beneficiaire : " + wrap.getAccountName());
            // Listing transactions
            System.out.println("Transactions :\n");
            Iterator<Transaction> iter = wrap.getTransactions().iterator();
            
            while (iter.hasNext()) {
                System.out.println(iter.next().toString());
                
            }
            System.out.println("################################################");
```

Output :

```
################################################
Solde (XPF) : 21310
Employeur : MAIRIE DE NOUMEA
Beneficiaire : MR ADRIEN SALES
Transactions :

Date : Mon Apr 24 00:00:00 SBT 2017 (Recharge)
Date : Fri Apr 14 00:00:00 SBT 2017 (Transaction CHAMPION N'GEA)
Date : Tue Apr 04 00:00:00 SBT 2017 (Transaction CHAMPION N'GEA)
Date : Fri Mar 31 00:00:00 SBT 2017 (Recharge)
Date : Wed Mar 29 00:00:00 SBT 2017 (Recharge)
################################################


```
# Use this lib in your project

Follow jitpack instructions, see https://jitpack.io/#adriens/tickets-resto-nc-api

# Motivations

- [x] Deal with htmlunit
- [x] Deal with Apache POI
- [x] Discover advanced features of Google Spreadsheets : dashboatd, reporting, mobile apps based on spreadsheets, ...
