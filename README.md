# TP-Synthèse-Multimedia

Cette Application a été réalisée par : 

    CHAMPVILLARD Sébastien
    BONDU Justine
    ROCHE Gabriel

# Table des Matières : 
## 1. Installation de l'application
## 2. Lancement de l'application
## 3. Utiliser l'application


# Prérequis

Il vous faut une installation de java récente pour que l'application puisse démarrer.
Système d'exploitation windows ou linux. Préférence sur Linux.



## 1. Installation de l'application

Sur la page github du répertoire : 
  Cliquer sur le bouton <b> "<> Code" </b> .
  Sélectionner <b>"Download zip"</b> .
  Avec un logiciel de gestion d'archives (7zip,winRar, ...), extraire l'archive à l'emplacement voulu.


## 1. Lancement de l'application

Pour lancer rapidement l'application télécharger le .jar et faite la commande java -jar SwingPainter.jar dans le dossier où est rangé le .jar



## 3. Utiliser l'application

L'application a nombre de fonctionnalités, qui sont toutes accessibles depuis les menus et la Barre d'outils en haut de la fenêtre.
Cette application respecte beaucoup de raccourcis usuels comme ctrl+O pour ouvrir, ctrl+Z pour revenir en arrière etc ...

  Le premier bouton et le plus intéressant est "ouvrir une image". Ce bouton vous ouvre une fenêtre de dialogue où vous pouvez aller chercher votre image.
  L'image est maintenant sur l'application ! 
  On peut donc commencer les traitements dans les menus : 
         Dans l'onglet <b>image</b> se trouvent les boutons pour tourner et retourner l'image.
         Dans l'onglet <b>ajustements </b>, on peut augmenter et diminuer le contraste et la luminosité, sans oublier une option pour passer l'image en noir et blanc.

Dans la barre d'outils se trouvent bien d'autres fonctionnalités pour vous aider à personnaliser vos images ! 
Dans l'ordre : 
   Le fameux bouton pour ouvrir une image, qu'on retrouve aussi dans les menus.
   Le bouton sauvegarder, qui écrase l'image ouverte.
   
   Les boutons retour et avancer, aussi connus sous le nom de ctrl+z et ctrl+y.
   Le bouton curseur, qui permet de revenir au curseur basique et sélectionner.
   
   Le bouton sélection de couleur, qui permet ... eh bien .... de sélectionner une couleur grâce à une fenêtre de dialogue.
   Le bouton pipette, qui vous permet de passer en mode pipette et de sélectionner une couleur en cliquant sur votre image.
   Le bouton remplir, qui permet de remplir une zone de couleur par la couleur sélectionnée. Un menu s'ouvre pour régler la "Tolérance".
   Le bouton fondTransparent, qui détermine automatiquement le fond de l'image et le rends transparent. 
   
   Les boutons liés au textes : 
      -Un bouton pour passer en mode écriture, qui écrit le mot écrit dans la zone de texte à droite, avec la taille choisie dans la liste déroulante.
      
   Ensuite on a le bouton pour importer une nouvelle image. Cela ouvre une autre fenêtre qui permet de sélectionner des bouts de l'image et de les rapatrier sur la fenêtre principale.
     -Il est recommandé de fermer cette fenêtre lorsque vous avez fini, surtout si vous importez beaucoup d'image.
     
   Ensuite on a la sélection Rectangle ou Ovale, qui permettent de sélectionner et copier des bouts de l'image en cliquant-glissant. Ces bouts d'images, appelés Figures, peuvent être déplacés où on veut en cliquant-glissant.
     -Vous pouvez sélectionner une figure en cliquant dessus, et utiliser les 4 derniers boutons qui changent leur ordre dans les calques. Les calques ne sont disponibles que pour les Figures, et non l'image principale.
     -Vous pouvez aussi détruire une figure en la sélectionnant et en appuyant sur la touche suppr.

   Le dernier bouton avant les calques est le bouton "coller", il permet de "coller" le contenu des Figures sur l'image. 
     -C'est très important car le contenu des Figures n'est pas modifiable. 
     -Une fois collé, le contenu d'une Figure ne fait qu'un avec l'image et est donc modifié avec toutes l'image, tout en gardant la Figure originelle intacte.
     -Si vous déplacez vos figures hors de l'image, elles ne sont pas pris en compte, ça vous évite à devoir les supprimer si vous ne souhaitez pas les utiliser tout de suite mais voulez les garder pour plus tard.

  Si vous quittez l'application sans avoir enregistré, l'application vous le fera savoir.
  Une fois fermée, l'application ne garde ni les modifications non enregistrées, ni les Figures en mémoire.
  
