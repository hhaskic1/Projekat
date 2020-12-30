package ba.unsa.etf.rpr.projekat;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class BuildingManagementDAO {

    private static BuildingManagementDAO instance;
    private Connection conn=null;

    private PreparedStatement getBuildingByUser,getUserIdByParameters,getMuncipalitesByUserId,getBuildingsById,getBuildingsIdFromMuncipalites, getlAllMuncipalites;
    private PreparedStatement getAllUsers,addMuncipality,getNextMuncipalityID,deleteMuncipality,getUserFromMuncipality,getUserById,addMuncipalityAndUser,updateJob;
    private PreparedStatement updateMuncipality,getMuncipalityByName,addUser,getNextUser,isThereUser,updateUser,deleteUser,getAllBuildings,getAllJobsForBuilding;
    private PreparedStatement addBuilding,getNextBuilding, updateUserMuncipality, deleteUserMuncipality,deleteBuilding,updateBuilding,getAllUsersExeceptGuests;
    private PreparedStatement getBuildingByAdress,getNextJobId,addJobsToBuilding,addJob,isThereUserByParametersExceptUser,checkUser,getAllBuildingsBYGuest;
    private PreparedStatement isThereMuncipality,isThereBuildingOnThatAdress,isThereBuildingOnThatAdressUpdate, getUserByParameters, getMuncipalityForUser;
    private PreparedStatement addBuildingUser,updateMuncipalityBuilding,deleteMuncipalityBuilding,deleteBuildingMuncipality,updateMuncipality2,getJob;
    private PreparedStatement getMuncipalityFromBM, getAllBuildingsFromUser, addUserToMunicipality, deleteUserFromMunicipality, isThereUserInMunicipality;
    private PreparedStatement deleteUserFromMunicipality2,getIdMunicipalityFromUser_Municipality,getIdMunicipalityFromBuilding_Municipality,getAllGuests;

    private BuildingManagementDAO(){
        try{
            conn= DriverManager.getConnection("jdbc:sqlite:baza.db");
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            getBuildingByUser=conn.prepareStatement("select * from Building where id=?");
        }catch (SQLException e){
            regenerisiBazu();
            try{
                getBuildingByUser=conn.prepareStatement("select * from Building where id=?");
            }catch (SQLException e1){
                e1.printStackTrace();
            }

        }

        try{



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
            updateMuncipality2=conn.prepareStatement("UPDATE Municipality set name=?, numberBuildings = ? where id=?");
            getMuncipalityByName=conn.prepareStatement("SELECT id from Municipality where name=? ");
            addUser=conn.prepareStatement("INSERT into User values (?,?,?,?,?,?,?,?,?)");
            getNextUser=conn.prepareStatement("select Max (id)+1 from User");
            isThereUser=conn.prepareStatement("SELECT * from User where username=?");
            updateUser=conn.prepareStatement("UPDATE User set first_name=?,last_name=?,phone_number=?,email=?,adress=?,username=?,password=?, type=? where id=?");
            deleteUser=conn.prepareStatement("DELETE from User where id=?");
            getAllBuildings=conn.prepareStatement("select * from Building");
            addBuilding=conn.prepareStatement("INSERT into Building values(?,?,?,?,?,?,?,?,?)");
            getNextBuilding=conn.prepareStatement("select Max(id)+1 from Building");
            updateUserMuncipality= conn.prepareStatement("update User_Muncipality set idUser = ? where idMuncipality = ?");
            deleteUserMuncipality =conn.prepareStatement("delete from User_Muncipality where idMuncipality = ?");
            deleteBuilding=conn.prepareStatement("delete from Building where id=?");
            updateBuilding=conn.prepareStatement("UPDATE Building set adress=?,numberOfFlats=?,type=?,garage=?, numberOfElevators=?, numberOfFloors=?,yearOfBuilt=?,guest_id=? where id=?");
            getBuildingByAdress=conn.prepareStatement("SELECT * from Building where adress=?");
            getNextJobId=conn.prepareStatement("select Max (id)+1 from Jobs");
            addJob=conn.prepareStatement("insert into Jobs values (?,?,?,?,?)");
            addJobsToBuilding=conn.prepareStatement("insert into Building_Jobs values (?,?)");
            isThereUserByParametersExceptUser=conn.prepareStatement("SELECT * from User where username=? and username!=?");
            checkUser=conn.prepareStatement("select * from User where password=? and username=?");
            isThereMuncipality=conn.prepareStatement("select * from Municipality where name=?");
            isThereBuildingOnThatAdress=conn.prepareStatement("select * from Building where adress=?");
            isThereBuildingOnThatAdressUpdate=conn.prepareStatement("select * from Building where adress=? and adress!=?");
            getUserByParameters = conn.prepareStatement("select * from User where username = ? and password = ?");
            getMuncipalityForUser = conn.prepareStatement("select Municipality.id, Municipality.name, Municipality.numberBuildings" +
                                                                " from Municipality, User_Muncipality " +
                    "                                             where User_Muncipality.idMuncipality = Municipality.id and " +
                    "                                                   User_Muncipality.idUser = ?");
            addBuildingUser = conn.prepareStatement("insert into Building_Muncipality values (?,?)");
            updateMuncipalityBuilding = conn.prepareStatement("update Building_Muncipality set id2 = ? where id1 = ?");
            deleteMuncipalityBuilding = conn.prepareStatement("delete from Building_Muncipality where id1 = ?");
            deleteBuildingMuncipality = conn.prepareStatement("delete from Building_Muncipality where id2 = ?");
            getMuncipalityFromBM = conn.prepareStatement("select Municipality.id, Municipality.name, Municipality.numberBuildings from Municipality, Building_Muncipality where Building_Muncipality.id2 = Municipality.id and Building_Muncipality.id1 = ?");
            getAllBuildingsFromUser = conn.prepareStatement("select Building.id, Building.adress, Building.numberOfFlats, Building.type, Building.garage,Building.numberOfElevators,Building.numberOfFloors, Building.yearOfBuilt, Building.guest_id from Building, User_Muncipality,Building_Muncipality,User where User_Muncipality.idUser = User.id and User_Muncipality.idMuncipality = Building_Muncipality.id2 and Building_Muncipality.id1 = Building.id and User.id = ?");
            addUserToMunicipality = conn.prepareStatement("insert into User_Muncipality values (?,?)");
            deleteUserFromMunicipality = conn.prepareStatement("delete from User_Muncipality where idUser = ? and idMuncipality != ?");
            deleteUserFromMunicipality2 = conn.prepareStatement("delete from User_Muncipality where idUser = ? and idMuncipality = ?");
            isThereUserInMunicipality = conn.prepareStatement("select  * from User_Muncipality where idUser = ?");
            getIdMunicipalityFromUser_Municipality = conn.prepareStatement("select idMuncipality from User_Muncipality where idUser=?");
            getIdMunicipalityFromBuilding_Municipality = conn.prepareStatement("select id2 from Building_Muncipality where id1=?");
            getAllGuests = conn.prepareStatement("select * from User where type = 3");
            getAllBuildingsBYGuest = conn.prepareStatement("select * from Building where guest_id = ?");
            getAllUsersExeceptGuests = conn.prepareStatement("select * from User where type != 3");
            getAllJobsForBuilding = conn.prepareStatement("select job_id from Building_Jobs where buildingID = ?");
            getJob = conn.prepareStatement("select * from Jobs where id=?");
            updateJob = conn.prepareStatement("update Jobs set endDate = ? where id=?");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Metoda za potrebe testova, vraća bazu podataka u polazno stanje
    public void vratiBazuNaDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM User");
        stmt.executeUpdate("DELETE FROM Municipality");
        stmt.executeUpdate("DELETE FROM User_Muncipality");
        stmt.executeUpdate("DELETE FROM Building_Muncipality");
        stmt.executeUpdate("DELETE FROM Building");
        stmt.executeUpdate("DELETE FROM Jobs");
        stmt.executeUpdate("DELETE FROM Building_Jobs");



        // Regeneriši bazu neće ponovo kreirati tabele jer u .sql datoteci stoji
        // CREATE TABLE IF NOT EXISTS
        // Ali će ponovo napuniti default podacima
        regenerisiBazu();
    }



    public Connection getConnection() {
        return conn;
    }

    public static BuildingManagementDAO getInstance(){
        if(instance==null) instance=new BuildingManagementDAO();
        return instance;
    }
    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

                if(rs.getInt(9) == 1)   u.setType(TypeOfUser.ADMINISTRATOR);
                else if(rs.getInt(9) == 2) u.setType(TypeOfUser.USER);
                else if(rs.getInt(9) == 3) u.setType(TypeOfUser.GUEST);

                users.add(u);
            }
        return users;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<User> getAllUsersExeceptGuests(){
        try{
            ArrayList<User>users=new ArrayList<>();
            ResultSet rs=getAllUsersExeceptGuests.executeQuery();
            while(rs.next()){
                User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
                u.setId(rs.getInt(1));

                if(rs.getInt(9) == 1)   u.setType(TypeOfUser.ADMINISTRATOR);
                else if(rs.getInt(9) == 2) u.setType(TypeOfUser.USER);
                else if(rs.getInt(9) == 3) u.setType(TypeOfUser.GUEST);

                users.add(u);
            }
            return users;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<User> getAllGuests(){
        try{
            ArrayList<User>users=new ArrayList<>();
            ResultSet rs=getAllGuests.executeQuery();
            while(rs.next()){
                User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
                u.setId(rs.getInt(1));
                u.setType(TypeOfUser.GUEST);

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

            deleteBuildingMuncipality.setInt(1,municipality.getIdMuncipality());
            deleteBuildingMuncipality.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserFromMuncipality(Municipality municipality){
        try{
            getUserFromMuncipality.setInt(1,municipality.getIdMuncipality());
            ResultSet rs2=getUserFromMuncipality.executeQuery();
            if(rs2.next()){
            int id=rs2.getInt(1);
            getUserById.setInt(1,id);
            ResultSet rs=getUserById.executeQuery();



            User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));

            u.setId(id);
            if(rs.getInt(9) == 1)   u.setType(TypeOfUser.ADMINISTRATOR);
            else if(rs.getInt(9) == 2) u.setType(TypeOfUser.USER);
            else if(rs.getInt(9) == 3) u.setType(TypeOfUser.GUEST);


            return u;}
            else
                return null;

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void AddMuncipality(String name,User user){
        try{
            ResultSet rs=getNextMuncipalityID.executeQuery();

            int id=rs.getInt(1);
            if(id==0) id++;
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

    public void updateMuncipality(Integer id, String name){

        try{

            //modifikovanje samo munci modifi naziv
            updateMuncipality.setString(1,name);
            updateMuncipality.setInt(2,id);
            updateMuncipality.executeUpdate();

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

            if(user.getType() == TypeOfUser.ADMINISTRATOR)   addUser.setInt(9,1);
            else if(user.getType() == TypeOfUser.USER)   addUser.setInt(9,2);
            else if(user.getType() == TypeOfUser.GUEST)   addUser.setInt(9,3);


            addUser.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }


    }


    public Boolean isThereMuncipality(String name){
        try{
            isThereMuncipality.setString(1,name);

            ResultSet rs=isThereMuncipality.executeQuery();

            if(rs.next()) return true;
            return false;


        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }


    public Boolean isThereBuildingOnThatAdress(String adress){
        try{

            isThereBuildingOnThatAdress.setString(1,adress);

            ResultSet rs=isThereBuildingOnThatAdress.executeQuery();

            if(rs.next()) return true;
            return false;


        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }


    public Boolean isThereBuildingOnThatAdressUpdate(String adress1,String oldAdress){
        try{

            isThereBuildingOnThatAdressUpdate.setString(1,adress1);
            isThereBuildingOnThatAdressUpdate.setString(2,oldAdress);

            ResultSet rs=isThereBuildingOnThatAdressUpdate.executeQuery();

            if(rs.next()) return true;
            return false;


        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }


    public Boolean isThereUser(String username,String password){
        try{

           // isThereUser.setString(1,password);
            isThereUser.setString(1,username);

            ResultSet rs=isThereUser.executeQuery();

            if(rs.next()) return true;
            return false;


        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }


    public Boolean checkUser(String username,String password){
        try{

            checkUser.setString(1,password);
            checkUser.setString(2,username);

            ResultSet rs=checkUser.executeQuery();

            if(rs.next()) return true;
            return false;


        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public Boolean isThereUserByParametersExceptUser(String username,String password,String  oldUsername,String oldPassword){
        try{

            //isThereUserByParametersExceptUser.setString(1,password);
            isThereUserByParametersExceptUser.setString(1,username);
            //isThereUserByParametersExceptUser.setString(3,oldPassword);
            isThereUserByParametersExceptUser.setString(2,oldUsername);


            ResultSet rs=isThereUserByParametersExceptUser.executeQuery();

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



            if(user.getType() == TypeOfUser.ADMINISTRATOR)   updateUser.setInt(8,1);
            else if(user.getType() == TypeOfUser.USER)   updateUser.setInt(8,2);
            else if(user.getType() == TypeOfUser.GUEST)   updateUser.setInt(8,3);

            updateUser.setInt(9,user.getId());


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
                Building b=new Building(rs.getInt(1),rs.getString(2),rs.getInt(3), rs.getInt(5),rs.getInt(9),rs.getInt(7),rs.getInt(6),rs.getInt(8),null);
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

    public void addBuilding(Building building, Municipality municipality){

        try {
            ResultSet rs=getNextBuilding.executeQuery();
            int id=rs.getInt(1);
            if(id==0) id++;

            addBuilding.setInt(1,id);
            addBuilding.setString(2,building.getAdress());
            addBuilding.setInt(3,building.getNumberOfFlats());
            addBuilding.setInt(4,building.getType().getLevel());
            addBuilding.setInt(5,building.getGarage());
            addBuilding.setInt(6,building.getNumberOfElevators());
            addBuilding.setInt(7,building.getNumberOfFloors());
            addBuilding.setInt(8,building.getYearOfBuilt());
            addBuilding.setInt(9,building.getGuestId());

            addBuilding.executeUpdate();

            addBuildingUser.setInt(1,building.getId());
            addBuildingUser.setInt(2,municipality.getIdMuncipality());
            addBuildingUser.executeUpdate();

            updateMuncipality2.setString(1,municipality.getNameOfMuncipality());
            updateMuncipality2.setInt(2,municipality.getNumberOfBuildings() + 1);
            updateMuncipality2.setInt(3,municipality.getIdMuncipality());

            updateMuncipality2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBuilding(Building building){
        try{




            getMuncipalityFromBM.setInt(1,building.getId());
            ResultSet resultSet = getMuncipalityFromBM.executeQuery();
            Municipality municipality = new Municipality(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3));

            updateMuncipality2.setString(1, municipality.getNameOfMuncipality());
            updateMuncipality2.setInt(2,municipality.getNumberOfBuildings() - 1);
            updateMuncipality2.setInt(3, municipality.getIdMuncipality());

            updateMuncipality2.executeUpdate();

            deleteMuncipalityBuilding.setInt(1,building.getId());
            deleteMuncipalityBuilding.executeUpdate();

            deleteBuilding.setInt(1,building.getId());
            deleteBuilding.executeUpdate();



        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateBuilding(Building building){
        try {
            updateBuilding.setString(1,building.getAdress());
            updateBuilding.setInt(2,building.getNumberOfFlats());

                if(building.getType()==BuildingType.Mall){
                    updateBuilding.setInt(3,3);

                }else if(building.getType()==BuildingType.NewBuilding){
                    updateBuilding.setInt(3,1);

                }else {
                    updateBuilding.setInt(3,2);

                }
            updateBuilding.setInt(4,building.getGarage());
            updateBuilding.setInt(5,building.getNumberOfElevators());
            updateBuilding.setInt(6,building.getNumberOfFloors());
            updateBuilding.setInt(7,building.getYearOfBuilt());
            updateBuilding.setInt(8,building.getGuestId());
            updateBuilding.setInt(9,building.getId());


            updateBuilding.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Building getBuildingByAdress(String adress){
        try{
            getBuildingByAdress.setString(1,adress);
            ResultSet rs= getBuildingByAdress.executeQuery();
            Building building=new Building(rs.getInt(1),rs.getString(2),rs.getInt(3));
            building.setGarage(rs.getInt(5));
            if(rs.getInt(4)==1){
                building.setType(BuildingType.NewBuilding);
            }else if(rs.getInt(4)==2) {
                building.setType(BuildingType.OldBuilding);
            }else {
                building.setType(BuildingType.Mall);
            }
            return building;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }



    public int getNextJobId(){

        try{
            ResultSet rs=getNextJobId.executeQuery();
            int id=rs.getInt(1);
            if(id==0) id++;
            return id;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }


    }

    public void addJobsToBuilding(Jobs jobs,Building building) {
        try {

            addJob.setInt(1, jobs.getId());
            addJob.setString(2,jobs.getName());
            addJob.setString(3, String.valueOf(jobs.getBeginingDate()));
            if(jobs.getEndDate() != null)
            addJob.setString(4, String.valueOf(jobs.getEndDate()));
            else addJob.setString(4, "");
            addJob.setString(5,jobs.getContractor());
            addJob.executeUpdate();

            addJobsToBuilding.setInt(1,building.getId());
            addJobsToBuilding.setInt(2,jobs.getId());
            addJobsToBuilding.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public User getUserByParameters(String username, String password){
        try {

            getUserByParameters.setString(1, username);
            getUserByParameters.setString(2, password);
            ResultSet rs = getUserByParameters.executeQuery();

            User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));

            u.setId(rs.getInt(1));
            if(rs.getInt(9) == 1)   u.setType(TypeOfUser.ADMINISTRATOR);
            else if(rs.getInt(9) == 2) u.setType(TypeOfUser.USER);
            else if(rs.getInt(9) == 3) u.setType(TypeOfUser.GUEST);

            return u;


        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }


    public Municipality getMuncipalityForUser(User user){
        try {
            getMuncipalityForUser.setInt(1,user.getId());

            ResultSet rs = getMuncipalityForUser.executeQuery();

            Municipality municipality = new Municipality(rs.getInt(1),rs.getString(2),rs.getInt(3));
            return municipality;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public int getNextBuildingId(){
        try {
            ResultSet resultSet = getNextBuilding.executeQuery();
            if(resultSet.getInt(1) == 0) return 1;
            return resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Building> getAllBuildingsFromUser(User user){
        try {
            getAllBuildingsFromUser.setInt(1,user.getId());
            ResultSet rs=getAllBuildingsFromUser.executeQuery();
            ArrayList<Building> buildings = new ArrayList<>();
            while(rs.next()){
                Building b=new Building(rs.getInt(1),rs.getString(2),rs.getInt(3), rs.getInt(5),rs.getInt(9),rs.getInt(7),rs.getInt(6),rs.getInt(8),null);
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

    public ArrayList<User> getAllManagersInMunicipality(Municipality municipality){
        try{
            ArrayList<User> users=new ArrayList<>();
            if(municipality != null){
                getUserFromMuncipality.setInt(1,municipality.getIdMuncipality());
                ResultSet rs2=getUserFromMuncipality.executeQuery();
                while(rs2.next()){
                    User u = getUserByID(rs2.getInt(1));
                    /*getUserById.setInt(1,rs2.getInt(1));
                    ResultSet rs = getUserById.executeQuery();
                    /*
                    User u=new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
                    u.setId(rs.getInt(1));


                    if(rs.getInt(9) == 1)   u.setType(TypeOfUser.ADMINISTRATOR);
                    else if(rs.getInt(9) == 2) u.setType(TypeOfUser.USER);
                    else if(rs.getInt(9) == 3) u.setType(TypeOfUser.GUEST);*/

                    users.add(u);
                }
            }
            else users = null;

            return users;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void addUserToMunicipality(User user, Municipality municipality){
        try {

            addUserToMunicipality.setInt(1, user.getId());
            addUserToMunicipality.setInt(2,municipality.getIdMuncipality());
            addUserToMunicipality.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void deleteUserFromMunicipality(User user, Municipality municipality){
        try {

            isThereUserInMunicipality.setInt(1,user.getId());
            ResultSet resultSet = isThereUserInMunicipality.executeQuery();

            if(resultSet.next()){
                deleteUserFromMunicipality.setInt(1,user.getId());
                deleteUserFromMunicipality.setInt(2,municipality.getIdMuncipality());
                deleteUserFromMunicipality.executeUpdate();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteUserFromMunicipality2(User user, Municipality municipality) {
        try {

            deleteUserFromMunicipality2.setInt(1, user.getId());
            deleteUserFromMunicipality2.setInt(2, municipality.getIdMuncipality());
            deleteUserFromMunicipality2.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public int getIdMunicipalityFromUser_Municipality(User user){
            try {
                getIdMunicipalityFromUser_Municipality.setInt(1,user.getId());
                ResultSet rs=getIdMunicipalityFromUser_Municipality.executeQuery();
                if(rs.next())
                return rs.getInt(1);
                    return -1;
            }catch (SQLException e){
                e.printStackTrace();
                return -1;
            }

        }

        public int getIdMunicipalityFromBuilding_Municipality(Building building){
            try {
                getIdMunicipalityFromBuilding_Municipality.setInt(1,building.getId());
                ResultSet rs=getIdMunicipalityFromBuilding_Municipality.executeQuery();
                if(rs.next())
                return rs.getInt(1);
                    return -1;
            }catch (SQLException e){
                e.printStackTrace();
                return -1;
            }
        }

        public String  getNameOfUserById(int id){
            try {
                getUserById.setInt(1,id);
                ResultSet rs=getUserById.executeQuery();
                if(rs.next())
                    return rs.getString(2) +" "+ rs.getString(3);
                return "";
            }catch (SQLException e){
                e.printStackTrace();
                return "";
            }
        }

        public User getUserByID(int id){
            try {
                getUserById.setInt(1,id);
                ResultSet rs=getUserById.executeQuery();
                if(rs.next())
                {
                    User u = new User(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8));
                    u.setId(id);
                    if(rs.getInt(9) == 1)   u.setType(TypeOfUser.ADMINISTRATOR);
                    else if(rs.getInt(9) == 2) u.setType(TypeOfUser.USER);
                    else if(rs.getInt(9) == 3) u.setType(TypeOfUser.GUEST);
                    return u;
                }
                return null;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }
        public ArrayList<Building> getAllBuildingsBYGuest(User user){
            try {
                getAllBuildingsBYGuest.setInt(1,user.getId());
                ResultSet rs=getAllBuildingsBYGuest.executeQuery();
                ArrayList<Building> buildings = new ArrayList<>();
                while(rs.next()){
                    Building b=new Building(rs.getInt(1),rs.getString(2),rs.getInt(3), rs.getInt(5),rs.getInt(9),rs.getInt(7),rs.getInt(6),rs.getInt(8),null);
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

    public ArrayList<Jobs> getAllJobsForBuilding(Building building){
        try {
            getAllJobsForBuilding.setInt(1,building.getId());
            ResultSet rs=getAllJobsForBuilding.executeQuery();
            ArrayList<Jobs> jobs = new ArrayList<>();
            while(rs.next()){
                getJob.setInt(1,rs.getInt(1));
                ResultSet rs2 = getJob.executeQuery();
                Jobs j = new Jobs(rs2.getInt(1),rs2.getString(2), LocalDate.parse(rs2.getString(3)),null,rs2.getString(5));
                String vrijemeKraja = rs2.getString(4);
                if(vrijemeKraja.length() != 0 ) {
                    j.setEndDate(LocalDate.parse(rs2.getString(4)));
                }
                jobs.add(j);
            }
            return jobs;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void updateJob(Jobs jobs){
        try {
            updateJob.setString(1, jobs.getEndDate().toString());
            updateJob.setInt(2, jobs.getId());

            updateJob.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
