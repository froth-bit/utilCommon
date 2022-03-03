package com.wyc.utils.wo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyc
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("scm_wo_hdr")
public class ScmWoHdr implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "uid", type = IdType.ASSIGN_UUID)
    private String uid;

    private String sWoCode;

    private Date dCreateDate;

    private String sRemark;

    public String getsWoCode() {
        return sWoCode;
    }

    public void setsWoCode(String sWoCode) {
        this.sWoCode = sWoCode;
    }

    public Date getdCreateDate() {
        return dCreateDate;
    }

    public void setdCreateDate(Date dCreateDate) {
        this.dCreateDate = dCreateDate;
    }

    public String getsRemark() {
        return sRemark;
    }

    public void setsRemark(String sRemark) {
        this.sRemark = sRemark;
    }
}
