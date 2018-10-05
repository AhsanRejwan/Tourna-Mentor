package sample;

public class Standings {
    private String name;
    private int wins,loss,draws;
    private int[] values = new int[50];


    public Standings(String name,int n, int values[])
    {
        this.name =name;
        this.wins= values[0];
        this.loss= values[1];
        this.draws= values[2];
        for(int i=3;i<n+3;i++)
        {
            this.values[i] = values[i];
        }
    }
}
