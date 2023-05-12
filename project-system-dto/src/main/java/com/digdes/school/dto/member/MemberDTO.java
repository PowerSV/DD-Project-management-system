package com.digdes.school.dto.member;

public class MemberDTO {

    private Long id;
    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                '}';
    }

}
