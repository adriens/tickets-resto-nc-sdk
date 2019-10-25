[![Build Status](https://travis-ci.org/adriens/tickets-resto-nc-sdk.svg?branch=master)](https://travis-ci.org/adriens/tickets-resto-nc-sdk) [![Join the chat at https://gitter.im/tickets-resto-nc-api/Lobby](https://badges.gitter.im/tickets-resto-nc-api/Lobby.svg)](https://gitter.im/tickets-resto-nc-api/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)[![](https://jitpack.io/v/adriens/tickets-resto-nc-sdk.svg)](https://jitpack.io/#adriens/tickets-resto-nc-sdk)

# tickets-resto-nc-sdk


Implémentation Java de l'API du site Web des tickets restaurants de
Nouvelle-Calédonie (https://www.neocarte.nc).

Actuellement il n'existe pas d'API pour interagir avec ce sîte Web : cela
est maintenant possible.


# Application mobile

- [Application mobile sur Android](https://play.google.com/store/apps/details?id=org.darwiin.trnc&hl=en_IN)
- [iOS](https://apps.apple.com/fr/app/mes-tickets-resto-nc/id1355470450)

# Use case

Pour recuperer les datas, tout simplement :

```java
        String login = "xxxxxxxxx";
        String password = "xxxxxx";
           
        TicketsRestaurantsServiceWrapper wrap = new TicketsRestaurantsServiceWrapper(login, password, ServiceType.BOTH);
	// now deal with with account, credit, transactions ;-p
	logger.info("################################################");
	logger.info("Solde (XPF) : " + wrap.getAccountBalance());
	logger.info("Employeur : " + wrap.getAccountEmployeer());
	logger.info("Beneficiaire : " + wrap.getAccountName());
	logger.info("################################################");

	// Listing transactions
	logger.info("################################################");
	logger.info("Transactions :\n");
	Iterator<Transaction> iter = wrap.getTransactions().iterator();

	while (iter.hasNext()) {
	    logger.info(iter.next().toString());
        }
	logger.info("################################################");
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

# Récupération des affiliés

```java
ArrayList<Affilie> affilies = TicketsRestaurantsServiceWrapper.getAffilies();
Iterator<Affilie> affIter = affilies.iterator();
Affilie aff = new Affilie();
            
logger.info("Affilies :\n");
while(affIter.hasNext()){
    aff =  affIter.next();
    logger.info(aff.toString());
}
```
# Use this lib in your project

Follow jitpack instructions, see https://jitpack.io/#adriens/tickets-resto-nc-api

# Motivations

- [x] Deal with htmlunit
- [x] Deal with Apache POI
- [x] Discover advanced features of Google Spreadsheets : dashboatd, reporting, mobile apps based on spreadsheets, ...
- [x] Getting fun as we gain the power on the data
