package dtos;

import entities.Role;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {


    private String user_name;
    private String user_pass;
    private String address;
    private String phone;
    private String email;
    private String birthYear;
    private int account;
    private List<String> roles = new ArrayList<>();

    // Constructors, getters, and setters

    public UserDTO(User user) {
        this.user_name = user.getUser_name();
        this.user_pass = user.getUserPass();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.birthYear = user.getBirthYear();
        this.account = user.getAccount();
        for (Role role : user.getRoleList()) {
            this.roles.add(role.getRoleName());
        }
    }

    public UserDTO(String user_name, String user_pass, String address, String phone, String email, String birthYear, int account) {
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthYear = birthYear;
        this.account = account;
    }

    public static List<UserDTO> getDtos(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(new UserDTO(user)));
        return userDTOS;
    }
}
