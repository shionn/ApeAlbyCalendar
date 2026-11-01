# Spécification fonctionnelle — ApeAlbyCalendar

Règles métier et comportement fonctionnel de l'application. Ce document décrit **ce que le système fait**, indépendamment de l'implémentation technique.

> Distinct des autres docs du projet :
> - `AGENTS.md` → workflow et conventions de développement.
> - `NORME_DE_CODE.md` → règles de code.

## Vocabulaire

| Terme | Signification |
|---|---|
| Événement | Une activité à laquelle des personnes peuvent s'inscrire (ex. atelier). |
| Période | Une tranche horaire d'une heure comprise dans la plage de l'événement. |
| Créneau | Une période d'une heure ; unité de base d'inscription. |
| Inscription | L'enregistrement d'une personne pour un ou plusieurs créneaux. |
| `maxRegister` | Nombre maximal d'inscriptions répertoriées sur une seule période. |

## 1. Événement et périodes

- Un événement est défini par son `id`, son `name`, sa `day`, et une plage horaire `[start, end)` exprimée en heures.
- Les périodes sont générées par heure entre `start` (inclusif) et `end` (exclusif) : `end - start` périodes.
- Chaque période est identifiée par son heure `period`.
- **Label affiché** d'une période : `%dh-%dh`, ex. `period = 18` → `18h-19h`.

## 2. Inscription

### 2.1 Formulaire
Champs collectés :
- Prénom (obligatoire)
- Nom (obligatoire)
- Email (obligatoire, format email)
- Créneau d'arrivée (sélecteur d'heure)
- Créneau de départ (sélecteur d'heure)
- Commentaire (optionnel)

### 2.2 Découpage en créneaux
Une inscription couvre un créneau `[arrivée, départ]`. Elle est découpée en **créneaux horaires individuels de 1 h** :
- `[18, 20[` → deux enregistrements : `18-19` et `19-20`.
- Chaque enregistrement a `departureHour = arrivalHour + 1`.

### 2.3 Routes
- `GET /event/{id}/register` → affiche le formulaire. Un paramètre optionnel `arrivalHour` peut pré-sélectionner une heure.
- `POST /event/{id}/register` → vérifie d'abord l'unicité de l'inscription (voir § 2.5) ; si la personne est déjà inscrite à cet événement, redirige vers le formulaire avec un message d'erreur, sinon persiste les créneaux horaires.
- Après inscription réussie, redirection vers `/` avec un message de succès.

### 2.5 Unicité de l'inscription

Une personne ne peut pas s'inscrire deux fois au même événement. La vérification porte sur l'identité **normalisée** (email + prénom + nom en minuscule) et l'événement, **sans tenir compte de l'heure ni de la casse** : une personne déjà inscrite à un créneau ne peut pas s'inscrire à un autre créneau du même événement. Une tentative bloquée redirige vers le formulaire (`/event/{id}/register`) avec un message d'erreur affiché (`error`).

## 3. Normalisation des noms

- **Stockage** : avant l'écriture en base, le prénom, le nom et l'email sont mis en **minuscule** (`EventRegistrationNormalizer.normalize`).
- **Affichage** : le libellé court affiche le prénom au complet (première lettre en majuscule), puis la première lettre du nom en majuscule suivie d'un point. Ex. `michel` / `vaillant` → `Michel V.`.

## 4. Affichage du tableau des inscrits

- Colonnes : « Quand » + une colonne par inscription possible.
- `maxRegister` = nombre maximal d'inscriptions sur une seule période ; il fixe la largeur du tableau.
- Les périodes ayant moins d'inscrits sont **remplies par des cellules vides** (un simple espace), afin que toutes les lignes aient la même largeur.
- Chaque ligne dispose d'un lien « S'inscrire » pré-rempli avec l'heure de la période (`arrivalHour`).

## 5. Désinscription

- `GET /event/{id}/unregister` → affiche le formulaire de désinscription (prénom, nom, email, créneau).
- `POST /event/{id}/unregister` → normalise prénom, nom et email en minuscule, puis vérifie qu'un enregistrement correspondant existe (prénom + nom + email + heure d'arrivée) ; si oui, le supprime, redirige vers `/` avec un message de succès, sinon redirige vers le formulaire avec un message d'erreur.
- Formulaire semblable à celui d'inscription, sans champ commentaire.
