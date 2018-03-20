package src.modele;

import java.util.Scanner;
public class Lanceur {

  static Scanner sc = new Scanner(System.in);

  // Mon démineur aura des problèmes d'affichage si le nombre de colonne ou de ligne dépasse 100 (cela me semble raisonnable).
  // J'ai implémenté plusieurs fonctions existantes dans un vrai démineur tel que :
  // - le premier coup n'est jamais une bombe, quel que soit son emplacement (nécessite d'initialiser le plateau après le premier coup du joueur)
  // - en demandant de révéler une case déjà révélé entouré du bon nombre de drapeau (2 drapeau si la case vaut 2), on révèle toutes les case autours de celle-ci.

  public static void lancer(){
    System.out.print("Bonjour , quel est vote nom ? ");
    String s = sc.next();
    Joueur j = new Joueur(s);
    System.out.println("Prêt pour une petite partie de démineur " + s + " ? ");
    boolean b = Joueur.ouiNon();
    while (b){
      int h = Joueur.nombreChoisi("Combien de lignes veux-tu sur ton Plateau ? ");
      int l = Joueur.nombreChoisi("Combien de colonnes veux-tu ? ");
      int m = Joueur.nombreChoisi("Et combien de mines ? ");
      Jeu jeu = new Jeu(j,h,l,m);
      jeu.jouer();
      System.out.println("Prêt pour la suivante ?");
      b = Joueur.ouiNon();
    }
    System.out.println("Au revoir, j'espère te revoir bientôt");
  }

  public static void main (String[] args){
    // Plateau p = new Plateau(10, 10, 10);
    // Joueur o = new Joueur ("Olivier");
    /* p.debugMines();
    p.debugAdja();
    p.afficher();
    p.revelerCase(3,4);
    p.afficher(); */
    // o.nombreChoisi();
    // o.ouiNon();
    // o.action();
    lancer();
  }

}
