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
``