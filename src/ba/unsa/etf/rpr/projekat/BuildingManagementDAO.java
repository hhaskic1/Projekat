package ba.unsa.etf.rpr.projekat;


import java.sql.*;
import java.util.ArrayList;

public class BuildingManagementDAO {

    private static BuildingManagementDAO instance;
    private Connection conn=null;

    private PreparedStatement getBuildingByUser,getUserIdByParameters,getMuncipalitesByUserId,getBuildingsById,getBuildingsIdFromMuncipalites, getlAllMuncipalites;
    private PreparedStatement getAllUsers,addMuncipality,getNextMuncipalityID,deleteMuncipality;


    private BuildingManagementDAO(){
        try{

            conn= DriverManager.getConnection("jdbc:sqlite:baza.db");

            getBuildingByUser=conn.prepareStatement("select * from Building where id=?");
            getUserIdByParameters=conn.prepareStatement("select id from User where username=? and password=? ");
            getMuncipalitesByUserId=conn.prepareStatement("select idMuncipality from User_Muncipality where idUser=? ");
            getBuildingsById=conn.prepareStatement("select * from Building where id=?");
            getBuildingsIdFromMuncipalites=conn.prepareStatement("select id1 from Building_Muncipality where id2=?");
            getlAllMuncipalites = conn.prepareStatement("select * from Municipality");
            getAllUsers=conn.prepareStatement("select * from User");
            addMuncipality=conn.prepareStatement("insert into Municipality values(?,?,?)");
            getNextMuncipalityID=conn.prepareStatement("select Max (id)+1 from Municipality ");
            deleteMuncipality=conn.prepareStatement("DELETE from Municipality where Municipality.id=?");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static BuildingManagementDAO getInstance(){
        if(instance==null) instance=new BuildingManagementDAO();
        return instance;
    }

    public ArrayList<Municipality> getBuildingsByUser(String username, String password) {

        try{
            ArrayList<Municipality> list = new ArrayList<>();
            ResultSet resultSet = getlAllMuncipalites.executeQuery();
            while(resultSet.next()){
                Municipality municipality = new Municipality(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3));
                list.add(municipality);
            }
            return list;

        }catch(SQLException w){
            w.printStackTrace();
            return null;
        }
        /*
        try {
            int idUser=getUserIdByParameters(username,password);
            getMuncipalitesByUserId.setInt(1,idUser);
            ResultSet listOfMuncipalites=getMuncipalitesByUserId.executeQuery();

            ArrayList<Integer>MuncipalitiesId=new ArrayList<>();
            while(listOfMuncipalites.next()){
                MuncipalitiesId.add(listOfMuncipalites.getInt(1));
            }

            ArrayList<Integer> listOfBuildings=new ArrayList<>();
            for(int i=0;i<MuncipalitiesId.size();i++){
                getBuildingsIdFromMuncipalites.setInt(1,MuncipalitiesId.get(i));
                ResultSet list=getBuildingsIdFromMuncipalites.executeQuery();
                while (list.next()){
                    listOfBuildings.add(list.getInt(1));
                }
            }
            ArrayList<Building> back=new ArrayList<>();
            for(int i=0;i<listOfBuildings.size();i++){
                getBuildingsById.setInt(1,listOfBuildings.get(i));
                ResultSet listlist=getBuildingsById.executeQuery();
                Building building=new Building(listlist.getInt(1),listlist.getString(2),listlist.getString(3));
                back.add(building);
            }
            return back;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    public int getUserIdByParameters(String username,String password){
        try{
            getUserIdByParameters.setString(1,username);
            getUserIdByParameters.setString(2,password);
            ResultSet rs=getUserIdByParameters.executeQuery();
            return rs.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<User> getAllUsers(){
        try{
            ArrayList<User>users=new ArrayList<>();
            ResultSet rs=getAllUsers.executeQuery();
            while(rs.next()){
                User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
                users.add(u);
            }
        return users;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void AddMuncipality(String name){
        try{
            ResultSet rs=getNextMuncipalityID.executeQuery();
            int id=rs.getInt(1);
            addMuncipality.setInt(1,id);
            addMuncipality.setString(2,name);
            addMuncipality.setInt(3,0);
            addMuncipality.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Municipality>getAllMuncipality(){
        return getBuildingsByUser("","");
    }

    public void deleteMuncipality(Municipality municipality){
        try{
            deleteMuncipality.setInt(1,municipality.getIdMuncipality());
            deleteMuncipality.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
