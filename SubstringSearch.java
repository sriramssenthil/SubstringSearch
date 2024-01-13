

public class SubstringSearch{

    // Runtime: O(n+m)
    // Implementation of String.indexOf()
    // Returns the starting index of a substring s in String t or -1 if the substring does not occur
    public static int search(String t, String s){
        int subLength = s.length();

        if(t.length() == 0 || subLength > t.length()){
            return -1;
        }

        int x = 53;
        int prime = 1000000007;        

        // Utilizes a polynomial rolling hash
        long subHash = hash(s, x, prime);
        long tHash = hash(t.substring(0,subLength), x , prime);

        for(int i = 0; i<t.length()-s.length()+1; i++){
            if (tHash == subHash){
                // Check for collisions
                if (t.substring(i,i+subLength).equals(s)){
                    //System.out.println(tHash);
                    return i;
                }
            }


            //Optimization to calculate hash of next substring without recalculating the entire hash
            //Subtraction of two polynomial rolling hashes
            tHash = (tHash - ((t.charAt(i)  - 'a' + 1) * (long)Math.pow(x, subLength-1)))%prime;
            tHash = (tHash * x)%prime;
            tHash = (tHash + t.charAt(i+subLength)  - 'a' + 1)%prime;
            //System.out.println(tHash);


            
            
        }
        return -1;
    }

    // Calculation of the polynomial rolling hash
    public static long hash(String s, int x, int prime){
        long hash = 0;
        long pow  = 1;

        // Addition of each term in the polynomial
        for (int i=s.length()-1; i>=0; i--){
            hash = ((hash + ((s.charAt(i)) - 'a' + 1) * pow) % prime);
            pow = (pow * x) % prime;
        }

        return hash;
    
    }

    public static void main(String[] args){
        // System.out.println(hash("is", 53, 1000000007));
        // System.out.println(hash("th", 53, 1000000007));

        int x = search("abcdefghijklmnop", "defgh");

        System.out.println(x);
    }


}