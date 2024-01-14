

public class SubstringSearch{

    // Runtime: O(n+m)
    // Implementation of String.indexOf() through the usage of polynomial rolling hashes
    // Returns the starting index of a substring s in String t or -1 if the substring does not occur
    public static int search(String textStr, String searchStr){
        int subLength = searchStr.length();

        // Two prime values used for the building and evaluating the polynomial hash
        // x is the argument of the polynomial
        // modulo is the modulo to prevent overflow
        final long x = 53;
        final long modulo = 1000000007;   

        // Case to terminate early
        if(textStr.length() == 0 || subLength > textStr.length()){
            return -1;
        }

             
        // Utilizes a polynomial rolling hash
        long subHash = hash(searchStr, x, modulo);
        long tHash = hash(textStr.substring(0,subLength), x , modulo);

        // Keeping the hash values positive to prevent comparing negative and positive hashes
        if(subHash<0){
            subHash+=modulo;
        }

        if(tHash<0){
            tHash+=modulo;
        }
        

        // for-loop to iterate through all hashes in the substrings of length subLength in textStr
        // Given n = length of text string and m = length of search string: 
        //  for-loop runs a total of n(1+(m/modulo)*m) times as there is a maximum of m/modulo collisions
        // CChoosing modulo > m^2 allows us to optimize the run-time
        for(int i = 0; i<textStr.length()-searchStr.length()+1; i++){
                           
            // Check for collisions
            if (tHash == subHash){
                // Runs a maximum of once in O(m) time
                if (textStr.substring(i,i+subLength).equals(searchStr)){
                    return i;
                }
            }

            //Optimization to calculate hash of next substring without recalculating the entire hash
            //Subtraction of two polynomial rolling hashes
            tHash = (tHash - ((textStr.charAt(i)  - 'a' + 1) * (long)Math.pow(x, subLength-1)))%modulo;
            if(tHash<0){
                tHash+=modulo;
            }
            tHash = (tHash * x)%modulo;

            if((i+subLength<textStr.length())){
                tHash = (tHash + textStr.charAt(i+subLength)  - 'a' + 1)%modulo;
            }
        }
        // If substring is not found
        return -1;
    }


    // Calculation of the polynomial rolling hash
    // Read more: https://www.geeksforgeeks.org/string-hashing-using-polynomial-rolling-hash-function/
    public static long hash(String s, long x, long modulo){
        long hash = 0;
        long pow  = 1;

        // Addition of each term in the polynomial
        for (int i=s.length()-1; i>=0; i--){
            hash = ((hash + ((s.charAt(i)) - 'a' + 1) * pow) % modulo);
            pow = (pow * x) % modulo;
        }

        return hash;
    
    }

    public static void main(String[] args){
        System.out.println(hash("Nhi", 53, 1000000007));
        System.out.println("");

        int x = search("thisIsn'tatest", "'ta");

        System.out.println(x);
    }


}