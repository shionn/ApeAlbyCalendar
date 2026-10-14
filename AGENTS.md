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
