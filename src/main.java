

import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       Grafo g = new Grafo();
       Grafo.Vertice a = g.adicionaVertice("a");
       Grafo.Vertice b = g.adicionaVertice("b");
       Grafo.Vertice c = g.adicionaVertice("c");
       Grafo.Vertice d = g.adicionaVertice("d");
       
       g.conecta(a,b);
       g.conecta(a,c);
       g.conecta(a,d);
       g.conecta(b,c);
       g.conecta(b,d);
       g.conecta(c,d);
       Set adj = g.adjacentes(b);
      // g.removeVertice(c);
      
       
       System.out.println("ORDEM : " + g.ordem());
       
       System.out.println("VERTICE ALEATORIO : " + g.umVertice().nome);
       
       System.out.println("GRAU de B: " + g.grau(b));
       
       System.out.println("ADJACENTES de B : " + adj);
       
       System.out.println("VERTICES : " + g.vertices());
       
       System.out.println("É REGULAR : " + g.ehRegular());
       
       System.out.println("É COMPLETO : " + g.ehCompleto());
       
       System.out.println("FECHO TRANSITIVO DE B : " + g.fechoTransitivo(b));
       
       System.out.println("É CONEXO : " + g.ehConexo());
       
       System.out.println("É ÁRVORE : " + g.ehArvore());
        
    }
    
}
