package com.tvnsoftware.drcare.model.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.SerializedName;
import com.tvnsoftware.drcare.Application;
import com.tvnsoftware.drcare.R;

import java.util.ArrayList;
import java.util.List;

import static com.tvnsoftware.drcare.Utils.Constants.USER_CHILD;
import static com.tvnsoftware.drcare.activity.LoginActivity.dbRefer;

/**
 * Created by Thieusike on 7/23/2017.
 */

public class User {

    private static final String TAG = User.class.getSimpleName();

    /*@SerializedName("USER_CODE")
    private String Key;
    @SerializedName("ROLE_CODE")
    private int RoleID;
    @SerializedName("USER_NAME")
    private String UserName;
    @SerializedName("USER_IMAGE")
    private String UserImage;
    private String UserCode;
    private String doctorSpecial;*/

    public User() {
    }

    /**
     * by Samn 28-Jul-2017
     */


    //// TODO: 28-Jul-17 : nếu có lấy từ API về thì : sửa bỏ UserCode
    //private String userCode;
    //private int doctorPhoto;

    private String Key;
    private int RoleID;
    private String UserName;
    private String UserImage;
    private String UserCode;
    private String doctorSpecial;

    private static List<User> userList;

    public String getUserCode() {
        return UserCode;
    }

    /*public User(String userCode , String userName, int roleID) {
        RoleID = roleID;
        UserName = userName;
        this.userCode = userCode;
    }*/

   /* public User(int userID, String userCode , String userName,
                int roleID, int doctorPhoto, String doctorSpecial) {
        UserID = userID;
        RoleID = roleID;
        UserName = userName;
        this.userCode = userCode;
        this.doctorPhoto = doctorPhoto;
        this.doctorSpecial = doctorSpecial;
    }

    private static void initializeUserList(){
        //cái này lấy từ bên Medical Record (list patient of Doctor) -- UserID = patientCode
        //Patient
        userList = new ArrayList<>();
        userList.add(new User("ABC1234", "John Cena", 1));
        userList.add(new User("SAM6969", "Samn Nguyen", 1));
        userList.add(new User("WSH0933", "Will Smith", 1));
        userList.add(new User("SGZ1065", "Selena Gomez", 1));
        userList.add(new User("HCC8345", "Hoàn Châu Cách Cách", 1));
        userList.add(new User("NTT3087", "Nguyễn Trần Tuyết Nghi", 1));
        userList.add(new User("THB5763", "Trịnh Hoàng Huy Bảo", 1));
        userList.add(new User("TTN9240", "Trần Phan Tố Nguyệt", 1));
        userList.add(new User("PTN8372", "Phạm Kiều Thảo Nguyên", 1));

        //doctor
        userList.add(new User(5, "TBN2374", "Allison Scott", 2, R.drawable.res_doctor4, "General practitioner (BS đa khoa)"));
        userList.add(new User(1, "NHT3249", "Gwen Seaver", 2, R.drawable.res_doctor2, "gastroenterologist (BS chuyên khoa Tiêu hóa)"));
        userList.add(new User(2, "LQH4239", "Brett Vandenberg", 2, R.drawable.res_doctor3, "Orthopedist (BS ngoại chỉnh hình)"));
        userList.add(new User(3, "PTT8930", "Fiona McConnell", 2, R.drawable.res_doctor1, "Dermatology (BS da liễu)"));
        userList.add(new User(4, "BIW1234", "Brandi Irwin", 2, R.drawable.res_doctor5, "General practitioner (BS đa khoa)"));
    }*/

    public static List<User> getUserList(){
        //initializeUserList();
        userList = new ArrayList<>();
        fetchUser();
        return userList;
    }


    private static void fetchUser(){
       dbRefer.child(USER_CHILD)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren() ){
                            User user = child.getValue(User.class);
                            user.setKey(child.getKey());
                            userList.add(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG,"User::onCancelled", databaseError.toException());
                    }
                });
    }

    public static User getUserByKey(String key){
        for (User item : userList){
            if (item.getKey() == key)
                return item;
        }
        return new User();
    }

    public static boolean checkIsUser(String user_code, List<User> userList){
        int isUser = 0;
        for (User user : userList){
            if(user.getUserCode().toLowerCase().equals(user_code)){
                isUser = 1;
                break;
            }
        }
        return (isUser == 1)? true : false;
    }

    public static int checkRole(String user_code, List<User> userList){
        int Role = 1;
        for (User user : userList){
            if(user.getUserCode().toLowerCase().equals(user_code)){
                //Role = user.getRoleID();
                break;
            }
        }
        return Role;
    }

    public static String getDoctorSpecial(int userID) {
        String result = "Default";
        /*for(User user : userList){
            if (userID == user.getUserID()){
                result = user.getDoctorSpecial();
                break;
            }
        }*/
        return result;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public int getRoleID() {
        return RoleID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public String getDoctorSpecial() {
        return doctorSpecial;
    }

    /**
     * end Samn ___
     */

}
