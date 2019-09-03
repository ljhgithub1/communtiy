package life.langteng.community.entity;

public class Notification {
    private Integer id;

    private Integer outer;

    private Integer replyer;

    private Integer receiver;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

    private String outerTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOuter() {
        return outer;
    }

    public void setOuter(Integer outer) {
        this.outer = outer;
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

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }
}