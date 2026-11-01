# AGENTS.md

Guidelines du projet — auto-chargées à chaque session.

## Normes de code
Les normes de code sont dans le fichier : `ShionnAgent/NORME_DE_CODE.md`.

## Spécification fonctionnelle
Règles métier et comportement de l'application : `docs/specification.md`.

## Maintenir le fil entre les contextes
La spécification fonctionnelle (`docs/specification.md`) est le document de référence de l'agent. Après tout changement de comportement implémenté, l'agent doit mettre à jour la spec pour qu'elle reste cohérente avec le code. Cela permet de retrouver le contexte entre les sessions.

