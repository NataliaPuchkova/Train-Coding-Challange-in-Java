import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Graph{

        private int V;
        private HashSet<Edge>[] adj;

        public Graph(int V){

                this.V = V;
                adj = new HashSet[V];

                try{

                        BufferedReader br = new BufferedReader( new FileReader("input.txt"));
                        String str;
                        while (( str = br.readLine())!=null){
                                String[] tokens = str.replaceAll("\\s","").split(",");
                                for (String token: tokens){
                                        if (token.length()<3) continue;
                                        Double temp = Double.parseDouble(token.substring(2));
                                        char st1 = token.charAt(0);
                                        char st2 = token.charAt(1);
                                        Edge d1 = new Edge(st2, temp.doubleValue());
                                        addAdj((int)(st1-'A'), d1);
                                }
                        }

                } catch (FileNotFoundException e){
                        e.printStackTrace();
                        System.out.println("Could't open the file");
                } catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Could't parse the file");
                }
        }

        public int V(){
                return V;
        }

        public HashSet<Edge>[] adj(){
                return adj;
        }
        public void addAdj(int from, Edge d){
                if (adj[from]==null)
                        adj[from] = new HashSet<Edge>();
                adj[from].add(d);
        }

        public void print(){
                for(int i=0; i<V; i++){
                        if(adj[i]!=null)
                                for(Edge e:adj[i])
                                        System.out.print((char)('A'+i) + "-" +(char)('A'+e.getTo())+"-"+e.getWeight()+" ");
                        System.out.println();
                }
        }

        public static void main(String[] arg){
                Graph d = new Graph(5);
                d.print();
        }
}
