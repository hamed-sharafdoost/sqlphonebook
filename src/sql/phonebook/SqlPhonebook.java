package sql.phonebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class SqlPhonebook {

    public static void main(String[] args) throws Exception
    {
        Scanner input = new Scanner(System.in);
        int op;
        int check=0;
        boolean flag = true; //Check for continiung
        int words=65; // For printing alphabet
        int count = 1;
        String url = "jdbc:mysql://localhost:3306/phonebook"; 
        String user = "root";
        String password = "13801380";
        String query1 = "select number,name,familyname,home_number,phone_number,city_code from phonebook order by name,familyname";
        
        do
        {
        System.out.println("--------------------");
        System.out.println("1)" + " see phonebook");
        System.out.println("2)" + "find");
        System.out.println("3)" + "add new record");
        System.out.println("4)" + "delete");
        System.out.println("-------------------");
        System.out.println( "which operate do you want do?");
        op = input.nextInt();
          switch(op)
          {
              case 1:
              {
                  Connection con = DriverManager.getConnection(url,user,password);
                  Statement state = con.createStatement();
                  ResultSet rs = state.executeQuery(query1);
                          
                  {
                      System.out.println("number" +"    "+"name" + "    "+"familyname"+"    "+"home_number" + "    "+"phone_number" + "    "+"city_code");
                      System.out.println("--------------------------------------------------------------------------");
                      while(rs.next())
                      {
                          System.out.println(count+"       "+rs.getString("name")+"       "+rs.getString("familyname")+"       "+rs.getInt("home_number")+"       "+"09"+rs.getInt("phone_number")+"       "+rs.getInt("city_code"));
                          System.out.println("--------------------------------------------------------------------------");
                          count++;
                      }
                         count =1;
                         System.out.println("phonebook");
                         while( (words >=65 && words <=90))
                          {
                          System.out.print("|"+(char)words);
                          words++;
                          }
                      words=65;
                      System.out.println();
                      System.out.println("choose a word for better view :");
                      String query2 = "select * from phonebook where name like 'w%'";
                      String query3= "select count(name) from phonebook where name like 'exist%'";
                      String word = input.next().concat("%");
                      ResultSet rs4 = state.executeQuery(query3.replace("exist%",word));
                      rs4.next();
                      if(rs4.getInt("count(name)") != 0)
                      {
                          ResultSet rs1 = state.executeQuery(query2.replace("w%", word)); 
                          System.out.println("number" +"    "+"name" + "    "+"familyname"+"    "+"home_number" + "    "+"phone_number" + "    "+"city_code");
                          System.out.println("--------------------------------------------------------------------------");
                          while(rs1.next())
                          {
                              System.out.println(count+"       "+
                                      rs1.getString("name")+"       "
                                      +rs1.getString("familyname")+"       "
                                      +rs1.getInt("home_number")+"       "+"09"+rs1.getInt("phone_number")+"       "+rs1.getInt("city_code"));
                              System.out.println("--------------------------------------------------------------------------");
                              count++;
                          }
                          count =1;
                      }
                      else
                      {
                          System.out.println("there is no name that start with " + word.substring(0, word.indexOf("%")));
                      }
                  }
                 
                
              }
              break;
              case 2:
              {
                  String name;
                  String find = "select * from phonebook where name like '%or%'";
                  String exist = "select count(name) from phonebook where name like '%or%'";
                  System.out.println("Search by name(1) or familyname(2) :");
                  int ans = input.nextInt();
                  Connection con = DriverManager.getConnection(url, user, password);
                       Statement state = con.createStatement();
                  {
                      if(ans == 1)
                      {
                         System.out.print("Please insert a name :");
                         name = input.next();
                         ResultSet check1 = state.executeQuery(exist.replace("or",name));
                         check1.next();
                         if(check1.getInt("count(name)") != 0)
                         {
                             ResultSet search = state.executeQuery(find.replace("or",name));
                             System.out.println("name" + "    "+"familyname"+"    "+"home_number" + "    "+"phone_number" + "    "+"city_code");
                             while(search.next())
                             {
                                 System.out.println(search.getString("name")+"    "+search.getString("familyname")+"      "+search.getString("home_number")+"      "+"09"+search.getString("phone_number")+"      "+search.getString("city_code"));
                                 System.out.println("----------------------------------------------------------------------------------------------------------");
                             }
                         }
                      }
                      else if(ans == 2)
                         {
                             exist = exist.replace("name","familyname");
                             find = find.replace("name","familyname");
                             System.out.println("Please insert a familyname :");
                             String family = input.next();
                             ResultSet check2 = state.executeQuery(exist.replace("or",family));
                             check2.next();
                             if(check2.getInt("count(familyname)") != 0)
                             {
                                 ResultSet search2 = state.executeQuery(find.replace("or",family));
                                 System.out.println("name" + "    "+"familyname"+"    "+"home_number" + "    "+"phone_number" + "    "+"city_code");
                                 while(search2.next())
                                 {
                                     System.out.println(search2.getString("name")+"    "+search2.getString("familyname")+"      "+search2.getString("home_number")+"      "+"09"+search2.getString("phone_number")+"      "+search2.getString("city_code"));
                                     System.out.println("----------------------------------------------------------------------------------------------------------");
                                 }
                             }
                             
                         }
                  
                      else
                      {
                                 System.out.println("There is nobody in phonebook with this familyname or name");
                      }
                      
                  }
                  
              }
              break;
              case 3:
              {
                  String query4 = "insert into phonebook(name,familyname,home_number,phone_number,city_code) values('next','fan',hr,ps,ca)";
                  BufferedReader buf = new BufferedReader (new InputStreamReader(System.in));
                  String [] info = new String [5];
                  System.out.println("for show next attribute press enter");
                  System.out.println("\"please press space if you don't know a attribute\"");
                  System.out.println("insert a name :");
                  info[0] = buf.readLine();
                  System.out.println("insert a familyname :");
                  info[1] = buf.readLine();
                  System.out.println("insert a home_number :");
                  info[2] = buf.readLine();
                  if(info[2].length() >8)
                  {
                      System.out.println("home_number includes 8 digits");
                      System.out.println("insert a home_number :");
                      info[2] = buf.readLine();
                  }
                  System.out.println("insert a phone_number :");
                  info[3] = buf.readLine();
                  if(info[3].length() >11)
                  {
                      System.out.println("phone_number includes 11 digits");
                       System.out.println("insert a phone_number :");
                       info[3] = buf.readLine();
                  }
                  System.out.println("insert a city_code :");
                  info[4] = buf.readLine();
                  for(int i=0;i<=4;i++)
                  {
                      if(info[i].isEmpty())
                      {
                          info[i]="null";
                      }
                  }
                  query4=query4.replace("next",info[0]);
                  query4=query4.replace("fan",info[1]);
                  query4=query4.replace("hr",info[2]);
                  query4=query4.replace("ps",info[3].substring(2,11));
                  query4=query4.replace("ca",info[4]);
                  if(info[2].isEmpty() && info[3].isEmpty())
                  {
                      System.out.println("you have to insert home_number or phone_number");
                  }
                  else
                  {
                       Connection con = DriverManager.getConnection(url, user, password);
                       PreparedStatement state = con.prepareStatement(query4);
                               
                       {
                           int insert = state.executeUpdate(query4);
                           if(insert >0)
                           {
                               System.out.println("\uD83D\uDCAF"+"inserted...." + "\uD83D\uDCAF");
                           }
                       }
                  }
              }
              break;
              case 4:
              {
                  System.out.println("delete by name,home_number or phone_number?");
                  String ans = input.next();
                  Connection con = DriverManager.getConnection(url, user, password);
                  {
                  String queryd =null;
                  if(ans.contains("name"))
                  {
                      System.out.print("please enter a name:");
                      String name = input.next();
                      queryd = "delete from phonebook where name = 'bios'";
                      queryd = queryd.replace("bios",name);
                  }
                  PreparedStatement state = con.prepareCall(queryd);
                  int delete  = state.executeUpdate(queryd);
                  if(delete > 0)
                  {
                      System.out.println("it's done bro...");
                  }
                  }
              }
              break;
             
          }
          System.out.println("do you want to continue?");
          String ans = input.next();
          if("no".equals(ans) || "NO".equals(ans))
          {
              flag= false;
              System.out.println("                        "+"HAPPY DAY" + "\uD83D\uDE09");
              
          }
          else
              flag=true;
        }
        while(flag);
        //herher.k
    }
   
}
