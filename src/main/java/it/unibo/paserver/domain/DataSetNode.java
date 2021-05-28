package it.unibo.paserver.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe de suporte para gerar rede e vinculos
 */
@Entity
@Table(name = "dataset_node")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataSetNode {

    private static final long serialVersionUID = 5104409303261032452L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "node_id")
    private Long nodeId;

    @Column(name = "linked_id")
    private long linkedId;
    @Column(name = "ref_id")
    private long refId;

    @Column(name = "ref_order")
    private int order;
    @Column(name = "label", columnDefinition = "TEXT", nullable = true)
    private String label;

    @Transient
    private List<Map<Long, Integer>> leaves = new ArrayList<>();

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creationDate", updatable = false, nullable = false)
    private DateTime creationDate = DateTime.now();
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "updateDate", updatable = true, nullable = false)
    private DateTime updateDate = DateTime.now();
    @Column(name = "removed")
    @Type(type = "org.hibernate.type.BooleanType")
    private boolean removed = false;

    public DataSetNode(long refId, int order, String label) {
        this.refId = refId;
        this.order = order;
        this.label = label;
    }

    public DataSetNode() {
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        return String.format(" LINKED_ID : %s, ID : %s , LABEL: %s , ORDER: %s", this.getLinkedId(), this.getRefId(), this.getLabel(), this.getOrder());
    }

    public List<Map<Long, Integer>> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Map<Long, Integer>> leaves) {
        this.leaves = leaves;
    }

    public void addLeaf(Long k, Integer v) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(k, v);
        this.leaves.add(map);
    }

    public long getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(long linkedId) {
        this.linkedId = linkedId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
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
