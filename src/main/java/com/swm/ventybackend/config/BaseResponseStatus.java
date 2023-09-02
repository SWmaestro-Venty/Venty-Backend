package com.swm.ventybackend.config;
import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EXISTS_NAME(false, 2018, "중복된 닉네임입니다."),
    POST_USERS_EMPTY_PASSWORD(false, 2019, "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_BORN_YEAR(false, 2020,"출생년도를 확인해주세요."),
    POST_USERS_EMPTY_BORN_YEAR(false, 2021, "출생년도를 입력해주세요."),
    POST_USERS_EMPTY_NAME(false, 2022, "닉네임을 입력해주세요."),
    POST_USERS_EMPTY_GENDER(false, 2023, "성별을 입력해주세요."),
    POST_USERS_SHORT_PASSWORD(false, 2027, "비밀번호가 너무 짧습니다."),
    POST_USERS_SHORT_NAME(false, 2028, "닉네임이 너무 짧습니다."),

    // Common : id형식이 long이어야하는데 String형이 들어왔을 경우/ long의 범위를 넘어갈 경우
    INVALID_ID(false, 2024, "올바르지 않은 id 형식입니다."),

    // [POST] /users/log-in
    POST_LOGIN_CHECK_EMAIL_OR_PW(false, 2025, "이메일이나 패스워드를 확인해주세요."),

    // [PATCH] /users/:userId
    PATCH_PASSWORD_EMPTY(false, 2026, "변경할 비밀번호를 입력하세요."),
    PATCH_PASSWORD_SHORT(false, 2028, "변경할 비밀번호가 너무 짧습니다."),

    // [GET] /app/categories?category-id=:categoryId
    EMPTY_CATEGORY_ID(false, 2029, "카테고리가 비어있습니다."),
    GET_CATEGORY_NOT_EXISTS(false, 2030, "없는 카테고리입니다."),

    // COMMON
    EMPTY_PATH_VARIABLE(false, 2031, "path variable이 비어있습니다."),

    // [GET] /app/events/:eventId
    EVENT_NOT_EXISTS(false, 2032, "존재하지 않는 이벤트입니다."),

    // COMMON
    MINI_CATEGORY_NOT_EXISTS(false, 2033, "존재하지 않는 하위 카테고리입니다."),
    TYPE_ERROR_NOT_BOOLEAN(false, 2034, "쿼리 파라미터는 boolean형이 들어와야합니다."),
    INVALID_ITEM_NUMBER(false, 2035, "구매할 제품의 개수는 0개거나 그 이하일 수 없습니다."),

    EMPTY_OPTION_NUMBER(false, 2036, "구매할 상품의 개수를 입력해주세요."),
    EMPTY_OPTION_ID(false, 2037, "구매할 상품을 선택해주세요."),
    KART_ITEM_ALREADY_EXISTS(false, 2038, "장바구니에 이미 담겨져있습니다."),

    EMPTY_SCRAPBOOK_NAME(false, 2039, "스크랩북 이름을 입력해주세요."),

    EMPTY_KART_ID_LIST(false, 2040, "주문할 상품이 없습니다."),

    // [POST] /users/payments/:userId
    EMPTY_ORDER_NAME(false, 2041,"주문자의 이름을 입력해주세요."),
    EMPTY_ORDER_PHONE_NUMBER(false, 2042, "주문자의 휴대폰 번호를 입력해주세요."),
    EMPTY_EMAIL(false, 2043, "주문자의 이메일을 입력하세요."),
    EMPTY_RECEIVED_NAME(false, 2044, "받는 사람의 이름을 입력하세요."),
    EMPTY_RECEIVED_PHONE(false, 2045, "받는 사람의 휴대폰 번호를 입력하세요."),
    EMPTY_PLACE_NAME(false, 2046,"배송지 명을 입력하세요."),
    EMPTY_ADDRESS_CODE(false, 2047, "우편번호를 입력하세요."),
    EMPTY_ADDRESS(false, 2048, "상세 주소를 입력하세요."),
    INVALID_ADDRESS_CODE(false, 2049, "올바르지 않은 우편번호입니다."),
    EMPTY_KART_ID(false, 2050, "주문할 상품을 선택해주세요."),
    EMPTY_COUPON_CODE(false, 2051, "쿠폰 번호를 입력해주세요."),
    EMPTY_REVIEW_DESCRIPTION(false, 2052, "리뷰를 작성해주세요."),
    SHORT_REVIEW_DESCRIPTION(false, 2053, "리뷰는 20자 이상 입력해야합니다."),
    INVALID_REVIEW_SCORE(false, 2054, "올바르지 않은 점수입니다."),
    EMPTY_INQUIRY_CATEGORY(false, 2055, "문의 카테고리를 입력해주세요."),
    EMPTY_INQUIRY_DESCRIPTION(false, 2056, "문의 내용을 입력해주세요."),
    INVALID_INQUIRY_ISPUBLIC(false, 2057, "공개 여부 코드가 잘못되었습니다. 0과 1 중에 다시 선택해주세요."),
    EMPTY_VERIFICATION_CODE(false, 2058, "인증 코드를 입력하세요."),
    EMPTY_VERIFICATION_CODE_ID(false, 2059, "인증 코드 id를 입력해주세요."),
    INVALID_VERIFICATION_CODE(false, 2060, "올바르지 않은 이메일 인증 코드이거나 만료된 인증 코드입니다."),

    INVALID_ID_USER(false, 2061, "올바르지 않은 유저 id 형식입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    USER_NOT_EXISTS(false, 3015, "존재하지 않는 유저입니다."),
    ITEM_ID_NOT_EXISTS(false, 3016, "존재하지 않는 상품 id입니다."),
    NOT_VALID_OPTION(false, 3017, "해당 상품에 속하지 않는 옵션입니다."),
    OPTION_ID_NOT_EXISTS(false, 3018, "존재하지 않는 옵션 id입니다."),
    INVALID_ITEM_OPTION(false, 3019, "올바르지 않은 상품 옵션입니다."),
    KART_ID_NOT_EXISTS(false, 3020, "장바구니 옵션이 존재하지 않습니다."),

    INVALID_PATCH_COUPON_STATUS_REQUEST(false, 3021, "올바르지 않은 쿠폰 상태 변경 요청입니다."),
    INVALID_COUPON_ID(false, 3022, "존재하지 않는 쿠폰 id입니다."),
    EMAIL_NOT_EXISTS(false, 3023, "존재하지 않는 이메일입니다."),
    COUPON_CODE_NOT_EXISTS(false, 3024, "이미 등록되었거나 존재하지 않는 쿠폰 코드입니다."),
    REVIEW_ALREADY_WRITTEN(false, 3025, "이미 사용자가 제품에 대한 리뷰를 작성했습니다."),
    LIKE_CATEGORY_NOT_EXISTS(false, 3026, "존재하지 않는 카테고리이거나 유저가 좋아요 누르지 않은 카테고리입니다."),

    GUEST_ORDER_EMAIL_NOT_EXISTS(false, 3027, "존재하지 않는 비회원 주문 이메일입니다."),
    GUEST_ORDER_NUMBER_NOT_EXISTS(false, 3028, "존재하지 않는 비회원 주문번호입니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    UPDATE_FAIL_PASSWORD(false, 4015, "유저 비밀번호 변경 실패");

    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}