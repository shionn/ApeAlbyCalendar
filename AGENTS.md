# AGENTS.md

Conventions de ce projet — à respecter.

## Nommage des tests

Les méthodes de test doivent commencer par **`test`**, en CamelCase, jamais en `snake_case`.

## Assertions AssertJ

Ajouter un `.as("message explicatif")` à chaque assertion, pour qu'un échec affiche la raison.

## Visibilité des tests

La classe de test et ses méthodes sont en visibilité **`public`** (pas la visibilité par défaut de JUnit 5, peu intuitive).

## Injection des valeurs de test

Ne **pas** utiliser la réflexion pour définir des champs `private` (désuet, fragile).

## Réflexion

Ne **pas** utiliser la réflexion pour accéder à des membres `private` (ni champs, ni méthodes). On ne casse pas la blackbox d'une classe.

## SRP

Chaque classe a une seule responsabilité.

## Pas de Static non justifier 

- Une methode static est un comportmeent de classe, comme la creation d'instance ou l'acces à un singleton
- Du static est plus dur à mocker ou a injecter
- Limite l'evolutivitée.
