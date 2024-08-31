package hlmg.users.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
