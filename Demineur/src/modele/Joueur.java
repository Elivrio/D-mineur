package src.modele;

import java.util.Scanner;
public class Joueur {
    private String nom;
    static Scanner sc = new Scanner(System.in);

    public Joueur(){
        //Par défaut, le nom du joueur est "Anonyme". Le constructeur initialise un scan qui pourra vous être utile.
        nom = "Anonyme";
    }

    // constructeur pour un joueur au nom donné
    public Joueur(String n){
      nom = n;
    }

    // permettent de modifier ou de récupérer nom. Personnelement je ne m'en sers pas (demandé dans le TP).
    public String getNom(){ return nom;}
    public void setNom(String s){ nom = s;}

    // demande au joueur un nombre ( et uniquement un nombre).
    public static int nombreChoisi(String str){
      if (str.equals("")) str = "Veuillez choisir un nombre : ";
      boolean b = true; int acc = 0;
      do {
        System.out.print(str);
        String s = sc.next();
        acc = 0; b = true;
        for (int i = 0; i < s.length(); i++){
          if (number(s,i)) {
            acc = acc*10 + s.charAt(i)-48;
          } else b = false;
        }
        if (!b) System.out.println("Ceci n'est pas une entrée correcte");
      } while (!b);
      return acc;
    }

    // demande au joueur un boolean (en français) (o/n sont également accepté).
    public static boolean ouiNon(){
      boolean b = false;
      do {
        System.out.print("Quel est votre choix, oui ou non : ");
        String s = sc.next();
        s = s.toLowerCase();
        if (s.equals("oui") || s.equals("o")) return true;
        else if (s.equals("non") || s.equals("n")) return false;
      } while (!b);
      return b; // inutile mais évite le missing return statement
    }

    // vérifie que la chaine de caractère passer en argument possède un chiffre à la position i.
    public static boolean number(String s, int i){
      int n = (int)s.charAt(i);
      return (n >= 48 && n <= 58);
    }

    // demande au joueur quels sont les actions qu'il souhaite effectuer.
    public int[] action(){
      int[] n = new int[3];
      n[0] = nombreChoisi("Veuillez choisir un nombre pour la ligne : ");
      n[1] = nombreChoisi("Veuillez choisir un nombre pour la colonne : ");
      n[2] = nombreChoisi("Veuillez entre 1 (= révéler une case) et 2 (= poser un drapeau)  : ");
      while (n[2]!=1 && n[2]!=2) {
        n[2] = nombreChoisi("Veuillez choisir entre 1 et 2 ! ");
      }
      return n;
    }
}
