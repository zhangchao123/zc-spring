package com.zc.orm;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zhangchao
 * @Title: Member
 * @ProjectName info-price
 * @Description: TODO
 * @date 2019/4/22/02215:58
 */
@Entity
@Table(name="com_ad")
@Data
public class Member {
    private String adNumber;
    private String picUrl;
}
