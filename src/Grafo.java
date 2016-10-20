/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Grafo {
    Map<String,Vertice> vertices;

   // Map<Aresta> arestas;
    public Grafo() {
        vertices = new HashMap<String,Vertice>();
       
        //arestas = new ArrayList<Aresta>();
    }

    /**
     * Adiciona um vertice no grafo
     * @param nome
     * @return 
     */
    Vertice adicionaVertice(String nome) {
        Vertice v = new Vertice(nome);
        this.vertices.put(nome,v);
        return v;
    }
    
    /**
     * remove um vertice e suas conexões 
     * @param vertice 
     */
     void removeVertice(Vertice vertice)
    {
         if(this.vertices.containsKey(vertice.nome))
        {
            for(String v : vertice.adjacentes.keySet())
            {
                if(v != null)
                {
                    desconecta(vertice,this.vertices.get(v));
                }
                
            }
            
            this.vertices.remove(vertice.nome);
           
        }else
        {
             System.out.println("Erro no nome do vertice");
        }
    }

     /**
      * Cria a aresta entre dois vertices
      * @param verticeOrigem
      * @param verticeDestino 
      */
    void conecta(Vertice verticeOrigem, Vertice verticeDestino) {
        
        if(this.vertices.containsKey(verticeOrigem.nome) && this.vertices.containsKey(verticeDestino.nome))
        {
            Aresta novaAresta = new Aresta(verticeOrigem,verticeDestino);
            verticeOrigem.adjacentes.put(verticeDestino.nome,novaAresta);
            
            Aresta novaAresta2 = new Aresta(verticeDestino,verticeOrigem);
            verticeDestino.adjacentes.put(verticeOrigem.nome,novaAresta2);
            
           // System.out.println("NOVA ARESTA : " + novaAresta.origem.nome + " - " + novaAresta.destino.nome);
            
           // System.out.println("ADJACENTE : " +verticeOrigem.adjacentes.get(verticeDestino.nome).destino.nome);

        }else
        {
             System.out.println("Erro no nome dos vertices");
        }
        
    }
    /**
     * Desconecta uma aresta entre dois vertices
     * @param verticeOrigem
     * @param verticeDestino 
     */
    void desconecta(Vertice verticeOrigem, Vertice verticeDestino) {
        
        if(this.vertices.containsKey(verticeOrigem.nome) && this.vertices.containsKey(verticeDestino.nome))
        {
            verticeOrigem.adjacentes.remove(verticeDestino.nome);
            verticeDestino.adjacentes.remove(verticeOrigem.nome);
           
        }else
        {
             System.out.println("Erro no nome dos vertices");
        }
        
    }
    
    /**
     * Retorna a quantidade de vértices do grafo
     * @return 
     */
    int ordem()
    {
        return this.vertices.size();
    }
    
    /**
     * Retorna um conjunto contendo todos os vértices
     * @return 
     */
    Set<String> vertices()
    {
        return this.vertices.keySet();
    }
    
    /**
     * Retorna um vértice aleatório
     * @return 
     */
    Vertice umVertice()
    {
        List<String> keysAsArray = new ArrayList<String>(this.vertices.keySet());
        Random r = new Random();
        return this.vertices.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
    }
    
    /**
     * Retorna um conjunto com os vertices adjacentes ao vertice
     * @param vertice
     * @return 
     */  
    Set<String> adjacentes(Vertice vertice)
    {
         if(this.vertices.containsKey(vertice.nome))
        {
            /* for(String v : vertices.get(vertice).adjacentes.keySet())
            {
                if(v != null)
                    System.out.println("ADJACENTE :"+v+" -" + vertices.get(v).nome );
            } */
           return vertice.adjacentes.keySet();
        }else
        {
             System.out.println("Erro no nome do vertice");
             return null;
        }
         
    }
    
    /**
     * Retorna a quantidade de arestas conectadas no vértice
     * @param vertice
     * @return 
     */
    int grau(Vertice vertice)
    {
        if(this.vertices.containsKey(vertice.nome))
        {
           return vertice.adjacentes.size();
        }else
        {
             System.out.println("Erro no vertice");
        }
          return -1;  
    }
    
    /**
     * Avalia se todos os vértives tem o mesmo grau, se sim retorna true
     * @return 
     */
    boolean ehRegular()
    {
        int n = this.grau(this.umVertice());
        
        for(String v : this.vertices())
        {
            if(grau(this.vertices.get(v)) != n )
               return false;
        }
        
        return true;
    }
    /**
     * Verifica se todos os vértices estão conectados com todos os outros vértices
     * @return 
     */
    boolean ehCompleto()
    {
        int n = this.ordem() - 1;
        
        for(String v : this.vertices())
        {
           if(grau(this.vertices.get(v)) != n )
               return false; 
        }
        return true;
    }
    
    /**
     * Retorna o conjunto de todos os vértives alcançaveis a partir do vértice v
     * @param v
     * @return 
     */
    Set fechoTransitivo(Vertice v)
    {
        return procuraFechoTransitivo(v,new HashSet<String>());
    }
    
    /**
     * Método privado que percorre os adjacentes e monta o conjunto para o fecho transitivo
     * @param v
     * @param jaVisitados
     * @return 
     */
    
    private Set procuraFechoTransitivo(Vertice v, Set jaVisitados)
    {
      jaVisitados.add(v.nome);
      
      for(String vAdj : this.adjacentes(v))
      {
          if(!jaVisitados.contains(vAdj))
             procuraFechoTransitivo(this.vertices.get(vAdj),jaVisitados); 
      }
      
      return jaVisitados;
    }
       
    /**
     * Retorna true se o conjunto retornado por vertices() tem os mesmos elementos do conjunto
     * retornado pelo fechoTransitivo() de um vertice qualquer
     * @return 
     */
    boolean ehConexo()
    {
        return this.vertices().equals(this.fechoTransitivo(this.umVertice()));
    }
    
    /**
     * Retorna true se o grafo não contem ciclos 
     * @return 
     */
    boolean ehArvore()
    {
        Vertice v = umVertice();
        return (ehConexo() && !haCicloCom(v,v,new HashSet<String>()));
    }
    
    
    /**
     * Método privado que verifica a existencia de ciclos. Retorna true se o grafo contem clico.
     * @param v
     * @param vAnterior
     * @param jaVisitados
     * @return 
     */
    private boolean haCicloCom(Vertice v, Vertice vAnterior,Set jaVisitados)
    {
        if(jaVisitados.contains(v.nome))
            return true;
        
        jaVisitados.add(v.nome);
        for(String vAdj : this.adjacentes(v))
        {
          if(!this.vertices.get(vAdj).equals(vAnterior))
          {
             if(this.haCicloCom(this.vertices.get(vAdj), v, jaVisitados)) 
                 return true;
          }
            
        }
        jaVisitados.remove(v.nome);
        return false;
    }
    
    
    // ****************  CLASSES INTERNAS ******************************* 
   
    public class Vertice {
        String nome;
        Map<String,Aresta> adjacentes;

        Vertice(String nome) {
            this.nome = nome;
            this.adjacentes = new HashMap<String,Aresta>();
        }

        void addAdj(String verticeNome,Aresta e) {
            adjacentes.put(verticeNome,e);
        }
    }
    
    public class Aresta {
        Vertice origem;
        Vertice destino;
        double value;

        Aresta(Vertice origem, Vertice destino) {
            this.origem = origem;
            this.destino = destino;
            this.value =1;
        }
        
        Aresta(Vertice origem, Vertice destino, double value){
            this.origem = origem;
            this.destino = destino;
            this.value =value;
        }
    }
}
