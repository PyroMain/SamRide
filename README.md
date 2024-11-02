# SamRide

![SamRide Logo](https://i.ibb.co/ctzKtg0/Logo-V2-png-256.png) <!-- Remplacer par l'URL de votre logo -->

SamRide est une application mobile intuitive et fiable qui permet aux utilisateurs de réserver un conducteur sobre (Sam) pour rentrer chez eux en toute sécurité après des soirées. Le projet vise à prévenir la conduite en état d’ébriété et à améliorer la sécurité sur les routes.

## 📲 Fonctionnalités

- **Inscription et connexion** des Sams et des utilisateurs
- **Géolocalisation en temps réel** des Sams disponibles
- **Système de réservation** à l'avance ou en temps réel
- **Paiement intégré** avec plusieurs options (Carte bancaire, PayPal)
- **Mode Groupes** pour partager le coût d'une course
- **Système d’évaluation** après chaque trajet
- **Notifications push** pour rappeler de ne pas conduire sous l'influence
- **Support 24/7** pour gérer les litiges et questions
- **Partage de la position** avec des proches pour plus de sécurité

## ⚙️ Technologies utilisées

- **Langage :** Kotlin (Android)
- **UI :** Jetpack Compose
- **Back-End :** Firebase Authentication (ou autre selon vos besoins)
- **Géolocalisation :** API Google Maps
- **Paiements :** Stripe ou PayPal SDK
- **Gestion de projet :** GitHub Issues & Projects

## 🚀 Démarrage rapide

### Prérequis
- **Android Studio** installé ([Télécharger ici](https://developer.android.com/studio))
- **Compte Firebase** pour l'authentification (optionnel)
- **Clé API Google Maps** pour la géolocalisation

# Configuration de Firebase pour l'Authentification et la Base de Données Firestore

## 1. Création du Projet Firebase

1. Accédez à la [console Firebase](https://console.firebase.google.com/).
2. Cliquez sur **Ajouter un projet** ou sélectionnez un projet existant.
3. Suivez les étapes pour créer votre projet :
    - Entrez le nom du projet : `SamRide`
    - Acceptez les conditions et activez les fonctionnalités supplémentaires si nécessaire.
4. Une fois le projet créé, cliquez sur **Continuer** pour accéder au tableau de bord du projet.

---

## 2. Ajouter Firebase à l'application

1. Dans la console Firebase, sélectionnez votre projet, puis cliquez sur **Ajouter une application** et choisissez **Android**.
2. Entrez le nom du package de votre application (disponible dans votre fichier `AndroidManifest.xml`).
3. (Optionnel) Entrez un surnom d’application et un certificat de débogage SHA-1.
4. Cliquez sur **Enregistrer l'application**.
5. Téléchargez le fichier `google-services.json` fourni par Firebase.
6. Placez ce fichier dans le dossier `app` de votre projet Android.
