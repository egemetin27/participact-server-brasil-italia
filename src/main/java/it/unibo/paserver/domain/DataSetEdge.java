package it.unibo.paserver.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Claudio
 * @project participact-server
 * @date 28/02/2019
 **/
@Entity
@Table(name = "dataset_edge")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataSetEdge {

    private static final long serialVersionUID = 5963144339365478497L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "edge_id")
    private Long edgeId;

    @Column(name = "linked_id")
    private long linkedId;
    @Column(name = "from_id")
    private long fromId;
    @Column(name = "to_id")
    private long toId;
    @Column(name = "leaf_id")
    private long leafId;
    @Column(name = "from_order")
    private int fromOrder;
    @Column(name = "to_order")
    private int toOrder;
    @Column(name = "leaf_order")
    private int leafOrder;
    @Column(name = "to_arrow")
    private boolean toArrow = false;
    @Column(name = "label", columnDefinition = "TEXT", nullable = true)
    private String label;
    @Column(name = "type")
    private String type = "circle";
    @Column(name = "isdegree")
    private boolean isDegree;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();
    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public DataSetEdge(int fromOrder, int toOrder, String label, boolean isDegree, int leafOrder) {
        this.fromOrder = fromOrder;
        this.toOrder = toOrder;
        this.label = label;
        this.isDegree = isDegree;
        this.leafOrder = leafOrder;
    }

    public DataSetEdge(long fromId, long toId, int fromOrder, int toOrder, boolean toArrow, String label, String type) {
        this.fromId = fromId;
        this.toId = toId;
        this.fromOrder = fromOrder;
        this.toOrder = toOrder;
        this.toArrow = toArrow;
        this.label = label;
        this.type = type;
    }

    public DataSetEdge() {
    }

    public String toString() {
        return String.format("linkedId %s,  fromId: %s , fromOrder: %s , getLabel: %s , getToId: %s , getToOrder: %s , getType: %s  ,disDegree %s , leafOrder %s, leafId %s",
                this.getLinkedId(), this.getFromId(), this.getFromOrder(), this.getLabel(), this.getToId(), this.getToOrder(), this.getType(), this.getDegree(), this.getLeafOrder(), this.getLeafId());
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public int getFromOrder() {
        return fromOrder;
    }

    public void setFromOrder(int fromOrder) {
        this.fromOrder = fromOrder;
    }

    public int getToOrder() {
        return toOrder;
    }

    public void setToOrder(int toOrder) {
        this.toOrder = toOrder;
    }

    public boolean isToArrow() {
        return toArrow;
    }

    public void setToArrow(boolean toArrow) {
        this.toArrow = toArrow;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getDegree() {
        return this.isDegree;
    }

    public int getLeafOrder() {
        return leafOrder;
    }

    public void setLeafOrder(int leafOrder) {
        this.leafOrder = leafOrder;
    }

    public boolean isDegree() {
        return this.isDegree;
    }

    public void setDegree(boolean d) {
        this.isDegree = d;
    }

    public long getLeafId() {
        return leafId;
    }

    public void setLeafId(long leafId) {
        this.leafId = leafId;
    }

    public long getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(long linkedId) {
        this.linkedId = linkedId;
    }

    public Long getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(Long edgeId) {
        this.edgeId = edgeId;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
