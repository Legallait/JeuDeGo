# Jeu de Go

TB : 18/20 (MZ)

### Programme permettant de jouer une partie de Go.

### Groupe : 206
- Nicolas Estermann
- Clément Galibardy-Séfrin
- Clément Salliot
- Kévin Jiang

### Fonctionnalités implémentées
- boardsize size : modifie la taille du plateau de jeu à size
- showboard : affiche le plateau
- clear_board : efface toutes les pierres jouées sur le plateau
- quit : arrête le programme
- play color coord : permet à un joueur console de jouer un coup
- pass color : permet à un joueur console de passer son tour
- liberties x y : permet d’obtenir le nombre de libertés d’une pierre à un emplacement donné
- player color type : change le type de joueur pour une couleur donnée

Le programme compte le nombre de pierres capturées en temps réel.

Si le plateau est rempli ou si les deux joueurs passent leur tour consécutivement, le programme s’arrête.

Il n’est pas possible de se suicider, sauf si la pierre posée capture d’autres pierres.

Le programme prend en compte deux types de joueurs :
- console : joue en rentrant des commandes dans la console
- random : bot qui joue de manière aléatoire
