public class RabinKarp
{
    //TODO by student: many things are not correct here
    private String [] pat; // pattern (only needed for Las Vegas)
    private long [] patHash; // pattern hash value
    private int [] M; // pattern length
    private long Q; // a large prime
    private int R = 2048; // alphabet size
    private long RM; // R^(M-1) % Q

    public RabinKarp(String pat)
    {
        this(new String[]{pat});
    }

    public RabinKarp(String[] pat)
    {
        //TODO by student: it's obviously not correct
        //pat={“have”, “find”, “Karp”}
        Q = 4463; // a large prime
        this.pat = new String[pat.length];
        M = new int[pat.length];
        patHash = new long[pat.length];
        for (int j=0; j<pat.length; j++) {
            //System.out.println("pat["+j+"] " +pat[j]);
            this.pat[j] = pat[j];
            this.M[j] = pat[j].length();
            patHash[j] = hash(pat[j], M[j]);
        }
        /* RM constant :-) */
        RM = 1;
        for (int i = 1; i <= M[0]-1; i++) // Compute R^(M-1) % Q for use
            RM = (R * RM) % Q; // in removing leading digit.
    }


    public boolean check(int i,String txt, int index) // Monte Carlo (See text.)
    {
        //TODO by student: it's obviously not correct
        ////System.out.println("txt " + txt);
        ////System.out.println("p.length " + pat.length);



        //System.out.println("M[j] " + M[j] + ", pat[j] " + pat[j]+", txt " + txt.substring(i, i + M[j]));
        //System.out.println("pat[j] " + pat[j] + ", j " + j);
        if (pat[index].equals(txt.substring(i, i + M[index])))
            return true;

        return  false;// For Las Vegas, check pat vs txt(i..i-M+1).
    }

    private long hash(String key, int M)
    { // Compute hash for key[0..M-1].
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }


    public int search(String txt)
    { // Search for hash match in text.
        //TODO by student: it's obviously not correct
        int N = txt.length();
        int finish =0;
        for (int i = 0; i<pat.length;i++){
            if (N>=M[i]){
                finish=1;
                break;
            }
        }
        if (finish==0){return txt.length();}

        long txtHash = hash(txt, M[0]); //tous de meme taille
        //System.out.println(pat.length);
        for (int j =0 ; j<pat.length; j++) {
            if (patHash[j] == txtHash){
                if (check(0,txt, j)){
                    return 0; // match
                }
                // Match at beginning.
            }
            //System.out.println("patHash["+j+"] = "+ patHash[j]);
        }
        //waiting

        for (int i = M[0]; i < N; i++)
        { // Remove leading digit, add trailing digit, check for match.
            txtHash = (txtHash + Q - RM*txt.charAt(i-M[0]) % Q) % Q;
            txtHash = (txtHash*R + txt.charAt(i)) % Q;
            //System.out.println("txtHash "+txtHash);
            for (int j =0; j<pat.length;j++){
                //System.out.println(patHash[j]);
                if (patHash[j] == txtHash){
                    //System.out.println(txt);

                    if (check(i - M[j] + 1,txt, j)){
                        return i - M[j] + 1; // match
                    }
                }
            }
        }
        return N; // no match found
    }
}