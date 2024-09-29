package notai.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import static lombok.AccessLevel.PROTECTED;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OauthId {

    @NotNull
    @Column(length = 255)
    private String oauthId;

    @NotNull
    @Enumerated(STRING)
    @Column(length = 20)
    private OauthProvider oauthProvider;
}
