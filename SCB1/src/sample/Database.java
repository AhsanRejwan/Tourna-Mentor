package sample;

import java.io.IOException;
import java.sql.*;

public class Database {

    Statement statement;
    ResultSet result;
    String CurrentTable,x;
    CallableStatement ctable;
    int id =0;


    Connection conn;

    public Database()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
           conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "tournamentor", "tournamentor");
            statement= conn.createStatement();
        } catch (Exception sqle) {
            System.out.println("SQL Exception 1: " + sqle);
        }
    }

    //create table of all tournaments

    public String CreateTournaments(String name, String type, int number)  {


            try
            {
                //Create Tournament Entry
                statement.executeUpdate("insert into Tournaments values ('" + name + "',null,'" + type + "'," + number + ",null, null )");
                //System.out.println("insert into Tournaments values ('" + name + "',null,'" + type + "'," + number + ",null )");
                statement.executeQuery("commit");

                //Get Generated ID
                result = statement.executeQuery("Select max(TNID) from tournaments where TNtype = '" + type + "'");
                while (result.next()) {
                    id = result.getInt(1);


                }
                x=Integer.toString(id);



                //Create Tournament Table
                CurrentTable = type+x;
                statement.executeUpdate("Create table "+ CurrentTable +"(TeamName varchar2(20), Wins Number, Draws Number, Losses Number)");
                statement.executeQuery("commit");


                //Create TournTable columns
                statement.executeUpdate("create table "+CurrentTable+"_Columns (Name varchar2(20))");
                statement.executeQuery("commit");

                //Create Teams table
                statement.executeUpdate("create table "+CurrentTable+"_Teams(Name varchar2(20) ,Captain varchar2(20) ,Contact varchar2(20) ,Organization varchar2(20),primary key(Name))");
                statement.executeQuery("commit");

                //Set the Current Table
                ctable = conn.prepareCall("{call Set_CurrentTable('" + CurrentTable + "')}");
                ctable.execute();
                statement.executeQuery("commit");

                //Set Current ID
                statement.executeUpdate("update global set CurrentID = " + id + "");
                statement.executeQuery("commit");
            }
            catch(SQLException ex)
            {
                System.out.println("Exception for database " +ex);
            }

        return x;
    }

    public void InitGlobal() throws SQLException
    {
        int count = 5;
        result = statement.executeQuery("select count(*) from global");
        while(result.next())
        {
            count = result.getInt(1);
        }
       // System.out.println(count);
        if(count == 0)
        {
            statement.executeUpdate("insert into global values('default','default',0)");
            statement.executeQuery("commit");
        }
        else
        {
            statement.executeUpdate("update global set currenttable = 'default'");
            statement.executeUpdate("update global set pattribute = 'default'");
            statement.executeUpdate("update global set currentid = 0");
            statement.executeQuery("commit");
        }
    }

    public String GetCurrentTable ()
    {
        String table = null;
        try {
            result = statement.executeQuery("Select * from global");
            while (result.next()) {
                table = result.getString(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Exception for GetCurrentTable:" + e);
        }
        return table;
    }

    public int GetCurrentID()
    {
        int CurrentID =0;
        try {
            result = statement.executeQuery("Select CurrentID from global");
            while (result.next()) {
                CurrentID = result.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Exception for GetCurrentTable:" + e);
        }
        return CurrentID;

    }
    public String GetCurrentType(int i)
    {
        String currentType = null;
        try{
            result= statement.executeQuery("select TNType from tournaments where TNID = " + i+"");
            while(result.next())
            {
                currentType = result.getString(1);
            }

        }
        catch(SQLException e)
        {
            System.out.println("Exception for GetCurrentTpye:" +e);
        }
        return currentType;
    }

    public void SetAttribute( String x)
    {
        try {
            ctable = conn.prepareCall("{call Set_Attribute('" + x + "')}");
            ctable.execute();
        }
        catch(SQLException e)
        {
            System.out.println("Exception for SetAttribute: " + e);
        }
    }

    public String GetCurrentAttribute()
    {
        String x = new String();
        try{
           result= statement.executeQuery("select pattribute from global");
           while(result.next())
           {
               x = result.getString(1);
           }
        }
        catch (SQLException e)
        {
            System.out.println("Exception to get current attribure" +e);
        }
        return x;
    }

    public void InsertColumns(String table, String x) {
        try
        {
            //System.out.println("alter table "+table+"add " + x + " int" );
            statement.executeUpdate("alter table "+ table +" add " + x + " int");
            statement.executeUpdate("insert into "+table+"_columns values('"+ x + "')");
            statement.executeQuery("commit");
            statement.executeUpdate("update "+table+" set " +x+ " = 0");
            statement.executeUpdate("update "+table+" set wins =0");
            statement.executeUpdate("update "+table+" set losses =0");
            statement.executeUpdate("update "+table+" set draws =0");
            statement.executeQuery("commit");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for Insert:" + e);
        }
    }

    public void DeleteColumn(String table, String x) {
        try
        {
            statement.executeUpdate("alter table "+table+" drop column " + x);
        }
        catch (SQLException e)
        {
            System.out.println("Exception for Delete:" + e);
        }
    }

    public int GetTeamNumber( int x)
    {
        int number = 0;
        try
        {
            //System.out.println("Select TNTNUMBER from tournaments where TNID = " + x + "");
            result = statement.executeQuery("Select TNTNUMBER from tournaments where TNID = " + x + "");
            while(result.next())
            {
                number = result.getInt(1);
            }
            //System.out.println("GETNUMBER: " + number);
        }
        catch(SQLException e)
        {
            System.out.println("Exceptrion For get team number: " + e);
        }
        return  number;
    }

    public void InsertTeam(String x, String name, String captain, String contact, String org)
    {
        try
        {
            statement.executeUpdate("Insert into "+x+"_teams values('"+name+"','"+captain+"','"+contact+"','"+org+"')");
            statement.executeUpdate("insert into "+x+"(teamname) values ('"+name+"')");
            statement.executeUpdate("insert into currentteams values('"+name+"')");
            statement.executeQuery("commit");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for insertteam: " +e);
        }
    }

    public void GenerateFixturesLeague()
    {
        try
        {
            ctable = conn.prepareCall("{call gen_fix_league}");
            ctable.execute();
        }
        catch (SQLException e)
        {
            System.out.println("exception for fixturegenleague: " +e);
        }

    }
    public void GenerateFixturesKnockout()
    {
        try
        {
            ctable = conn.prepareCall("{call gen_fix_knockout}");
            ctable.execute();
        }
        catch (SQLException e)
        {
            System.out.println("exception for fixturegenknockout: " +e);
        }

    }
    public int GetFixtureSize()
    {
        int i=0;
        try
        {
            result = statement.executeQuery("select count(*) from fixtures");
            while(result.next())
            {
                i = result.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("exception in fixturesize: " +e);
        }
        //System.out.println("fixnum in database: " +i);
        return i;
    }

    public String[] GetFixture()
    {
        int i = 0;
        String[] team = new String[256];
        try
        {
            result = statement.executeQuery("select * from fixtures");
            while(result.next())
            {
                team[i] = result.getString("team1");
                team[i+1] = result.getString("team2");
                i+=2;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Exception for getfixture: " +e);
        }
        return team;
    }
    public String[] GetAttributes(String table)
    {
        int i=0;
        String[] x = new String [50];
        try
        {
            result = statement.executeQuery("select * from "+table+"_columns");
            while(result.next())
            {
                x[i] = result.getString(1);
               // System.out.println("database: " +x[i]);
                i++;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Exception for GetAttributes: " +e);
        }
        return x;
    }
    public int GetAttributesNumber(String table)
    {
        int x = 0;
        try
        {
            result = statement.executeQuery("select count(*) from "+table+"_columns");
            while(result.next())
            {
                x = result.getInt(1);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Exception for GetAttributes: " +e);
        }
        return x;
    }
    public String[] GetTeamNames(String table)
    {
        int i=0;
        String[] x = new String [16];
        try
        {
            result = statement.executeQuery("select teamname from "+table+"");
            while(result.next())
            {
                x[i] = result.getString(1);
                //System.out.println(" Database Name: " +x[i]);
                i++;
            }
        }
        catch(SQLException e)
        {
            System.out.println("Exception for GetAttributes: " +e);
        }
        return x;
    }
    public int[] GetTeamStandings(String team, String table,int attributeno)
    {
        int i=0;
        String name;
        int[] x = new int[50];
        try{
            //System.out.println(attributeno);
            result = statement.executeQuery("select * from "+table+" where teamname = '"+team+ "'");
            while(result.next())
            {
                name= result.getString(1);
                System.out.print(name+" ");
                for(i=0;i<attributeno+3;i++)
                {
                    x[i]= result.getInt(i+2);
                    System.out.print(x[i]+" ");
                }
                System.out.println("");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Exception for GetTeamStandings" +e);
        }
        return x;
    }
    public String GetTeam1(int x)
    {
        String y = new String();
        try{
            result = statement.executeQuery("select * from fixtures where rownum<"+(x+1)+" minus select * from fixtures where rownum<"+x+"");
            //System.out.println("select * from fixtures where rownum<"+(x+1)+" minus select * from fixtures where rownum<"+x+"");
            while(result.next())
            {
                y = result.getString(1);
               // System.out.println("team1: " +y);
            }
        }
        catch(SQLException e )
        {
            System.out.println("Exception for GetTeam1");

        }
        return y;
    }
    public String GetTeam2(int x)
    {
        String y = new String();
        try{
            result = statement.executeQuery("select * from fixtures where rownum<"+(x+1)+" minus select * from fixtures where rownum<"+x+"");
            //System.out.println("select * from fixtures where rownum<"+(x+1)+" minus select * from fixtures where rownum<"+x+"");
            while(result.next())
            {
                y = result.getString(2);
               // System.out.println("team2: "+y);
            }
        }
        catch(SQLException e )
        {
            System.out.println("Exception for GetTeam1: " +e);
        }
        return y;
    }
    public void SetWInner(String table, String team)
    {
        try{
            statement.executeUpdate("update "+table+" set wins = wins+1 where teamname = '"+team+"'");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for setwinner: " +e);
        }
    }
    public void SetLoser(String table, String team)
    {
        try{
            statement.executeUpdate("update "+table+" set losses = losses+1 where teamname = '"+team+"'");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for setwinner: " +e);
        }
    }
    public void SetDraw(String table, String team)
    {
        try{
            statement.executeUpdate("update "+table+" set draws = draws+1 where teamname = '"+team+"'");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for setwinner: " +e);
        }
    }
    public void UpdateStanding(String table, String column,String team, int value)
    {
        try
        {
            statement.executeUpdate("update "+table+" set "+column+ " = "+column+" + "+value+" where teamname = '"+team+"'");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for UpdateStanding: " +e);
        }
    }
    public ResultSet getall(String tablename) throws SQLException
    {

        try {
             result = statement.executeQuery("Select * from "+tablename+"");
        }
        catch (SQLException e)
        {
            System.out.println("Exception for GetALl: " +e);
        }
        return  result;
    }
}

