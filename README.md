[![Build Status](https://travis-ci.org/adriens/tickets-resto-nc.svg?branch=master)](https://travis-ci.org/adriens/tickets-resto-nc) [![Join the chat at https://gitter.im/tickets-resto-nc-api/Lobby](https://badges.gitter.im/tickets-resto-nc-api/Lobby.svg)](https://gitter.im/tickets-resto-nc-api/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![](https://jitpack.io/v/adriens/tickets-resto-nc-api.svg)](https://jitpack.io/#adriens/tickets-resto-nc-api) [![Dependency Status](https://www.versioneye.com/user/projects/598428cb6725bd0076c18b3d/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/598428cb6725bd0076c18b3d)


# tickets-resto-nc-sdk


Implémentation Java de l'API du site Web des tickets restaurants de
Nouvelle-Calédonie (http://www.ticketrestaurant.nc).

Actuellement il n'existe pas d'API pour interagir avec ce sîte Web : cela
est maintenant possible.

# Projets connexes

Une appli [jhypster](https://jhipster.github.io/) : https://github.com/DSI-Ville-Noumea/ticket-restonc-web
est en cours de développement qui tente de couvrir les fonctionnalités non
couvertes aujourd'hui sur le site officiel :

- envoie des notifications dès qu'une transaction est effectuée
- site web qui ajoute un visu et des fonctionnalités qui correspondent davantage à nos attentes (statistiques, reporting, ...)

# Autres idées

Implémenter une appli android qui permet de consulter son solde entre autres.

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
