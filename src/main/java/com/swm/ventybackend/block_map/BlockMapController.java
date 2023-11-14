package com.swm.ventybackend.block_map;


import com.amazonaws.services.dynamodbv2.xspec.L;
import com.nimbusds.jwt.JWT;
import com.swm.ventybackend.config.BaseException;
import com.swm.ventybackend.config.BaseResponse;
import com.swm.ventybackend.config.BaseResponseStatus;
import com.swm.ventybackend.utils.JwtService;
import com.swm.ventybackend.utils.JwtSuccessDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockMapController {

    private final BlockMapService blockMapService;
    private final JwtService jwtService;

    @Operation(summary = "[1 - 3] 차단 등록", description = "차단 유저 등록")
    @PostMapping("/create")
    public BaseResponse<String> create(@RequestParam Long usersId, Long blockedUsersId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if (jwtSuccessDTO.getUsersId().equals(usersId.toString()) || jwtSuccessDTO.getStatus().equals("3")) {
                BlockMap blockMap = new BlockMap();
                blockMap.setUsersId(usersId);
                blockMap.setBlockedUsersId(blockedUsersId);

                Long blockMapId = blockMapService.saveBlockMap(blockMap);
                return new BaseResponse<String>(blockMapId + "번 유저 차단 완료");
            }
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "유저 차단 에러");

    }

    @Operation(summary = "[3] 블록 ID로 차단 해제", description = "어드민에 의한 차단 해제")
    @DeleteMapping("/deleteByBlockMapId")
    public BaseResponse<String> delete(@RequestParam Long blockMapId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if (jwtSuccessDTO.getUsersId().equals("3")) {
                blockMapService.removeBlockMapByBlockMapId(blockMapId);
                return new BaseResponse<String>(blockMapId + "번 차단 맵 삭제 완료");
            }
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "차단 맵 삭제 에러");
    }

    @Operation(summary = "[1 - 3] 차단 해제", description = "본인 및 어드민에 의한 차단 해제")
    @DeleteMapping("/deleteBlockMapByUsersIdAndBlockedUsersId")
    public BaseResponse<String> delete(@RequestParam Long usersId, Long blockedUsersId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if (jwtSuccessDTO.getUsersId().equals(usersId.toString()) || jwtSuccessDTO.getStatus().equals("3")) {
                blockMapService.removeByBothId(usersId, blockedUsersId);
                return new BaseResponse<String>(usersId + "번 유저, " + blockedUsersId + "번 차단 유저 맵 삭제");
            }
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "차단 맵 삭제 에러");
    }

    @Operation(summary = "[3] UsersId로 BlockMap 찾기", description = "어드민에 의한 BlockMap 조회")
    @GetMapping("/findByUsersId")
    public BaseResponse<List<BlockMap>> findByUsersId(@RequestParam Long usersId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if (jwtSuccessDTO.getStatus().equals("3"))
                return new BaseResponse<List<BlockMap>>(blockMapService.findByUsersId(usersId));
            else
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);

        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @Operation(summary = "[3] UsersId로 차단 당한 유저 리스트 찾기", description = "어드민에 의한 차단된 유저 리스트 조회")
    @GetMapping("/findByBlockedUsersByUsersId")
    public BaseResponse<List<BlockMap>> findByBlockedUsersByUsersId(@RequestParam Long blockedUsersId, @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken) {
        try {
            JwtSuccessDTO jwtSuccessDTO = jwtService.getToken(accessToken);

            if (jwtSuccessDTO.getStatus().equals("3"))
                return new BaseResponse<List<BlockMap>>(blockMapService.findByBlockedUsersByUsersId(blockedUsersId));
            else
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

}
