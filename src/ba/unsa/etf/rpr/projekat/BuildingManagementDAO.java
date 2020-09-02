package ba.unsa.etf.rpr.projekat;


import java.sql.*;
import java.util.ArrayList;

public class BuildingManagementDAO {

    private static BuildingManagementDAO instance;
    private Connection conn=null;

    private PreparedStatement getBuildingByUser,getUserIdByParameters,getMuncipalitesByUserId,getBuildingsById,getBuildingsIdFromMuncipalites;


    private BuildingManagementDAO(){
        try{

            conn= DriverManager.getConnection("jdbc:sqlite:baza.db");

            getBuildingByUser=conn.prepareStatement("select * from Building where id=?");
            getUserIdByParameters=conn.prepareStatement("select id from User where username=? and password=? ");
            getMuncipalitesByUserId=conn.prepareStatement("select idMuncipality from User_Muncipality where idUser=? ");
            getBuildingsById=conn.prepareStatement("select * from Buildings where id=?");
            getBuildingsIdFromMuncipalites=conn.prepareStatement("select id1 from Building_Muncipality where id2=?");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public static BuildingManagementDAO getInstance(){
        if(instance==null) instance=new BuildingManagementDAO();
        return instance;
    }

    public ArrayList<Building> getBuildingsByUser(String username, String password) {

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
                getBuildingsIdFromMuncipalites.setInt(1,listOfBuildings.get(i));
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
        }
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
}
