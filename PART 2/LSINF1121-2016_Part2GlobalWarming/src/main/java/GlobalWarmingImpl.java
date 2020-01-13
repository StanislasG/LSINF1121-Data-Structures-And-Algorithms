import java.util.Arrays;

public class GlobalWarmingImpl extends GlobalWarming {

    //technique, mettre en 1D puis de nouveau en 2D
    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        // expected pre-processing time in the constructor : O(n^2 log(n^2))

        //2D to 1D, O(n)
        int totLen=0;
        for (int[] row : altitude) {
            totLen += row.length;
        }
        int [] altitude1D =  new int[totLen];

        int posCopy = 0;
        for (int[] row : altitude) {
            System.arraycopy(row, 0, altitude1D, posCopy, row.length);
            posCopy += row.length;
        }

        //O(n lg(n))
        Arrays.sort(altitude1D);

        //O(n)
        for (int i = 0; i < altitude.length; i++){//row
            for (int j = 0; j < altitude[i].length; j++){ //column
                altitude[i][j]=altitude1D[i*altitude.length+j];
            }
        }
    }


    // autre possibilite est de compter le nb de point dans le constructeur
    public int nbSafePoints(int waterLevel) {
        // expected time complexity O(log(n^2))
        int result=0;
        for (int i=altitude.length-1; i>=0; i--){ //row check
            if(altitude[i][0] > waterLevel){
                result += altitude[i].length;
            }
            else{
                for (int j=altitude[i].length-1; j>=0; j--){ //column check
                    if(altitude[i][j]<=waterLevel){break;}
                    result++;
                }
                break;
            }
        }
        return result;
    }
}