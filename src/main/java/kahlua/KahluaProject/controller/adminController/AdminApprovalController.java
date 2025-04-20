package kahlua.KahluaProject.controller.adminController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.user.response.UserListResponse;
import kahlua.KahluaProject.global.aop.checkAdmin.CheckUserType;
import kahlua.KahluaProject.global.apipayload.ApiResponse;
import kahlua.KahluaProject.global.security.AuthDetails;
import kahlua.KahluaProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자(멤버 관리)", description = "관리자(멤버 관리) 페이지 관련 API")
@RestController
@RequestMapping("/v1/admin/users")
@RequiredArgsConstructor
public class AdminApprovalController {
    private final UserService userService;

    @GetMapping
    @CheckUserType(userType = UserType.ADMIN)
    @Operation(summary = "깔루아 멤버리스트 API", description = """
            깔루아 멤버들의 정보를 확인할 수 있는 리스트 api입니다.
            UserType이 ADMIN/KAHLUA일 경우 승인 상태는 APROVED,
            PENDING일 경우에는 PENDING,
            ALL을 입력할 경우 전체 멤버 명단이 조회됩니다.
            """)
    public ApiResponse<UserListResponse> getUsers(
            @AuthenticationPrincipal AuthDetails authDetails,
            @RequestParam(defaultValue = "ALL")String approvalStatus,
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "10")   int size){

        UserListResponse userListResponse=userService.getUserList(approvalStatus,page,size);
        return ApiResponse.onSuccess(userListResponse);
    }
}
