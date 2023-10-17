package com.bnksys.esg.data;

import lombok.Data;

public class Member {
    int id;
    String name;

    // 기본 생성자
    public Member() {
    }

    // Getter와 Setter 메서드
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
