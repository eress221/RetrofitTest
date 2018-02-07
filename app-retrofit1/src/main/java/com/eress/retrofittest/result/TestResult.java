package com.eress.retrofittest.result;

import com.eress.retrofittest.data.Test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TestResult {

    String result;
    int member_seq;
    String member_nm;
    String status;
    Test[] user_list;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getMember_seq() {
        return member_seq;
    }

    public void setMember_seq(int member_seq) {
        this.member_seq = member_seq;
    }

    public String getMember_nm() {
        return member_nm;
    }

    public void setMember_nm(String member_nm) {
        this.member_nm = member_nm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Test[] getUser_list() {
        return user_list;
    }

    public void setUser_list(Test[] user_list) {
        this.user_list = user_list;
    }
}
