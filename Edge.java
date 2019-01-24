public class Edge{
        private final int w; // vertex "to"
        private final double weight;

        public Edge(int w, double weight){
                this.w = w;
                this.weight = weight;
        }

        public Edge(char w, double weight){
                this.w = w - 'A';
                this.weight = weight;
        }

        public int getTo(){
                return w;
        }

        public double getWeight(){
                return weight;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Edge other = (Edge) obj;
                if (w == other.getTo() && weight == other.getWeight()) return true;
                else return false;
        }

        @Override
        public int hashCode() {
                Double d = new Double(weight);
                return (int)  w * d.intValue();
        }
}
