### Projet de POO

## Présentation

Ce projet consiste à réaliser un jeu de cartes avec interface graphique minimaliste. Le jeu réalisé est une digitalisation de SHAPE UP, crée par MO HOLKAR.

Le but du jeu est, sur un cumul de round, de gagner le plus de points à partir de la disposition du plateau en fin de round. Le système de comptage de points est basé sur l'alignement successifs de caractéristiques des cartes. Pour être plus précis, chaque joueur dispose d'une carte victoire qui lui indique les caractéristique qu'il ou elle devra essayer d'aligner le plus possible sur l'aire de jeu. Enfin, ces caractéristiques sont la forme, la couleur et le remplissage des cartes. Pour avoir une description plus complète du jeu, je vous renvoie vers les [règles](ShapeUp-Rules-v1.0.pdf) de ce dernier.

## Phases

Le projet s'est déroulé en trois phases.

La première phase a été la phase de modélisation UML. Il s'agissait de modéliser les cas d'utilisations, les classes ainsi que de réaliser un diagramme de séquences pour simuler un tour de jeu.

La seconde phase a consisté à développer le jeu en ligne de commandes uniquement. Il a été demandé d'implémenter les design patterns Strategy de façon judiceuse, visitor pour le comptage de points et le singleton pour la partie.

Pour ce qui est de troisième phase, il a fallu migrer vers une architechture MVC pour rajouter une vue graphique qui serait accessible en concurrence avec la vue console déjà implémentée.

Une documentation de l'application est aussi disponible.

## Acquisitions à l'issue du projet

Ce projet m'a permis de m'initieraux bonnes pratiques en termes de POO. Utiliser des patrons de conception, structurer son code suivant une certaine architecture et respecter une nomenclature dans la déclaration de ses instances.
J'ai aussi pu concevoir une première innterface graphique , certes très minimaliste grâce à la bibliothèque Swing et l'outil windows builder

