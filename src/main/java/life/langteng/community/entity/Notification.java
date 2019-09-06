package life.langteng.community.entity;

public class Notification {
    private Integer id;

    private Integer outter;

    private Integer replyer;

    private Integer receiver;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

    private String outterTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOutter() {
        return outter;
    }

    public void setOutter(Integer outter) {
        this.outter = outter;
    }

    public Integer getReplyer() {
        return replyer;
    }

    public void setReplyer(Integer replyer) {
        this.replyer = replyer;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOutterTitle() {
        return outterTitle;
    }

    public void setOutterTitle(String outterTitle) {
        this.outterTitle = outterTitle == null ? null : outterTitle.trim();
    }
}