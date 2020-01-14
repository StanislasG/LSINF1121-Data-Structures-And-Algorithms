public class GlobalWarmingImpl extends GlobalWarming {
    public int[] unions;
    public int[] size;
    public int count;

    public GlobalWarmingImpl(int[][] altitude, int waterLevel) {
        super(altitude,waterLevel);

        unions = new int[altitude.length*altitude.length];
        size = new int[altitude.length*altitude.length];
        count=nbSafePointsCorrect(waterLevel);

        for(int i=0;i<unions.length;i++){
            unions[i]=i;
            size[i]=1;
        }

        for(int i=0;i<altitude.length;i++){ //row
            for(int j=0;j<altitude.length;j++){ //column
                if(altitude[i][j]>waterLevel){
                    if(i>0 && altitude[i-1][j]>waterLevel){ //check left
                        union(new Point(i,j), new Point(i-1,j));
                    }
                    if(j>0 && altitude[i][j-1]>waterLevel){ //check top
                        union(new Point(i,j), new Point(i,j-1));
                    }
                }
            }
        }

    }

    public int find(int p){
        int old_p=p;
        while(p!=unions[p]){
            p=unions[p];
        }
        //start @ old_p to connect with the root p
        while(old_p!=p){
            int old = unions[old_p];
            unions[old_p]=p;
            old_p=old;
        }
        return old_p; //now old_p is also the root
    }

    public void union(Point p1, Point p2){
        int i = find(p1.x*altitude.length+p1.y);
        int j = find(p2.x*altitude.length+p2.y);
        if(i==j){
            return;
        }

        if(size[i]<size[j]){
            unions[i]=j;
            size[j]+=size[i];
        }
        else{
            unions[j]=i;
            size[i]+=size[j];
        }

        count--;
    }

    public int nbIslands() {
        return count;
    }


    public boolean onSameIsland(Point p1, Point p2) {
        if(altitude[p1.x][p1.y]<=waterLevel || altitude[p2.x][p2.y]<=waterLevel){
            return false;
        }

        if(p1.equals(p2))
            return true;

        return find(p1.x*altitude.length+p1.y) == find(p2.x*altitude.length+p2.y);
    }

    //good for debugging
    public void string(int[] tab){
        for(int i=0;i<tab.length;i++){
            System.out.print(tab[i]+" ");
            if(i%(altitude.length-1)==0){
                System.out.println(" ");
            }
        }
        System.out.println("-----------------");
    }

}