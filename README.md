[![Build Status](https://travis-ci.org/adriens/tickets-resto-nc-api.svg?branch=master)](https://travis-ci.org/adriens/tickets-resto-nc-api)

# tickets-resto-nc-api
Implémentation Java de l'API de http://www.ticketrestaurant.nc

# Use case

Pour recuperer les datas, tout simplement :

```
java
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