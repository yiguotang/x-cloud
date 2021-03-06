/**
 * 
 */
package org.waddys.xcloud.msg.serviceImpl.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.waddys.xcloud.msg.serviceImpl.dao.entity.MessageInfoE;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
public interface MessageRepository extends Repository<MessageInfoE, String> {

    /**
     * 新增一个消息实体
     * 
     * @param messageInfoE
     * @return 返回一个消息对象
     */
    public MessageInfoE save(MessageInfoE messageInfoE);

    /**
     * 根据 ID 进行删除
     * 
     * @param id
     */
    public void deleteById(String id);

    /**
     * 根据ID进行查找
     * 
     * @param id
     * @return 返回一个消息实体对象
     */
    public MessageInfoE findById(String id);

    /**
     * 根据ID分页查询
     *
     * @return 返回page 集合对象
     */
    public Page<MessageInfoE> findAll(Pageable pageable);

    /**
     * 根据状态进行查询
     * 
     * @param state
     * @return
     */
    public List<MessageInfoE> findByEventState(String state);

    /**
     * 统计一共有多少条数据
     *
     * @return
     */
    @Query("select count(distinct message) from MessageInfoE message")
    public int countAll();

    /**
     * 根据条件分页查询
     * 
     * @param param
     * @param pageable
     * @return
     */
    @Query("select p from MessageInfoE p where p.mailSubject like %?1%")
    public Page<MessageInfoE> findByMailSubject(String param, Pageable pageable);

}
