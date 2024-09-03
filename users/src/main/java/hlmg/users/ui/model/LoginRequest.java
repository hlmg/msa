package hlmg.users.ui.model;

public record LoginRequest(
        String email,
        String password
) {
}
