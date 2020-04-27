package net.dreamfteam.quiznet.data.dao;

import net.dreamfteam.quiznet.data.entities.User;

import java.util.List;

public interface UserDao {

    String SELECT_QUERY = "SELECT user_id, email, password, username, is_online, is_activated, is_verified, last_time_online," +
            "image, about_me, recovery_url, recovery_sent_time, activation_url, date_acc_creation, role, roles.role_id " +
            "FROM users INNER JOIN roles ON users.role_id=roles.role_id\n";

    String UPDATE_QUERY = "UPDATE users SET username = ?, email = ?, password= ?, is_activated = ?, is_verified = ?," +
            " is_online = ?, last_time_online = ?, image = ?, about_me = ?, recovery_url = ?, recovery_sent_time = ?, role_id = ?";

    String DELETE_QUERY = "DELETE FROM users";

    String SAVE_QUERY = "INSERT INTO users (user_id, username, email, password, is_activated," +
            " is_verified, is_online, activation_url, date_acc_creation, last_time_online, role_id) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    User getByEmail(String email);

    User getByUsername(String username);

    User getById(String id);

    User save(User user);

    void deleteById(String id);

    void update(User user);

    List<User> getAll();

    User getByActivationUrl(String activationUrl);

    User getByRecoverUrl(String recoverUrl);

    List<User> getAllByRoleUser();

    List<User> getBySubStr(String str);

    List<User> getBySubStrAndRoleUser(String str);

    int deleteIfLinkExpired();
}
