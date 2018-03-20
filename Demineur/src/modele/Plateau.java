package src.modele;

import java.util.Random;
public class Plateau {
  private final int hauteur, largeur, nbMines;
  private int revelations;                // cet attribut permet de vérifier facilement si la partie est finie.
  private boolean blowUP = false;         // cet attribut permet de vérifier facilement si une bombe a été touché.
  private  boolean[][] mines;
      /*indique où sont les mines sur le plateau*/
  private final int[][] etats;
      /*indique dans quel état est chaque case
        (cachée, révélée, avec/sans drapeau)--> (0,1,2)*/
  private  int[][] adja;
      /*indique le nombre de mines adjacentes
        à chaque case*/

  Random rand = new Random();
  // public static final String ANSI_RESET = "\u001B[0m";
  // public static final String ANSI_BLACK = "\u001B[30m";
  // public static final String ANSI_RED = "\u001B[31m";
  // public static final String ANSI_GREEN = "\u001B[32m";
  // public static final String ANSI_YELLOW = "\u001B[33m";       --> projet possible de mettre de la couleur dans le terminal
  // public static final String ANSI_BLUE = "\u001B[34m";
  // public static final String ANSI_PURPLE = "\u001B[35m";
  // public static final String ANSI_CYAN = "\u001B[36m";
  // public static final String ANSI_WHITE = "\u001B[37m";

  // public Plateau(int h, int l, int m){
  //   hauteur = h; largeur = l; nbMines = m; revelations = h * l - m;
  //   mines = new boolean[h][l];
  //   etats = new int[h][l];
  //   adja = new int[h][l];
  //   initialiserMines();
  //   initialiseAdja();
  // }

///------------------------------INITIALISATION DU PLATEAU----------------------------------------------------------

  // constructeur
  public Plateau(int h, int l, int m){
    hauteur = h; largeur = l; nbMines = m; revelations = h * l - m;
    mines = new boolean[h][l];
    etats = new int[h][l];
    adja = new int[h][l];
  }

  // permet de construire le plateau de telle sorte que le premier coup du joueur ne soit jamais sur une bombe.
  public void initialiserPlateau(int x, int y){
    initialiserMines();
    initialiseAdja();
    if (adja[x][y] != 0) {
      mines = new boolean[hauteur][largeur];
      adja = new int[hauteur][largeur];
      initialiserPlateau(x,y);
    }
  }

  // initialise les mines de façon aléatoire dans mine[][]
  public void initialiserMines(){
    int i =0, j=0;
    for (int acc = 0; acc<nbMines; acc++){
      do {
        i = rand.nextInt(hauteur);
        j = rand.nextInt(largeur);
      }while (mines[i][j]);
      mines[i][j] = true;
    }
  }

  // initialise le tableau des adjacents
  public void initialiseAdja(){
    for (int i = 0; i< hauteur; i++){
      for (int j = 0; j<largeur; j++){
        if (mines[i][j]){
          for (int k = -1; k<2; k++){
            for (int l = -1; l<2; l++){
              workAdja(i+k,j+l);
            }
          }


          // prototype abandonné
          /*if (i != 0) {
            adja[i-1][j]++;
            if (j!=0) adja[i-1][j-1]++;
            if (j!=largeur-1) adja[i-1][j+1]++;
          }
          if (i != hauteur-1){
            adja[i+1][j]++;
            if (j != 0) adja[i+1][j-1]++;
            if (j != largeur-1) adja[i+1][j+1]++;}
          if (j != largeur-1) adja[i][j+1]++;
          if (j != 0) adja[i][j-1]++;*/
        }
      }
    }
  }

  // initialise le tableau de façon récursive
  public void workAdja(int i, int j){
    if (!(doesntExist(i,j))) adja[i][j]++;
  }



/// **************************************************************************************************
///------------------------------LES ACTIONS----------------------------------------------------------
/// **************************************************************************************************

  // effectue la première action sur le plateau et s'occupe d'initialiser le plateau pour cela.
  public void premierAgir(int[] n){
    initialiserPlateau(n[0], n[1]);
    agir(n[0], n[1], n[2]);
    afficher();
  }

  // agis en fonction de la volonté du joueur.
  public void agir(int x, int y, int s){
    if (doesntExist(x,y)) return;
    if (s == 2){
      if (etats[x][y] == 0)   etats[x][y] = 2;
      else etats[x][y] = 0;
    }
    if (s == 1){
      if (etats[x][y] == 1 && checkFlag(x,y)){
        this.workRevele(x,y);
      }
      if (etats[x][y] == 2) return;
      this.revelerCase(x,y);
    }
  }

  // revele la case cibler par le joueur de façon récursive
  public void revelerCase(int i, int j){
    if (doesntExist(i,j)
      || etats[i][j] == 2) return;
    if (mines[i][j]) blowUP = true;
    if (etats[i][j] == 0){
      etats[i][j] = 1; revelations--;
      if (adja[i][j] == 0) this.workRevele(i,j);
    }
  }

  // la récursion de revelerCase, brutal mais fonctionne très bien.
  public void workRevele(int i, int j){
    this.revelerCase(i-1,j-1);
    this.revelerCase(i-1,j);
    this.revelerCase(i-1,j+1);
    this.revelerCase(i,j-1);
    this.revelerCase(i,j+1);
    this.revelerCase(i+1,j-1);
    this.revelerCase(i+1,j);
    this.revelerCase(i+1,j+1);
  }

/// ************************************************************************************************************
///------------------------------DIFFERENTS TESTS---------------------------------------------------------------
/// ************************************************************************************************************

  // vérifie si le nombre de drapeau autour d'une case est égal au nombre de bombe autour de la case.
  public boolean checkFlag(int i, int j){
    int bombAdja = adja[i][j];
    for (int k = -1; k<2; k++){
      for (int l = -1; l<2; l++){
        if (!doesntExist(i+k,j+l) && etats[i+k][j+l] == 2) bombAdja--;
      }
    }
    return (bombAdja == 0);
  }

  // renvoie true si [i][j] envoie hors des limites du plateau
  public boolean doesntExist(int i , int j){
    return (i < 0
      || i > hauteur-1
      || j < 0
      || j > largeur-1);
    }

  // renvoie true si la partie est terminée
  public boolean fini(){ return (blowUP || revelations==0);}
  // renvoie true si une bombe a explosé
  public boolean blows() { return blowUP;}




/// ************************************************************************************************************
///------------------------------DIFFERENTS AFFICHAGES----------------------------------------------------------
/// ************************************************************************************************************

  // renvoie la première ligne de l'affichage des tableaux
  public String beginEND(){
    String s = "  ";
    for (int i = 0; i<largeur; i++) {
      if (i<10){ s+= " ";}
      s += i + " ";
    }
    return s;
  }

  // renvoie une chaîne de caractère contenant l'état du jeu sur le plateau
  public String affichage(){
    String s = beginEND();
    for (int i = 0; i < hauteur; i++){
      s+= "\n"; if (i<10){ s+= " ";}
      s += i + " ";
      for (int j = 0; j < largeur; j++){
        if (etats[i][j] == 0) s += ".  ";
        else if (etats[i][j] == 1 && mines[i][j]) s += "*  ";
        else if (etats[i][j] == 1) s += ((adja[i][j] == 0)?"   ":(adja[i][j] + "  "));
        else s+= "?  ";
      }
    }
    return s + "\n";
  }

  // affiche l'état du plateau de jeu
  public void afficher(){
    System.out.println(this.affichage());
  }

  // affiche le tableau mines de façon compréhensible à des fin de débuggage
  public void debugMines(){
    String s = this.beginEND() + "\n"; int i = 0;
    for (boolean[] t : mines) {
      s += i + " |";
      i++;
      for (boolean b : t) {
        if (b) s+= "#|";
        else s+= ".|";
      }
      s+= "\n";
    } System.out.println(s);
  }

  // affiche le tableau mines de façon compréhensible à des fin de débuggage
  public void debugAdja(){
    String s = this.beginEND() + "\n\n"; int i = 0;
    for (int[] t : adja) {
      s += i + "  "; i++;
      for (int b : t) {
        s+= b + " ";
      }
      s+= "\n";
    } System.out.println(s);
  }
}
