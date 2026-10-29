package fu.de200045.ch08javafx.b3;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class UserStore {
    // Lưu tài khoản trong bộ nhớ RAM bằng Map (key: username viết thường)
    private static final Map<String, User> USERS = new HashMap<>();

    static {
        // Tài khoản quản trị viên mẫu có sẵn
        add(new User("Quản trị viên", "admin@example.com", "admin", "Admin@123",
                "Khác", LocalDate.of(2000, 1, 1), "Kỹ thuật phần mềm", List.of()));
    }

    private UserStore() {}

    public static boolean exists(String username) {
        return USERS.containsKey(username.trim().toLowerCase());
    }

    public static void add(User u) {
        USERS.put(u.username().trim().toLowerCase(), u);
    }

    public static Optional<User> login(String username, String password) {
        return Optional.ofNullable(USERS.get(username.trim().toLowerCase()))
                .filter(u -> u.password().equals(password));
    }
}