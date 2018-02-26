import java.sql.*;

public class store {
    public void store()
    {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:C:/Users/sanjeev halyal/IdeaProjects/GlassdoorScrapper/Glassdoordb", "sa", "");
            Statement st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from Main.TEST");
            while (rs.next()) {
                System.out.println(rs.getString("NAME"));
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkcompany(int empid,String companyname)
    {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:C:/Users/sanjeev halyal/IdeaProjects/GlassdoorScrapper/Glassdoordb", "sa", "");
            Statement st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from Main.COMPANY WHERE EMPID='"+empid+"' AND COMPANYNAME='"+companyname+"'");
            while (rs.next()) {
                if (rs.getInt("EMPID")==empid && rs.getString("companyname").contains(companyname))
                {
                    System.out.println("Company exists in db");
                    return true;
                }
                else
                {
                    System.out.println("Company does not exist in db");
                    return false;
                }
            }
            conn.close();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inputcompanyvalue(int empid,String  companyname ,String info,String linkedinlink)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:C:/Users/sanjeev halyal/IdeaProjects/GlassdoorScrapper/Glassdoordb", "sa", "");
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Main.COMPANY (EMPID,COMPANYNAME,INFO,LINKEDIN) VALUES ("+ empid +",'"+companyname+"','"+info+"','"+linkedinlink+"')");
            stmt.close();
            connection.close();
            return true;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean checkjob(String jobid)
    {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:C:/Users/sanjeev halyal/IdeaProjects/GlassdoorScrapper/Glassdoordb", "sa", "");
            Statement st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery("select * from Main.JOB WHERE JOBID='"+jobid+"'");
            while (rs.next()) {
                if (rs.getString("JOBID").contains(jobid))
                {
                    System.out.println("Job exists in db");
                    return true;
                }
                else
                {
                    System.out.println("Job does not exist in db");
                    return false;
                }
            }
            conn.close();

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean inputjobvalue(String jobid,String jobname,String website,String applylink,
                                 String companyname,String companyrating,String location,
                                 String desc,int recom,int approval,
                                 int empid)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:C:/Users/sanjeev halyal/IdeaProjects/GlassdoorScrapper/Glassdoordb", "sa", "");
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Main.JOB (JOBID,JOBNAME,WEBSITE,APPLYLINK,COMPANYNAME,COMPANYRATING,LOCATION,RECOM,APPROVAL,EMPID,DESC) VALUES ('"+ jobid +"','"+jobname+"','"+website+"','"+applylink+"','"+companyname+"','"+companyrating+"','"+location+"',"+recom+","+approval+","+empid+",'"+desc.replace("'","").replace("\"","")+"');");
            stmt.close();
            connection.close();
            System.out.println("INSERT INTO Main.JOB (JOBID,JOBNAME,WEBSITE,APPLYLINK,COMPANYNAME,COMPANYRATING,LOCATION,RECOM,APPROVAL,EMPID,DESC) VALUES ('"+ jobid +"','"+jobname+"','"+website+"','"+applylink+"','"+companyname+"','"+companyrating+"','"+location+"',"+recom+","+approval+","+empid+",'"+desc+"')");
            return true;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
