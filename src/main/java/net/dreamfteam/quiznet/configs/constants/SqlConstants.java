package net.dreamfteam.quiznet.configs.constants;

public class SqlConstants {

    //Notifications constants
    public static final String NOTIFICATIONS_GET_UNSEEN_BY_USER_ID =
            "SELECT " +
                    "CASE value " +
                    "WHEN 'uk' " +
                    "THEN content_uk " +
                    "WHEN 'en' " +
                    "THEN content " +
                    "END AS content, " +

                    "notif_id, n.user_id, date_time, seen, link, type_id " +
                    "FROM user_notifications n INNER JOIN user_settings s ON n.user_id = s.user_id " +
                    "WHERE seen = false AND n.user_id = UUID(?) AND setting_id = UUID(?)";

    public static final String NOTIFICATIONS_GET_BY_ID =
            "SELECT " +
                    "CASE value " +
                    "WHEN 'uk' " +
                    "THEN content_uk " +
                    "WHEN 'en' " +
                    "THEN content " +
                    "END AS content, " +

                    "notif_id, n.user_id, date_time, seen, link, type_id " +
                    "FROM user_notifications n INNER JOIN user_settings s ON n.user_id = s.user_id " +
                    "WHERE notif_id = UUID(?) AND setting_id = ?";

    public static final String NOTIFICATIONS_INSERT =
            "INSERT INTO user_notifications " +
                    "(content, user_id, content_uk, link, type_id) " +
                    "VALUES (?,?,?,?,?)";

    public static final String NOTIFICATIONS_UPDATE_SEEN =
            "UPDATE user_notifications " +
                    "SET seen = true " +
                    "WHERE user_id = UUID(?)";

    public static final String NOTIFICATIONS_DELETE_HALF_YEAR =
            "DELETE " +
                    "FROM user_notifications " +
                    "WHERE date_time < CURRENT_TIMESTAMP  - interval '181' day";


    //Settings constants
    public static final String SETTINGS_INIT_SETTINGS_DEFAULTS =
            "INSERT INTO user_settings (user_id, setting_id, value) " +
                    "SELECT UUID(?) , setting_id, default_value " +
                    "FROM settings " +
                    "WHERE title=?";

    public static final String SETTINGS_INIT_SETTINGS_LANGUAGE =
            "INSERT INTO user_settings (user_id, setting_id, value) " +
                    "VALUES (UUID(?),UUID('e8449301-6d6f-4376-8247-b7d1f8df6416'),?)";

    public static final String SETTINGS_GET_TITLES_DEFAULT =
            "SELECT title " +
                    "FROM settings " +
                    "WHERE default_value IS NOT NULL";

    public static final String SETTINGS_GET_TITLES_PRIVILEGED =
            "SELECT title " +
                    "FROM settings " +
                    "WHERE privileged = TRUE AND default_value IS NOT NULL";

    public static final String SETTINGS_EDIT_SETTINGS =
            "UPDATE user_settings " +
                    "SET value=? " +
                    "WHERE user_id = UUID(?) AND setting_id = UUID(?)";

    public static final String SETTINGS_GET_SETTINGS =
            "SELECT " +
                    "CASE " +
                    "WHEN ? = 'uk' " +
                    "THEN title_uk " +
                    "ELSE title " +
                    "END AS title, " +

                    "CASE " +
                    "WHEN ? = 'uk' " +
                    "THEN description_uk " +
                    "ELSE description " +
                    "END AS description, " +

                    "settings.setting_id, value " +
                    "FROM settings INNER JOIN " +
                    "user_settings on settings.setting_id=user_settings.setting_id " +
                    "WHERE user_id=UUID(?)";

    public static final String SETTINGS_GET_LANGUAGE =
            "SELECT value " +
                    "FROM user_settings " +
                    "WHERE user_id = UUID(?) AND setting_id = 'e8449301-6d6f-4376-8247-b7d1f8df6416'";

    public static final String SETTINGS_GET_NOTIFICATION_SETTING =
            "SELECT value " +
                    "FROM user_settings " +
                    "WHERE userId = UUID(?) AND setting_id = '34c00e41-9eab-49f9-8a9a-4862f6379dd0'";

    //Announcements constants

    public static final String ANNOUNCEMENTS_CREATE_ANNOUNCEMENT =
            "INSERT INTO announcements " +
                    "(creator_id, title, text_content, datetime_creation," +
                    " datetime_publication, is_published, image)" +
                    " VALUES (?,?,?,?,?,?,?)";

    public static final String ANNOUNCEMENTS_GET_ANNOUNCEMENT_BY_ID =
            "SELECT announcement_id,  creator_id as username, " +
                    "title, text_content, announcements.image, " +
                    "datetime_creation, is_published, datetime_publication" +
                    " from announcements join users " +
                    "on announcements.creator_id = users.user_id " +
                    "where announcement_id = UUID(?)";


    public static final String ANNOUNCEMENTS_GET_ALL_ANNOUNCEMENTS =
            "SELECT announcement_id, username, title, text_content," +
                    " announcements.image, datetime_creation, is_published, " +
                    "datetime_publication from announcements " +
                    "join users on announcements.creator_id = users.user_id " +
                    "where datetime_publication < current_timestamp " +
                    "order by datetime_publication desc limit ? offset ? rows;";

    public static final String ANNOUNCEMENTS_EDIT_ANNOUNCEMENT_WITH_IMAGE =
            "UPDATE announcements SET creator_id = UUID(?), title = ?,  text_content = ?"
                    + ",is_published = ?, image = ? WHERE  announcement_id = UUID(?)";

    public static final String ANNOUNCEMENTS_EDIT_ANNOUNCEMENT_WITHOUT_IMAGE =
            "UPDATE announcements SET creator_id = UUID(?), title = ?,  text_content = ?"
                    + ",is_published = ? WHERE  announcement_id = UUID(?)";

    public static final String ANNOUNCEMENTS_DELETE_ANNOUNCEMENT =
            "DELETE FROM announcementS WHERE announcement_id = UUID(?)";

    public static final String ANNOUNCEMENTS_GET_ANNOUNCEMENTS_AMOUNT =
            "SELECT count(*) from announcements";

    // Users Constants Queries

    public static final String SELECT_USER_QUERY =
            "SELECT user_id, email, password, username, is_activated, is_verified, last_time_online," +
                    "image, about_me, recovery_url, recovery_sent_time, activation_url, " +
                    "date_acc_creation, role, roles.role_id " +
            "FROM users INNER JOIN roles ON users.role_id = roles.role_id\n";

    public static final String USERS_UPDATE_QUERY_BY_ID =
            "UPDATE users SET username = ?, email = ?, password= ?, is_activated = ?, is_verified = ?," +
                    "last_time_online = ?, image = ?, about_me = ?, recovery_url = ?, recovery_sent_time = ?, role_id = ? " +
            "WHERE user_id = UUID(?) ;";

    public static final String DELETE_USER_QUERY = "DELETE FROM users\n";

    public static final String USERS_GET_ALL_BY_ROLE_USER = SELECT_USER_QUERY +
            "WHERE roles.role_id = 1 AND is_activated = true ORDER BY last_time_online DESC ;";

    public static final String USERS_GET_BY_USERNAME_SUBSTR = SELECT_USER_QUERY +
            "WHERE LOWER(username) LIKE LOWER(?) ORDER BY role , last_time_online DESC ;";

    public static final String USERS_GET_BY_USERNAME_SUBSTR_AND_ROLE_USER = SELECT_USER_QUERY +
            "WHERE LOWER(username) LIKE LOWER(?) " +
            "AND roles.role_id = 1 and is_activated = true ORDER BY last_time_online DESC ;";

    public static final String USERS_GET_BY_EMAIL = SELECT_USER_QUERY +
            "WHERE email = ? ;";

    public static final String USERS_GET_BY_USERNAME = SELECT_USER_QUERY +
            "WHERE username = ? ;";

    public static final String USERS_GET_BY_ID = SELECT_USER_QUERY +
            "WHERE user_id = UUID(?) ;";

    public static final String USERS_GET_BY_RECOVERY_URL = SELECT_USER_QUERY +
            "WHERE recovery_url = ?";

    public static final String USERS_GET_BY_ACTIVATION_URL = SELECT_USER_QUERY +
            "WHERE activation_url = ?";

    public static final String USERS_SAVE_QUERY =
            "INSERT INTO users (user_id, username, email, password, is_activated," +
                    "is_verified, activation_url, date_acc_creation, last_time_online, role_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ;";

    public static final String USERS_DELETE_BY_ID = DELETE_USER_QUERY +
            "WHERE user_id = UUID(?) ;";

    public static final String USERS_DELETE_IF_LINK_EXPIRED = DELETE_USER_QUERY +
            "WHERE is_verified = 'false' AND current_timestamp - date_acc_creation >= '1 DAY' ;";

    public static final String USERS_GET_ALL_POPULAR_CREATORS = SELECT_USER_QUERY +
            "INNER JOIN " +
            "(SELECT creator_id, count(quiz_id) AS count FROM quizzes " +
            "WHERE validated = true and published = true GROUP BY creator_id) AS cic " +
            "ON creator_id = user_id WHERE roles.role_id = 1 AND is_activated = true ORDER BY count DESC LIMIT 20 ;";

    public static final String USERS_GET_ALL_PRIVILIGED = SELECT_USER_QUERY +
            "WHERE roles.role_id > 1 ODDER BY last_time_online DESC ;";

    public static final String USERS_REMOVE_FRIEND =
            "UPDATE friends SET accepted_datetime = null, parent_id = UUID(?), friend_id=UUID(?)" +
            "WHERE parent_id IN ( UUID(?), UUID(?)) AND friend_id IN ( UUID(?), UUID(?))";

    public static final String USERS_GET_FRIENDS_RELATIONS =
            "SELECT f.parent_id = uuid(?) AS outgoing ," +
                    "f.friend_id = uuid(?) AS incoming , f.accepted_datetime IS NOT NULL AS friend from friends f " +
                    "WHERE  (f.parent_id IN ( UUID(?), UUID(?)) and f.friend_id IN ( UUID(?), UUID(?))) ;";

    public static final String USERS_REJECT_FRIEND_INVITATION =
            "DELETE FROM friends " +
            "WHERE parent_id = uuid(?) AND friend_id = uuid(?) ;";

    public static final String USERS_ACCEPT_FRIEND_INIVITAION = "UPDATE friends " +
            "SET accepted_datetime = CURRENT_TIMESTAMP " +
            "WHERE parent_id = UUID(?) AND friend_id = UUID(?) ;";

    public static final String USERS_SAVE_OUTGOING_FRIEND_INVITATION =
            "INSERT INTO friends (parent_id, friend_id, invite_datetime) " +
            "VALUES (UUID(?), UUID(?), CURRENT_TIMESTAMP) ;";

    public static final String USERS_DELETE_OUTGOING_FRIEND_INVITATION =
            "DELETE FROM friends " +
            "WHERE friend_id IN " +
            "(UUID(?), UUID(?)) AND parent_id IN ( UUID(?), UUID(?)) ;";

    public static final String USERS_GET_FRIEND_OUTGOING_INVITATIONS_TOTAL_SIZE =
            "SELECT COUNT(*) AS total_size " +
            "FROM friends " +
            "WHERE parent_id = uuid(?) " +
            "AND accepted_datetime IS NULL;";

    public static final String USERS_GET_FRIEND_OUTGOING_INVITATIONS_BY_USER_ID =
            "SELECT user_id, username, invite_datetime, image AS image_content " +
            "FROM users INNER JOIN friends f ON users.user_id = f.friend_id " +
            "WHERE f.parent_id = uuid(?) " +
            "AND f.accepted_datetime IS NULL " +
            "LIMIT ? OFFSET ?;";

    public static final String USERS_GET_FRIEND_INCOMING_INVITATIONS_TOTAL_SIZE =
            "SELECT COUNT(*) AS total_size " +
            "FROM friends " +
            "WHERE friend_id = uuid(?) " +
            "AND accepted_datetime IS NULL;";

    public static final String USERS_GET_FRIEND_INCOMING_INVITATIONS_BY_USER_ID =
            "SELECT user_id, username, invite_datetime, image AS image_content " +
            "FROM users INNER JOIN friends f ON users.user_id = f.parent_id " +
            "WHERE f.friend_id = uuid(?) " +
            "AND f.accepted_datetime IS NULL " +
            "LIMIT ? OFFSET ?;";

    public static final String USERS_GET_FRIENDS_TOTAL_SIZE =
            "SELECT COUNT(*) AS total_size " +
            "FROM friends " +
            "WHERE (parent_id = uuid(?) " +
            "OR friend_id = uuid(?))" +
            "AND accepted_datetime IS NOT NULL;";

    public static final String USERS_GET_ALL_FRIENDS_BY_USER_ID =
            "SELECT user_id, username, last_time_online, image AS image_content " +
            "FROM users WHERE user_id IN (SELECT f.friend_id AS id " +
            "FROM friends f " +
            "WHERE f.parent_id = uuid(?) " +
            "AND f.accepted_datetime IS NOT NULL " +
                    "UNION " +
                    "SELECT f1.parent_id AS id " +
                    "FROM friends f1 " +
                    "WHERE f1.friend_id = uuid(?) " +
                    "AND f1.accepted_datetime IS NOT NULL) " +
                    "LIMIT ? OFFSET ?;";

    //Quiz constants

    public static final String QUIZ_SAVE =
            "INSERT INTO quizzes (title, description, creator_id, activated, validated, quiz_lang, ver_creation_datetime, published, image) " +
            "VALUES (?,?,?,?,?,?,current_timestamp,?,?)";

    public static final String QUIZ_SAVE_QUIZ_TAGS =
            "INSERT INTO quizzes_tags (quiz_id, tag_id) VALUES (UUID(?),UUID(?))";

    public static final String QUIZ_SAVE_QUIZ_CATEGS =
            "INSERT INTO categs_quizzes (quiz_id, category_id) VALUES (UUID(?),UUID(?))";

    public static final String QUIZ_SAVE_GET_AUTHOR =
            "SELECT username " +
            "FROM users " +
            "WHERE user_id = UUID(?)";

    public static final String QUIZ_UPDATE_IS_PUBLISHED =
            "SELECT published " +
            "FROM quizzes " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_UPDATE_QUZZES_EDIT =
            "INSERT INTO quizzes_edit (prev_ver_id, new_ver_id, edit_datetime) " +
            "VALUES (UUID(?), UUID(?), current_timestamp)";

    public static final String QUIZ_UPDATE =
            "UPDATE quizzes " +
            "SET title = ?, description = ?, quiz_lang = ?, image = ? " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_UPDATE_TAGS =
            "INSERT INTO quizzes_tags (quiz_id, tag_id) " +
            "VALUES (UUID(?),UUID(?)) ON CONFLICT DO NOTHING";

    public static final String QUIZ_UPDATE_CATEGS =
            "INSERT INTO categs_quizzes (quiz_id, category_id) " +
            "VALUES (UUID(?),UUID(?)) ON CONFLICT DO NOTHING";

    public static final String QUIZ_GET =
            "SELECT quizzes.quiz_id, quizzes.title, quizzes.description, quizzes.image, quizzes.ver_creation_datetime, quizzes.creator_id, " +
            "quizzes.activated, quizzes.validated, quizzes.published, quizzes.quiz_lang, quizzes.admin_commentary, quizzes.rating, quiz_rating(quiz_id) as rating " +
            "FROM quizzes WHERE quiz_id = UUID(?)";

    public static final String QUIZ_GET_IS_FAVOURITE =
            "SELECT count(*) " +
            "FROM favourite_quizzes " +
            "WHERE user_id = UUID(?) AND quiz_id = UUID(?)";

    public static final String QUIZ_FAVOURITES_DELETE =
            "DELETE FROM favourite_quizzes " +
            "WHERE user_id = UUID(?) AND quiz_id = UUID(?)";

    public static final String QUIZ_FAVOURITES_INSERT =
            "INSERT INTO favourite_quizzes (user_id, quiz_id) " +
            "VALUES (UUID(?),UUID(?))";

    public static final String QUIZ_MARK_AS_PUBLISHED =
            "UPDATE quizzes SET published = true " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_DELETE_BY_ID =
            "DELETE FROM quizzes " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_DEACTIVATE =
            "UPDATE quizzes SET activated = false " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_GET_USER_QUIZ_BY_TITLE =
            "SELECT quiz_rating(quizzes.quiz_id), * FROM quizzes " +
            "WHERE title = ? AND creator_id = UUID(?)";

    public static final String QUIZ_VALIDATE =
            "UPDATE quizzes SET validated = true, published = ?, activated = ?, admin_commentary = ?, validator_id = uuid(?), " +
            "validation_date = current_timestamp " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_VALIDATE_GET_OLD_QUIZ_ID =
            "SELECT prev_ver_id " +
            "FROM quizzes_edit " +
            "WHERE new_ver_id = UUID(?)";

    public static final String QUIZ_FILTER_INITIAL =
            "SELECT q.quiz_id, q.title, q.image, quiz_rating(q.quiz_id) as rating  " +
            "FROM quizzes q INNER JOIN users u ON q.creator_id = u.user_id " +
            "WHERE activated = true AND validated = true AND ";

    public static final String QUIZ_FIND_QUIZZES_BY_FILTER_SIZE =
            "SELECT COUNT(*) FROM quizzes q INNER JOIN users u ON q.creator_id = u.user_id " +
            "WHERE activated = true AND validated = true AND ";

    public static final String QUIZ_GET_VALID_QUIZZES =
            "SELECT quiz_id, title, description, q.image AS image_content, ver_creation_datetime, creator_id, username, " +
            "quiz_lang, admin_commentary, published, activated " +
            "FROM quizzes q INNER JOIN users u ON q.creator_id = u.user_id " +
            "WHERE validated = true AND validator_id = UUID(?)" +
            "ORDER BY validation_date DESC " +
            "LIMIT ? OFFSET ?;";

    public static final String QUIZ_INVALID_QUIZZES_TOTAL_SIZE =
            "SELECT COUNT(*) AS total_size " +
            "FROM quizzes WHERE validated = false AND published = true";

    public static final String QUIZ_VALID_QUIZZES_TOTAL_SIZE =
            "SELECT COUNT(*) AS total_size " +
            "FROM quizzes WHERE validated = true AND validator_id = UUID(?)";

    public static final String QUIZ_SET_VALIDATOR =
            "UPDATE quizzes SET validator_id = uuid(?) " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_REMOVE_QUESTION_IMAGE =
            "UPDATE questions SET img = NULL " +
            "WHERE question_id = UUID(?)";

    public static final String QUIZ_SAVE_QUESTION =
            "INSERT INTO questions (quiz_id, title, content, points, type_id, img) VALUES (?,?,?,?,?,?)";

    public static final String QUIZ_FIRST_TYPE_ANS =
            "INSERT INTO options (content, is_correct, question_id) VALUES (?,?,UUID(?))";

    public static final String QUIZ_SECOND_THIRD_TYPE_ANS =
            "INSERT INTO one_val_options (value, question_id) VALUES (?,UUID(?))";

    public static final String QUIZ_FOURTH_TYPE_ANS =
            "INSERT INTO seq_options (seq_pos, content, question_id) VALUES (?,?,UUID(?))";

    public static final String QUIZ_DELETE_QUESTION =
            "DELETE FROM questions " +
            "WHERE question_id = UUID(?)";

    public static final String QUIZ_GET_QUESTION_LIST =
            "SELECT q.question_id, q.quiz_id, q.title, q.content, q.points, q.type_id, q.img " +
            "FROM questions q WHERE q.quiz_id = UUID(?)";

    public static final String QUIZ_GET_TAG_LIST =
            "SELECT tag_id, description FROM tags";

    public static final String QUIZ_GET_CATEGORY_LIST =
            "SELECT category_id, " +
                    "CASE WHEN " +
                    "? = 'uk' " +
                    "THEN title_uk ELSE title END AS title " +
            "FROM categories";

    public static final String QUIZ_GET_USER_QUIZ_LIST =
            "SELECT q.quiz_id, title, description, image, ver_creation_datetime, activated, validated, published, " +
                    "quiz_lang , quiz_rating(q.quiz_id) as rating, admin_commentary,  f.liked  " +
            "FROM quizzes as q left join " +
                    "(SELECT count(*) as liked, quiz_id  " +
                    "FROM favourite_quizzes " +
                    "WHERE user_id=uuid(?) " +
                    "GROUP BY quiz_id)" +
            " as f on f.quiz_id=q.quiz_id where creator_id=uuid(?) " +
            "ORDER BY validated, activated desc, published desc ";

    public static final String QUIZ_USER_FAVOURITES_LIST =
            "SELECT quiz_id, title, description, image,ver_creation_datetime, activated, validated, published,quiz_lang, quiz_rating(quiz_id) as rating  " +
            "FROM quizzes " +
            "WHERE quiz_id in (SELECT quiz_id from favourite_quizzes where user_id=uuid(?)) " +
            "ORDER BY rating DESC, published DESC, activated DESC ";

    public static final String QUIZ_GET_QUIZZES =
            "SELECT quiz_id, title, q.image AS image_content,  quiz_rating(quiz_id) as rating " +
            "FROM quizzes q " +
            "WHERE validated = true AND activated = true AND published = true " +
            "ORDER BY rating DESC LIMIT ? OFFSET ? ;";

    public static final String QUIZ_GET_SUGGESTION_QUIZ_LIST_BY_CATEGS_AND_TAGS =
            "SELECT DISTINCT q1.quiz_id, q1.title, q1.image,  quiz_rating(q1.quiz_id) as q1rating " +
            "FROM quizzes q1 INNER JOIN categs_quizzes cq1 ON q1.quiz_id = cq1.quiz_id " +
            "                INNER JOIN quizzes_tags qt1 ON q1.quiz_id = qt1.quiz_id " +
            "WHERE (category_id IN (SELECT cq.category_id " +
            // 3 categories with most of games played by the user
            "                        FROM categs_quizzes cq INNER JOIN (games g INNER JOIN users_games ug " +
            "                                                           ON g.game_id = ug.game_id) " +
            "                                                           ON g.quiz_id = cq.quiz_id " +
            "                        WHERE ug.user_id = uuid(?) " + //UserId here
            "                        GROUP BY cq.category_id " +
            "                        ORDER BY COUNT(g.game_id) DESC " +
            "                        LIMIT 3) " + "      OR qt1.tag_id IN (SELECT qt3.tag_id " +
            //3 tags with most of games played by the user
            "                        FROM quizzes_tags qt3 INNER JOIN (games g3 INNER JOIN users_games ug3 " +
            "                                                          ON g3.game_id = ug3.game_id) " +
            "                                                          ON g3.quiz_id = qt3.quiz_id " +
            "                        WHERE ug3.user_id = uuid(?) " + //Same userId here
            "                        GROUP BY qt3.tag_id " +
            "                        ORDER BY COUNT(g3.game_id) DESC" +
            "                        LIMIT 3) " + "      ) " +
            //excluding quizzes which the user has already played before
            "      AND q1.quiz_id NOT IN (SELECT g2.quiz_id " +
            "                            FROM games g2 INNER JOIN users_games ug2 " +
            "ON g2.game_id = ug2.game_id " + "                            WHERE ug2.user_id = uuid(?)) " +
            "      AND q1.activated = true " + //only available to play
            "ORDER BY q1rating DESC " + //order by overall rating
            "LIMIT ?;"; //first X rows;

    public static final String QUIZ_GET_INVALID_QUIZZES =
            "SELECT quiz_id, title, description, q.image AS image_content, ver_creation_datetime, creator_id, username, quiz_lang, admin_commentary " +
            "FROM quizzes q INNER JOIN users u ON q.creator_id = u.user_id " +
            "WHERE validated = false AND (validator_id IS NULL OR validator_id = uuid(?)) " +
            "AND published = true " +
            "ORDER BY ver_creation_datetime DESC " +
            "LIMIT ? OFFSET ?;";

    public static final String QUIZ_QUIZZES_TOTAL_SIZE =
            "SELECT COUNT(*) AS total_size " +
            "FROM quizzes WHERE validated = true AND activated = true AND published = true";

    public static final String QUIZ_QUESTIONS_AMOUNT_IN_QUIZ =
            "SELECT COUNT(*) AS total_size " +
            "FROM questions WHERE quiz_id = uuid(?)";

    public static final String QUIZ_GET_QUESTIONS_IN_PAGE =
            "SELECT q.question_id, q.quiz_id, q.title, q.content, q.points, q.type_id, q.img as imgcontent " +
            "FROM questions q " +
            "WHERE q.quiz_id = UUID(?) LIMIT ? OFFSET ?";

    public static final String QUIZ_AMOUNT_SUCCESS_CREATED =
            "SELECT COUNT(*) " +
            "FROM quizzes " +
            "WHERE creator_id = uuid(?) AND validated = true AND published = true;";

    public static final String QUIZ_LAST_PLAYED_QUIZZES =
            "SELECT q.quiz_id, duration_time, is_winner, score, datetime_start, q.title, g.game_id " +
            "FROM users_games ug INNER JOIN games g ON ug.game_id = g.game_id " +
                    "INNER JOIN quizzes q ON q.quiz_id = g.quiz_id " +
            "WHERE user_id = UUID(?)" + "AND datetime_start > (NOW() - INTERVAL '7 DAY') " +
                    "AND finished = TRUE;";

    public static final String QUIZ_MOST_POPULAR_QUIZZES_LAST_WEEK =
            "SELECT g.quiz_id, q.title, COUNT(*) games_amount " +
            "FROM quizzes q INNER JOIN games g ON g.quiz_id = q.quiz_id " +
            "WHERE g.datetime_start > (NOW() - INTERVAL '7 DAY') " +
            "GROUP BY g.quiz_id, q.title " +
            "ORDER BY games_amount DESC " +
            "LIMIT (?) ";

    public static final String QUIZ_USER_QUIZ_RATING =
            "SELECT quiz_id, user_id, rating_points " +
            "FROM user_quiz_rating " +
            "WHERE quiz_id=uuid(?) AND user_id=uuid(?) ";

    public static final String QUIZ_RATE_QUIZ =
            "INSERT INTO user_quiz_rating (quiz_id, user_id, rating_points) " +
                    "VALUES (" +
                        "(SELECT quiz_id " +
                        "FROM games " +
                        "WHERE game_id=uuid(?)" +
                    "), uuid(?), ?) " +
            "ON CONFLICT (quiz_id, user_id) DO UPDATE SET rating_points = ?;";

    public static final String QUIZ_COUNT_VALIDATED_BY_ADMIN =
            "SELECT count(*) FROM quizzes " +
            "JOIN users u ON quizzes.validator_id = u.user_id " +
            "WHERE role_id = 3 and CURRENT_TIMESTAMP - validation_date <= '7 DAY' ";

    public static final String QUIZ_COUNT_VALIDATED_BY_MODERATOR =
            "SELECT count(*) FROM quizzes " +
            "JOIN users u ON quizzes.validator_id = u.user_id " +
            "WHERE role_id = 2 and CURRENT_TIMESTAMP - validation_date <= '7 DAY' ";

    public static final String QUIZ_GET_NUMBER =
            "SELECT COUNT(*) " +
            "FROM quizzes";

    public static final String QUIZ_ACTIVATED_NUMBER =
            "SELECT COUNT(*) " +
            "FROM quizzes WHERE activated = true";

    public static final String QUIZ_PUBLISHED_NUMBER =
            "SELECT COUNT(*) " +
            "FROM quizzes WHERE published = true";

    public static final String QUIZ_REJECTED_NUMBER =
            "SELECT COUNT(*) " +
            "FROM quizzes WHERE validated = true AND activated = false";

    public static final String QUIZ_UNVALIDATED_NUMBER =
            "SELECT COUNT(*) " +
            "FROM quizzes WHERE validated = false";

    public static final String QUIZ_GET_RIGHT_ANSWERS =
            "SELECT content " +
            "FROM options " +
            "WHERE question_id = UUID(?) AND is_correct = true";

    public static final String QUIZ_GET_OTHER_ANSWERS =
            "SELECT content " +
            "FROM options " +
            "WHERE question_id = UUID(?) AND is_correct = false";

    public static final String QUIZ_GET_ONE_VAL_ANSWER =
            "SELECT value " +
            "FROM one_val_options " +
            "WHERE question_id = UUID(?)";

    public static final String QUIZ_GET_SEQUENCE_ANSWER =
            "SELECT content " +
            "FROM seq_options " +
            "WHERE question_id = UUID(?) " +
            "ORDER BY seq_pos;";

    public static final String QUIZ_GET_USER_QUIZZES_RATING =
            "SELECT quiz_id, title, image, quiz_rating(quiz_id) as rating " +
            "FROM quizzes " +
            "WHERE creator_id = UUID(?) " +
            "ORDER BY rating DESC";

    public static final String QUIZ_GET_TAG_NAME_LIST =
            "SELECT t.description " +
            "FROM tags t " +
                "INNER JOIN quizzes_tags qt " +
                "ON t.tag_id = qt.tag_id " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_GET_CATEGORY_NAME_LIST =
            "SELECT CASE WHEN " +
                    "? = 'uk' " +
                    "THEN c.title_uk " +
                    "ELSE c.title END AS title "+
            "FROM categories c INNER JOIN categs_quizzes cq ON c.category_id = cq.category_id " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_GET_TAG_ID_LIST =
            "SELECT tag_id " +
            "FROM quizzes_tags " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_GET_CATEGORY_ID_LIST =
            "SELECT category_id " +
            "FROM categs_quizzes " +
            "WHERE quiz_id = UUID(?)";

    public static final String QUIZ_USER_QUIZ_LIST_AMOUNT =
            "SELECT count(*) as amount " +
            "FROM quizzes " +
            "WHERE creator_id=uuid(?) ";

    public static final String QUIZ_USER_FAV_QUIZ_LIST_AMOUNT =
            "SELECT count(*) " +
            "FROM quizzes " +
            "WHERE quiz_id in (" +
                    "SELECT quiz_id " +
                    "FROM favourite_quizzes " +
                    "WHERE user_id=uuid(?)" +
                    ")";
}
