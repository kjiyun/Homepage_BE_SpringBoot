package kahlua.KahluaProject.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {
    @Schema(description = "https://kahlua.com.jpg")
    private String profileImageUrl;
}