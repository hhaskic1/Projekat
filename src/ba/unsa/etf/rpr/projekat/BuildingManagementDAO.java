package ba.unsa.etf.rpr.projekat;


import java.sql.*;
import java.util.ArrayList;

public class BuildingManagementDAO {

    private static BuildingManagementDAO instance;
    private Connection conn=null;

    private PreparedStatement getBuildingByUser,getUserIdByParameters,getMuncipalitesByUserId,getBuildingsById,getBuildingsIdFromMuncipalites, getlAllMuncipalites;
    private PreparedStatement getAllUsers,addMuncipality,getNextMuncipalityID,deleteMuncipality,getUserFromMuncipality,getUserById,addMuncipalityAndUser;
    private PreparedStatement updateMuncipality,getMuncipalityByName,addUser,getNextUser,isThereUser,updateUser,deleteUser,getAllBuildings;
    private PreparedStatement addBuilding,getNextBuilding, updateUserMuncipality, deleteUserMuncipality,deleteBuilding,updateBuilding;

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
            getUserFromMuncipality=conn.prepareStatement("select idUser from User_Muncipality where idMuncipality=?");
            getUserById=conn.prepareStatement("select * from User where id=?");
            addMuncipalityAndUser=conn.prepareStatement("INSERT into User_Muncipality values(?,?)");
            updateMuncipality=conn.prepareStatement("UPDATE Municipality set name=? where id=?");
            getMuncipalityByName=conn.prepareStatement("SELECT id from Municipality where name=? ");
            addUser=conn.prepareStatement("INSERT into User values (?,?,?,?,?,?,?,?)");
            getNextUser=conn.prepareStatement("select Max (id)+1 from User");
            isThereUser=conn.prepareStatement("SELECT * from User where password=? and username=?");
            updateUser=conn.prepareStatement("UPDATE User set first_name=?,last_name=?,phone_number=?,email=?,adress=?,username=?,password=? where id=?");
            deleteUser=conn.prepareStatement("DELETE from User where id=?");
            getAllBuildings=conn.prepareStatement("select * from Building");
            addBuilding=conn.prepareStatement("INSERT into Building values(?,?,?,?,?)");
            getNextBuilding=conn.prepareStatement("select Max(id)+1 from Building");
            updateUserMuncipality= conn.prepareStatement("update User_Muncipality set idUser = ? where idMuncipality = ?");
            deleteUserMuncipality =conn.prepareStatement("delete from User_Muncipality where idMuncipality = ?");
            deleteBuilding=conn.prepareStatement("delete from Building where id=?");
            updateBuilding=conn.prepareStatement("UPDATE Building set adress=?,numberOfFlats=?,type=?,garage=? where id=?");

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
                u.setId(rs.getInt(1));
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
            deleteUserMuncipality.setInt(1,municipality.getIdMuncipality());
            deleteUserMuncipality.executeUpdate();

            deleteMuncipality.setInt(1,municipality.getIdMuncipality());
            deleteMuncipality.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserFromMuncipality(Municipality municipality){
        try{
            getUserFromMuncipality.setInt(1,municipality.getIdMuncipality());
            ResultSet rs2=getUserFromMuncipality.executeQuery();
            int id=rs2.getInt(1);
            getUserById.setInt(1,id);
            ResultSet rs=getUserById.executeQuery();



                User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
               return u;

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void AddMuncipality(String name,User user){
        try{
            ResultSet rs=getNextMuncipalityID.executeQuery();

            int id=rs.getInt(1);
            addMuncipality.setInt(1,id);
            addMuncipality.setString(2,name);
            addMuncipality.setInt(3,0);
            addMuncipality.executeUpdate();

            addMuncipalityAndUser.setInt(1,getUserIdByParameters(user.getUsername(),user.getPassword()));

            addMuncipalityAndUser.setInt(2,id);
            addMuncipalityAndUser.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateMuncipality(Integer id, String name, User user){

        try{
            /*getMuncipalityByName.setString(1,id);
            ResultSet rs=getMuncipalityByName.executeQuery();
            int identity=rs.getInt(1);*/
            //modifikovanje samo munci
            updateMuncipality.setString(1,name);
            updateMuncipality.setInt(2,id);
            updateMuncipality.executeUpdate();


            //modifikovanje user-munci
            updateUserMuncipality.setInt(1,user.getId());
            updateUserMuncipality.setInt(2,id);
            updateUserMuncipality.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();

        }


    }

    public void addUser(User user){

        try{
            ResultSet rs=getNextUser.executeQuery();
            int id=rs.getInt(1);

            addUser.setInt(1,id);
            addUser.setString(2,user.getFirst_name());
            addUser.setString(3,user.getLast_name());
            addUser.setString(4,user.getPhone_number());
            addUser.setString(5,user.getEmail());
            addUser.setString(6,user.getAdress());
            addUser.setString(7,user.getUsername());
            addUser.setString(8,user.getPassword());

            addUser.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public Boolean isThereUser(String username,String password){
        try{

            isThereUser.setString(1,password);
            isThereUser.setString(2,username);

            ResultSet rs=isThereUser.executeQuery();

            if(rs.next()) return true;
            return false;


        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public void updateUser(User user){

        try {
            updateUser.setString(1,user.getFirst_name());
            updateUser.setString(2,user.getLast_name());
            updateUser.setString(3,user.getPhone_number());
            updateUser.setString(4,user.getEmail());
            updateUser.setString(5,user.getAdress());
            updateUser.setString(6,user.getUsername());
            updateUser.setString(7,user.getPassword());
            updateUser.setInt(8,user.getId());

            updateUser.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(User user){
        try{
            deleteUser.setInt(1,user.getId());
            deleteUser.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Building> getAllBuildings(){
        try {
            ResultSet rs=getAllBuildings.executeQuery();
            ArrayList<Building> buildings = new ArrayList<>();
            while(rs.next()){
                Building b=new Building(rs.getInt(1),rs.getString(2),rs.getString(3));
                if(rs.getInt(4) == 1)b.setType(BuildingType.NewBuilding);
                else if(rs.getInt(4) == 2)b.setType(BuildingType.OldBuilding);
                else b.setType(BuildingType.Mall);
                buildings.add(b);
            }
            return buildings;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addBuilding(Building building){

        try {
            ResultSet rs=getNextBuilding.executeQuery();
            int id=rs.getInt(1);
            if(id==0) id++;

            addBuilding.setInt(1,id);
            addBuilding.setString(2,building.getAdress());
            addBuilding.setString(3,building.getNumberOfFlats());
            addBuilding.setInt(4,building.getType().getLevel());
            addBuilding.setInt(5,building.getGarage());

            addBuilding.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBuilding(Building building){
        try{
            deleteBuilding.setInt(1,building.getId());
            deleteBuilding.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateBuilding(Building building){
        try {
            updateBuilding.setString(1,building.getAdress());
            updateBuilding.setString(2,building.getNumberOfFlats());

                if(building.getType()==BuildingType.Mall){
                    updateBuilding.setInt(3,3);

                }else if(building.getType()==BuildingType.NewBuilding){
                    updateBuilding.setInt(3,1);

                }else {
                    updateBuilding.setInt(3,2);

                }
            updateBuilding.setInt(4,building.getGarage());
            updateBuilding.setInt(5,building.getId());

            updateBuilding.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
