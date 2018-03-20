package src.modele;

import src.modele.Joueur;
import src.modele.Plateau;

public class Jeu {
    private Joueur leJoueur;
    private Plateau lePlateau;

    // constructeur sans argument
    public Jeu(){
      leJoueur=new Joueur(); lePlateau=new Plateau(40, 16, 99);
    }

    // constructeur pour un joueur et un plateau
    public Jeu(Joueur j, Plateau p){
      leJoueur = j; lePlateau = p;
    }

    // constructeur pour un joueur et trois entiers qui permettent d'initialiser un plateau.
    public Jeu(Joueur j, int h, int l, int m){
      leJoueur=new Joueur(); lePlateau=new Plateau(h, l, m);
    }

    // vérifie que la partie est finie.
    public boolean jeuFini(){
      return lePlateau.fini();
    }

    // vérifie si le joueur à gagner la partie.
    public boolean gagne(){
      return !(lePlateau.blows());
    }

    // joue une partie.
    public void jouer(){
      int[] n = new int[3];
      lePlateau.afficher();
      n = leJoueur.action();
      lePlateau.premierAgir(n);
      while(!jeuFini()){
        n = leJoueur.action();
        lePlateau.agir(n[0], n[1], n[2]);
        lePlateau.afficher();
      }
      lePlateau.afficher();
      if (gagne()){
        System.out.print("Vous avez gagné cette partie ! ");
      } else {
        System.out.print("Vous avez perdu cette partie !");
      } System.out.print("\n" /*+ joueur.score()      permettra d'afficher le score et des stats sur le joueur*/);
    }


}
