package com.bookshop.mybookshop.domain.user;

import com.bookshop.mybookshop.dto.ChangeUserDataForm;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Entity
public class UserDataEdition {

    @Id
    private UUID id;
    private String name;
    private String mail;
    private String phone;
    private String password;

    private String userDetailEmail;

    private LocalDateTime expiredTime;

    public UserDataEdition(ChangeUserDataForm changeUserDataForm, String uuid, String email) {
        this.id = UUID.fromString(uuid);
        this.userDetailEmail = email;
        if (changeUserDataForm.getName() != null && StringUtils.hasText(changeUserDataForm.getName())) {
            this.name = changeUserDataForm.getName();
        }
        if (changeUserDataForm.getMail() != null && StringUtils.hasText(changeUserDataForm.getMail())) {
            this.mail = changeUserDataForm.getMail();
        }
        if (changeUserDataForm.getPassword() != null && StringUtils.hasText(changeUserDataForm.getPassword()) &&
                changeUserDataForm.getPassword().equals(changeUserDataForm.getPasswordReply())) {
            this.password = changeUserDataForm.getPassword();
        }
        if (changeUserDataForm.getPhone() != null && StringUtils.hasText(changeUserDataForm.getPhone())) {
            this.phone = changeUserDataForm.getPhone();
        }
        this.expiredTime = LocalDateTime.now().plusMinutes(10);
    }

    public UserDataEdition() {
    }
}
