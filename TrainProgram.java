import java.util.*;

public class TrainProgram{
        private Graph d;
        private double[] distTo; // for min paath
        private Edge[] edgeTo;   // formin path
        private PriorityQueue<Integer> pq; //for minpath

        private int[] visited; //for minpath
        private int numberOfPaths; // count of paths
        private boolean flag; // helps don't stop to calculate on the first step if vertex "to" equaals vertex "from"
        private HashSet<String> paths;// helps to count only unic paths

        public Test(Graph d){
                visited = new int[d.V()];
                distTo = new double[d.V()];
                edgeTo = new Edge[d.V()];
                visited = new int[d.V()];
                this.d = d;
        }
/*********************************************************************
*                        Min path searching                          *
**********************************************************************/
        public void minPathFrom(int from){
                pq = new PriorityQueue<Integer>();
                for (int i=0; i<d.V(); i++){
                        distTo[i] = Double.POSITIVE_INFINITY;
                        visited[i] = 0;
                }
                distTo[from]=0.0;
                visited[from] = 1;
                pq.add(from);
                while(!pq.isEmpty()){
                        int to = pq.poll();
                        HashSet<Edge> adj = d.adj()[to];
                        for (Edge e: adj){
                                checkDist(to, e);
                                if (visited[e.getTo()] == 0){
                                        pq.add(e.getTo());
                                        visited[e.getTo()] = 1;
                                }
                        }
                }
        }
/*************************************************************************
* Min path searching - second step if vertex "to" equaals vertex "from"  *
**************************************************************************/
    public double  minPathTo(int to){
                double dist = Double.POSITIVE_INFINITY;
                double[] temp = distTo.clone();
                for(int i=0; i<d.V(); i++){
                        if (i==to || temp[i]==Double.POSITIVE_INFINITY ||  temp[i]>dist) continue;
                        minPathFrom(i);
                        if (distTo[to]+temp[i]<dist) dist=distTo[to]+temp[i];
                }
                return dist;
        }
/*************************************************************************
*                   Check distnce for Min path searching                 *
**************************************************************************/

        public void checkDist(int from, Edge e){
                int to = e.getTo();
                if( distTo[to] > (distTo[from]+e.getWeight())){
                        distTo[to] = distTo[from]+e.getWeight();
                        edgeTo[to] = e;
                }
        }
/*************************************************************************
*      Main function for invoking of MinPath functionality               *
**************************************************************************/
        public double minPathFromTo(int v, int w){
                minPathFrom(v);
                if (v!=w){
                        return distTo[w];
                } else{
                         return minPathTo(w);
                }
        }
/*************************************************************************
*      Main function for invoking of MinPath functionality               *
**************************************************************************/
        public String minPathFromTo(char v, char w){
                double dist = minPathFromTo((int)(v-'A'), (int)(w-'A'));
                if (dist == Double.POSITIVE_INFINITY) return "NO SUCH ROUTE";
                else return ""+dist;

        }
/*************************************************************************
*                       Distance between two points                      *
**************************************************************************/
        public double distFromTo(int v, int w){
                for(Edge e: d.adj()[v]){
                        if (e.getTo()==w) return e.getWeight();
                }
                return Double.POSITIVE_INFINITY;
        }
        
        public String distFromTo(char from, char to){
                int v = (int) from -'A';
                int w = (int) to - 'A';
                double dist = distFromTo(v, w);
                if (dist == Double.POSITIVE_INFINITY) return "NO SUCH ROUTE";
                else return ""+dist;
        }

        public String distFromTo(String str){
                String[] tokens = str.replaceAll("\\s","").split("-");
                int length = tokens.length;
                if (length<2)  return "Wrong input parameters"; //We can add more checking if we will understande input restrictions
                int i=0;
                double dist = 0.0;
                while (i<length-1){
                        int from = (int)tokens[i].charAt(0)-'A';
                        i++;
                        int to = (int)tokens[i].charAt(0)-'A';
                        dist += distFromTo(from, to);
                        if (dist == Double.POSITIVE_INFINITY) return "NO SUCH ROUTE";
                }
                return ""+dist;
        }
/*************************************************************************
*      Main function for invoking of  All Paths counting functionality   *
* Parameters:                                                            *
*   from - vertex "from"                                                 *
*   to - vertex "to"                                                     *
*  max - limit by total depth                                            *
* operation - 0 (depth should be equal max)                              *
*             1 (depth shoild be less or equal (??)                      *
*             3 (check total distance - should be less then dist parametr*
* dist - limit by total distance                                         *
**************************************************************************/
public void allPaths(int from, int to, int max, int operation, double dist) {
                numberOfPaths =0;
                flag = false;
                paths = new HashSet<String>();
                dfs( from, to, max, 0, operation, dist, 0, ""+(char)(from + 'A'));
        }


        public void allPaths(char from, char to, int max, int operation, double dist) {
                allPaths((int)(from-'A'), (int)(to-'A'), max, operation, dist);
         }
/*************************************************************************
*                     function for All Path functionality                *
* Additional prameters:                                                  *
* depth - depth for prev steps                                           *
* dist1 - dostance for prev steps                                        *
* str - String of the  path                                              *
**************************************************************************/
        private void dfs(int v, int t, int max, int depth, int operation, double dist, double dist1, String str) {
                if ((depth>max && operation == 1)||(depth==max && operation == 0 )||(dist<=dist1 && operation ==3 )) {return;}
                if (v == t && flag) {
                        if(paths.add(str)) numberOfPaths++;
                }
                flag = true;
                for (Edge e : d.adj()[v]) {
                        int w = e.getTo();
                        dfs(w, t, max, depth+1, operation, dist, dist1+e.getWeight(), str+(char)(w+'A'));
                        }
                return;
        }
/*************************************************************************
*                function for returning of Numbers of paths              *
**************************************************************************/
        public int getNumberOfPaths(){
                return numberOfPaths;
        }
        public static void main(String[] arg){
                Graph d = new Graph(5);
                Test t = new Test(d);
                System.out.println("The distance of the route A-B-C:");
                System.out.println("Output #1: " + t.distFromTo("A-B-C"));
                System.out.println("The distance of the route A-D:");
                System.out.println("Output #2: " + t.distFromTo("A-D"));
                System.out.println("The distance of the route A-D-C:");
                System.out.println("Output #3: " + t.distFromTo("A-D-C"));
                System.out.println("The distance of the route A-E-B-C-D:");
                System.out.println("Output #4: " + t.distFromTo("A-E-B-C-D"));
                System.out.println("The distance of the route A-E-D:");
                System.out.println("Output #5: " + t.distFromTo("A-E-D"));
                System.out.println("The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).");
                t.allPaths('C' , 'C', 3, 1, 0);
                System.out.println("Output #6: " + t.getNumberOfPaths());
                System.out.println("The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).");
                t.allPaths('A', 'C', 4, 0, 0);
                System.out.println("Output #7: " + t.getNumberOfPaths());
                System.out.println("The length of the shortest route (in terms of distance to travel) from A to C");
                System.out.println("Output #8: " + t.minPathFromTo('A','C'));
                System.out.println("The length of the shortest route (in terms of distance to travel) from B to B");
                System.out.println("Output #9: " + t.minPathFromTo('B','B'));
                System.out.println("The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.");
                t.allPaths('C', 'C', 0, 3, 30);
                System.out.println("Output #10: " + t.getNumberOfPaths());
        }

}
