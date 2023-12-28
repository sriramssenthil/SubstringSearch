
public class SubstringSearch{

    // Implementation of String.indexOf()
    // Returns the starting index of a substring s in String t or -1 if the substring does not occur
    public static int search(String t, String s){
        int subLength = s.length();

        if(t.length() == 0 || subLength > t.length()){
            return -1;
        }

        int x = 37;
        int prime = 1000000007;

        // Utilizes a polynomial rolling hash
        int subHash = hash(s, x, prime);
        System.out.println(subHash);
        int tHash = hash(t.substring(0,subLength), x , prime);

        for(int i = 0; i<t.length()-s.length(); i++){
            tHash = hash(t.substring(i, i+subLength), x, prime);
            if (tHash == subHash){
                // Check for collisions
                if (t.substring(i,i+subLength).equals(s)){
                    System.out.println(tHash);
                    return i;
                }
            }

            //Calculate hash of next substring without recalculating the entire hash
            // Subtraction of two polynomial rolling hashes
            // tHash = (tHash * x)%prime;
            // tHash = (tHash - (t.charAt(i) * (int)Math.pow(x, subLength)))%prime;
            // tHash = (tHash + t.charAt(+subLength))%prime;
            
        }
        System.out.println(tHash);
        return -1;
    }

    // Polynomial rolling hash
    public static int hash(String s, int x, int prime){
        int hash = 0;
        long pow  = 1;

        for (int i=0; i<s.length(); i++){
            hash = (int)((hash + ((int)(s.charAt(i))) * pow) % prime);
            pow = (pow * x) % prime;
        }

        return hash;
    
    }

    public static void main(String[] args){
        System.out.println(search("thisisaverylongstringusedfortesting", "long"));
    }


}