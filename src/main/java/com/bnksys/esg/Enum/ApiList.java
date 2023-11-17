package com.bnksys.esg.Enum;

public enum ApiList {
    BUSINESS(1, "apilist_Business"),
    ELECTRONIC(2, "apilist_Electronic"),
    BuSINESS2(3,"apilist_Business2"),
    TEST(4, "apilist_Test");

    private final int schedulingId;
    private final String functionName;

    ApiList(int schedulingId, String functionName) {
        this.schedulingId = schedulingId;
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public static ApiList fromSchedulingId(int schedulingId) {
        for (ApiList apiList : values()) {
            if (apiList.schedulingId == schedulingId) {
                return apiList;
            }
        }
        throw new IllegalArgumentException("없는 API 목록 입니다 : " + schedulingId);
    }
    }
